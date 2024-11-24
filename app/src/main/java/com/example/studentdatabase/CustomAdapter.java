/**
 * CustomAdapter.java

 * This adapter is used to bind the student data to the RecyclerView in the MainActivity.
 * It manages the display of each student's information, including their ID, name, number, email, grade,
 * and marks in Physics and Math. The adapter allows interaction with each item, opening the UpdateActivity
 * to modify student records.

 * Author: Abdulla Nibah Hussain
 * Date: 24/11/2024
 * Version: 1.0

 * Note: This adapter handles the creation and binding of student items in the RecyclerView and
 * passes student details to the UpdateActivity when an item is clicked.
 */

package com.example.studentdatabase;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder> {

    private final Context context;
    private final Activity activity;
    private final ArrayList<String> student_id, student_name, student_num, student_mail, grade, student_physics, student_math;

    /**
     * Constructor for the CustomAdapter class. Initializes the data arrays and context.
     *
     * @param activity The activity from which this adapter is invoked.
     * @param context The context for inflating views.
     * @param student_id List of student IDs.
     * @param student_name List of student names.
     * @param student_num List of student numbers.
     * @param student_mail List of student emails.
     * @param grade List of student grades.
     * @param student_physics List of student marks in Physics.
     * @param student_math List of student marks in Math.
     */
    public CustomAdapter(Activity activity,
                         Context context,
                         ArrayList<String> student_id,
                         ArrayList<String> student_name,
                         ArrayList<String> student_num,
                         ArrayList<String> student_mail,
                         ArrayList<String> grade,
                         ArrayList<String> student_physics,
                         ArrayList<String> student_math) {
        this.activity = activity;
        this.context = context;
        this.student_id = student_id;
        this.student_name = student_name;
        this.student_num = student_num;
        this.student_mail = student_mail;
        this.grade = grade;
        this.student_physics = student_physics;
        this.student_math = student_math;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate the item layout for each student record
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.my_row, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        // Bind student data to the views in the ViewHolder
        holder.student_id_txt.setText(student_id.get(position));
        holder.student_name_txt.setText(student_name.get(position));
        holder.student_number_txt.setText(student_num.get(position));
        holder.student_mail_txt.setText(student_mail.get(position));
        holder.student_grade_txt.setText(grade.get(position));

        // Set a click listener on the main layout to open UpdateActivity with the selected student's data
        holder.mainLayout.setOnClickListener(view -> {
            Intent intent = new Intent(context, UpdateActivity.class);
            // Pass the selected student's details to the UpdateActivity
            intent.putExtra("id", student_id.get(position));
            intent.putExtra("name", student_name.get(position));
            intent.putExtra("number", student_num.get(position));
            intent.putExtra("mail", student_mail.get(position));
            intent.putExtra("physics", student_physics.get(position)); // Pass physics marks
            intent.putExtra("math", student_math.get(position));       // Pass math marks
            intent.putExtra("grade", grade.get(position));              // Pass grade
            activity.startActivityForResult(intent, 1);  // Start UpdateActivity and expect a result
        });
    }

    @Override
    public int getItemCount() {
        // Return the total number of items (students) in the list
        return student_id.size();
    }

    /**
     * ViewHolder class for holding the views for each student item in the RecyclerView.
     */
    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView student_id_txt, student_name_txt, student_number_txt, student_mail_txt, student_grade_txt;
        LinearLayout mainLayout;

        /**
         * Constructor for MyViewHolder class. Initializes the views for a single student item.
         *
         * @param itemView The view of a single student item.
         */
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            // Initialize the views that will be used to display student data
            student_id_txt = itemView.findViewById(R.id.student_id_txt);
            student_name_txt = itemView.findViewById(R.id.student_name_txt);
            student_number_txt = itemView.findViewById(R.id.student_number_txt);
            student_mail_txt = itemView.findViewById(R.id.student_mail_txt);
            student_grade_txt = itemView.findViewById(R.id.student_grade_txt);
            mainLayout = itemView.findViewById(R.id.mainLayout);  // Root layout of the item
        }
    }
}
