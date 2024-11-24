/**
 * UpdateActivity.java

 * This activity allows users to update or delete an existing student's information.
 * The student details are retrieved from the database and displayed in input fields.
 * Users can modify the information and update it in the database, or delete the student's record.

 * Author: Abdulla Nibah Hussain
 * Date: 24/11/2024
 * Version: 1.0

 * Note: This activity uses an AlertDialog to confirm the deletion of a student.
 */

package com.example.studentdatabase;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class UpdateActivity extends AppCompatActivity {

    // UI elements
    EditText name_input, number_input, mail_input, physics_input, math_input;
    Button update_button, delete_button;

    // Variables to hold the student's information
    String id, name, number, mail, physics, math, grade;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        // Initialize the UI elements
        name_input = findViewById(R.id.name_input2);
        number_input = findViewById(R.id.number_input2);
        mail_input = findViewById(R.id.email_input2);
        physics_input = findViewById(R.id.physics_input2);
        math_input = findViewById(R.id.math_input2);
        update_button = findViewById(R.id.update_button);
        delete_button = findViewById(R.id.delete_button);

        // Retrieve data passed via Intent and set it to the input fields
        getAndSetIntentData();

        // Set the ActionBar title to the student's name
        ActionBar ab = getSupportActionBar();
        if (ab != null) {
            ab.setTitle(name);
        }

        // Handle the update button click event
        update_button.setOnClickListener(view -> {
            try {
                // Retrieve the updated student data from input fields
                String updatedName = name_input.getText().toString();
                int updatedNumber = Integer.parseInt(number_input.getText().toString());
                String updatedMail = mail_input.getText().toString();
                int updatedPhysics = Integer.parseInt(physics_input.getText().toString());
                int updatedMath = Integer.parseInt(math_input.getText().toString());

                // Calculate the average and assign a grade
                int average = (updatedPhysics + updatedMath) / 2;
                String updatedGrade = (average >= 90) ? "A+" : (average >= 80) ? "A" :
                        (average >= 70) ? "B+" : (average >= 60) ? "B" :
                                (average >= 50) ? "C+" : (average >= 40) ? "C" :
                                        (average >= 33) ? "D" : "F";

                // Update the student data in the database
                MyDatabaseHelper myDB = new MyDatabaseHelper(UpdateActivity.this);
                myDB.updateStudent(id, updatedName, updatedNumber, updatedMail, updatedPhysics, updatedMath, updatedGrade);

                // Send the result back to MainActivity to indicate successful update
                Intent resultIntent = new Intent();
                setResult(RESULT_OK, resultIntent);

                // Show a success message
                Toast.makeText(UpdateActivity.this, "Updated Successfully", Toast.LENGTH_SHORT).show();
                finish(); // Close the activity
            } catch (NumberFormatException e) {
                // Show an error message if invalid numbers are entered
                Toast.makeText(UpdateActivity.this, "Please enter valid numbers!", Toast.LENGTH_SHORT).show();
            }
        });

        // Handle the delete button click event
        delete_button.setOnClickListener(view -> {
            confirmDialog(); // Show a confirmation dialog before deleting
        });
    }

    /**
     * Retrieves the student data from the Intent and populates the input fields.
     */
    void getAndSetIntentData() {
        if (getIntent().hasExtra("id") && getIntent().hasExtra("name") &&
                getIntent().hasExtra("number") && getIntent().hasExtra("mail") &&
                getIntent().hasExtra("physics") && getIntent().hasExtra("math") && getIntent().hasExtra("grade")) {

            // Retrieve the data passed via Intent
            id = getIntent().getStringExtra("id");
            name = getIntent().getStringExtra("name");
            number = getIntent().getStringExtra("number");
            mail = getIntent().getStringExtra("mail");
            physics = getIntent().getStringExtra("physics");
            math = getIntent().getStringExtra("math");
            grade = getIntent().getStringExtra("grade");

            // Set the retrieved data to the input fields
            name_input.setText(name);
            number_input.setText(number);
            mail_input.setText(mail);
            physics_input.setText(physics);
            math_input.setText(math);
        } else {
            // Show a message if no data is found
            Toast.makeText(this, "No data.", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Displays a confirmation dialog before deleting the student's record.
     */
    void confirmDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete " + name + " ?");
        builder.setMessage("Are you sure you want to delete " + name + " ?");

        // Handle the positive button (Yes)
        builder.setPositiveButton("Yes", (dialogInterface, i) -> {
            // Delete the student record from the database
            MyDatabaseHelper myDB = new MyDatabaseHelper(UpdateActivity.this);
            myDB.deleteOneRow(id);

            // Send the result back to MainActivity to indicate deletion
            Intent resultIntent = new Intent();
            setResult(RESULT_OK, resultIntent);
            finish(); // Close the activity after deletion
        });

        // Handle the negative button (No)
        builder.setNegativeButton("No", (dialogInterface, i) -> {
            // Dismiss the dialog
        });

        // Show the confirmation dialog
        builder.create().show();
    }
}
