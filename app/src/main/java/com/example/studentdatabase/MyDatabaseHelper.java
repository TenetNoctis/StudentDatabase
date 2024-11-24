/**
 * MyDatabaseHelper.java

 * This class handles all database-related operations for the Student Database application.
 * It defines methods for creating, reading, updating, and deleting student records in an SQLite database.
 * It also includes functionality for adding new students and deleting all data from the database.

 * Author: Abdulla Nibah Hussain
 * Date: 24/11/2024
 * Version: 1.0

 * Note: This class extends SQLiteOpenHelper to manage database creation and version management.
 */

package com.example.studentdatabase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class MyDatabaseHelper extends SQLiteOpenHelper {

    private final Context context;
    private static final String DATABASE_NAME = "StudentDB.db"; // Database name
    private static final int DATABASE_VERSION = 1; // Database version

    // Table and column names
    private static final String TABLE_NAME = "cyryx_college";
    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_NAME = "student_name";
    private static final String COLUMN_NUMBER = "student_num";
    private static final String COLUMN_MAIL = "student_mail";
    private static final String COLUMN_PHYSICS = "student_physics";
    private static final String COLUMN_MATH = "student_math";
    private static final String COLUMN_GRADE = "student_grade";

    // Constructor
    public MyDatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    // Called when the database is created for the first time
    @Override
    public void onCreate(SQLiteDatabase db) {
        // SQL query to create the table
        String query = "CREATE TABLE " + TABLE_NAME +
                " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_NAME + " TEXT, " +
                COLUMN_NUMBER + " INTEGER, " +
                COLUMN_MAIL + " TEXT, " +
                COLUMN_PHYSICS + " INTEGER, " +
                COLUMN_MATH + " INTEGER, " +
                COLUMN_GRADE + " TEXT);";
        db.execSQL(query);
    }

    // Called when the database version is updated
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME); // Drop the old table
        onCreate(db); // Create the new table
    }

    // Method to add a new student to the database
    public void addStudent(String name, int number, String mail, int physics, int math, String grade) {
        SQLiteDatabase db = this.getWritableDatabase(); // Get writable database
        ContentValues cv = new ContentValues();

        // Add values to ContentValues
        cv.put(COLUMN_NAME, name);
        cv.put(COLUMN_NUMBER, number);
        cv.put(COLUMN_MAIL, mail);
        cv.put(COLUMN_PHYSICS, physics);
        cv.put(COLUMN_MATH, math);
        cv.put(COLUMN_GRADE, grade);

        // Insert the new student data into the table
        long result = db.insert(TABLE_NAME, null, cv);
        if (result == -1) {
            Toast.makeText(context, "Failed to add student", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Student added successfully", Toast.LENGTH_SHORT).show();
        }
    }

    // Method to read all data from the database
    public Cursor readAllData() {
        String query = "SELECT * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase(); // Get readable database
        return db.rawQuery(query, null); // Execute query and return cursor
    }

    // Method to update an existing student's details
    public void updateStudent(String row_id, String name, int number, String mail, int physics, int math, String grade) {
        SQLiteDatabase db = this.getWritableDatabase(); // Get writable database
        ContentValues cv = new ContentValues();

        // Add updated values to ContentValues
        cv.put(COLUMN_NAME, name);
        cv.put(COLUMN_NUMBER, number);
        cv.put(COLUMN_MAIL, mail);
        cv.put(COLUMN_PHYSICS, physics);
        cv.put(COLUMN_MATH, math);
        cv.put(COLUMN_GRADE, grade);

        // Update the student data in the database
        long result = db.update(TABLE_NAME, cv, "_id=?", new String[]{row_id});
        if (result == -1) {
            Toast.makeText(context, "Failed to update student", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Student updated successfully", Toast.LENGTH_SHORT).show();
        }
    }

    // Method to delete a single student record
    public void deleteOneRow(String row_id) {
        SQLiteDatabase db = this.getWritableDatabase(); // Get writable database
        long result = db.delete(TABLE_NAME, "_id=?", new String[]{row_id});
        if (result == -1) {
            Toast.makeText(context, "Failed to delete student", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Student deleted successfully", Toast.LENGTH_SHORT).show();
        }
    }

    // Method to delete all student records from the database and reset ID to 1
    void deleteAllData() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_NAME);
        db.execSQL("DELETE FROM sqlite_sequence WHERE name='" + TABLE_NAME + "'");
        Toast.makeText(context, "All Data Deleted. ID reset to 1.", Toast.LENGTH_SHORT).show();
    }
}
