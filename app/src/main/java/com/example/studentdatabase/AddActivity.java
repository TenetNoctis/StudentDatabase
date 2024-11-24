/**
 * AddActivity.java

 * This activity allows users to add a new student record to the database.
 * It collects student details such as name, number, email, physics marks, and math marks,
 * calculates the average grade, and stores the data in the database.

 * Author: Abdulla Nibah Hussain
 * Date: 24/11/2024
 * Version: 1.0

 * Note: This activity validates input data and displays appropriate error messages for missing or invalid inputs.
 */

package com.example.studentdatabase;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class AddActivity extends AppCompatActivity {

    // Declare input fields and button
    EditText name_input, number_input, email_input, physics_input, math_input;
    Button add_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        // Initialize input fields and button
        name_input = findViewById(R.id.name_input);
        number_input = findViewById(R.id.number_input);
        email_input = findViewById(R.id.email_input);
        physics_input = findViewById(R.id.physics_input);
        math_input = findViewById(R.id.math_input);
        add_button = findViewById(R.id.add_button);

        // Set OnClickListener for the add button
        add_button.setOnClickListener(view -> {
            // Try to add data to the database
            try {
                // Get and trim user inputs
                String name = name_input.getText().toString().trim();
                int number = Integer.parseInt(number_input.getText().toString().trim());
                String email = email_input.getText().toString().trim();
                int physics = Integer.parseInt(physics_input.getText().toString().trim());
                int math = Integer.parseInt(math_input.getText().toString().trim());

                // Calculate average and determine grade based on marks
                int average = (physics + math) / 2;
                String grade = (average >= 90) ? "A+" : (average >= 80) ? "A" :
                        (average >= 70) ? "B+" : (average >= 60) ? "B" :
                                (average >= 50) ? "C+" : (average >= 40) ? "C" :
                                        (average >= 33) ? "D" : "F";

                // Validate required fields
                if (name.isEmpty() || email.isEmpty()) {
                    Toast.makeText(AddActivity.this, "Name and Email are required!", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Save student data to the database
                MyDatabaseHelper myDB = new MyDatabaseHelper(AddActivity.this);
                myDB.addStudent(name, number, email, physics, math, grade);

                // Send result back to MainActivity and close the activity
                Intent returnIntent = new Intent();
                setResult(RESULT_OK, returnIntent);
                finish(); // Close this activity

            } catch (NumberFormatException e) {
                // Handle invalid number input (non-numeric)
                Toast.makeText(AddActivity.this, "Please enter valid numbers!", Toast.LENGTH_SHORT).show();
            } catch (Exception e) {
                // Catch any other unexpected errors
                Toast.makeText(AddActivity.this, "An error occurred: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
