/**
 * MainActivity.java

 * This is the main activity for the Student Database application.
 * It handles the display of student records in a RecyclerView and allows
 * the user to add, view, and delete student data.

 * Author: Abdulla Nibah Hussain
 * Date: 24/11/2024
 * Version: 1.0

 * Note: This file includes functionality for viewing and deleting all student records.
 */

package com.example.studentdatabase;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    // Declare UI components
    RecyclerView recyclerView;
    FloatingActionButton add_button;
    ImageView empty_imageview;
    TextView no_data;

    // Declare necessary data storage variables
    MyDatabaseHelper myDB;
    ArrayList<String> student_id, student_name, student_num, student_mail, student_physics, student_math, grade;
    CustomAdapter customAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize UI components
        recyclerView = findViewById(R.id.recyclerView);
        add_button = findViewById(R.id.add_button);
        empty_imageview = findViewById(R.id.empty_imageview);
        no_data = findViewById(R.id.no_data);

        // Initialize database helper and data lists
        myDB = new MyDatabaseHelper(MainActivity.this);
        student_id = new ArrayList<>();
        student_name = new ArrayList<>();
        student_num = new ArrayList<>();
        student_mail = new ArrayList<>();
        student_physics = new ArrayList<>();
        student_math = new ArrayList<>();
        grade = new ArrayList<>();

        // Fetch and store data from database
        storeDataInArrays();

        // Set up RecyclerView adapter and layout manager
        customAdapter = new CustomAdapter(
                MainActivity.this,
                this,
                student_id,
                student_name,
                student_num,
                student_mail,
                grade,
                student_physics,
                student_math
        );
        recyclerView.setAdapter(customAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));

        // Set up the click listener for the add button
        add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Open AddActivity to add new student data
                Intent intent = new Intent(MainActivity.this, AddActivity.class);
                startActivityForResult(intent, 1);  // Start AddActivity and wait for result
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK) {
            // Refresh the data in the RecyclerView after adding a new student
            storeDataInArrays();
            customAdapter.notifyDataSetChanged();  // Notify the adapter to refresh RecyclerView
        }
    }

    // Method to store data in the arrays and update the RecyclerView
    void storeDataInArrays() {
        // Clear the existing data to prevent duplicates
        student_id.clear();
        student_name.clear();
        student_num.clear();
        student_mail.clear();
        student_physics.clear();
        student_math.clear();
        grade.clear();

        // Query all student data from the database
        Cursor cursor = myDB.readAllData();

        // Check if the database is empty
        if (cursor.getCount() == 0) {
            // Display the empty state UI
            empty_imageview.setVisibility(View.VISIBLE);
            no_data.setVisibility(View.VISIBLE);
        } else {
            // Populate the arrays with student data from the database
            while (cursor.moveToNext()) {
                student_id.add(cursor.getString(0));
                student_name.add(cursor.getString(1));
                student_num.add(cursor.getString(2));
                student_mail.add(cursor.getString(3));
                student_physics.add(cursor.getString(4)); // Physics column
                student_math.add(cursor.getString(5));    // Math column
                grade.add(cursor.getString(6));
            }
            // Hide the empty state UI
            empty_imageview.setVisibility(View.GONE);
            no_data.setVisibility(View.GONE);
        }
    }

    // Inflate the options menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);  // Inflate the menu from the XML file
        return super.onCreateOptionsMenu(menu);
    }

    // Handle menu item selection
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        // Check if the "Delete All" menu item was selected
        if(item.getItemId() == R.id.delete_all) {
            // Show a toast and open the confirmation dialog
            Toast.makeText(this, "All data deleted!", Toast.LENGTH_SHORT).show();
            confirmDialog();
        }
        return super.onOptionsItemSelected(item);
    }

    // Show a confirmation dialog before deleting all data
    void confirmDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete All?");  // Set the title of the dialog
        builder.setMessage("Are you sure you want to delete all data?");  // Set the message

        // Define the actions for the dialog's buttons
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                // Perform the deletion when "Yes" is clicked
                MyDatabaseHelper myDB = new MyDatabaseHelper(MainActivity.this);
                myDB.deleteAllData();  // Delete all student data from the database

                // Refresh the RecyclerView after deletion
                storeDataInArrays();
                customAdapter.notifyDataSetChanged();  // Notify the adapter to refresh the RecyclerView

                // Optionally, send a result to notify the user (not necessary here)
                Intent resultIntent = new Intent();
                setResult(RESULT_OK, resultIntent);
            }
        });

        // Handle the "No" button (dismiss the dialog)
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                // Dismiss the dialog without doing anything
            }
        });

        // Show the dialog
        builder.create().show();
    }
}
