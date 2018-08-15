package com.lessonscontrol.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.lessonscontrol.data.entities.Student;

import java.security.InvalidParameterException;
import java.util.InvalidPropertiesFormatException;


public class ViewStudentActivity extends AppCompatActivity {

    /**
     * Request code for this activity. This code will be returned when the activity exits.
     * @see android.support.v4.app.FragmentActivity#startActivityForResult
     */
    public static final int VIEW_STUDENT_ACTIVITY_REQUEST_CODE = 2;

    private Student student;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_student);

        this.student = this.getIntent().getParcelableExtra(Student.STUDENT_EXTRA_KEY);

        if (this.student == null) {
            String invalidParameterMessage = "Student should not be null on ViewStudentActivity.";
            Log.e(this.getClass().toString(), invalidParameterMessage);
            throw new InvalidParameterException(invalidParameterMessage);
        }

        this.populateViewWithStudentInfo();

        FloatingActionButton fab = findViewById(R.id.edit_student_fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent editStudentInfoIntent = new Intent(ViewStudentActivity.this, EditStudentActivity.class);
                ViewStudentActivity.this.startActivityForResult(editStudentInfoIntent, EditStudentActivity.EDIT_STUDENT_ACTIVITY_REQUEST_CODE);
            }
        });
    }

    private void populateViewWithStudentInfo() {
        ((TextView) findViewById(R.id.view_name)).setText(student.getName());
        ((TextView) findViewById(R.id.view_phone)).setText(student.getPhone());
        ((TextView) findViewById(R.id.view_mail)).setText(student.getEmail());
        ((TextView) findViewById(R.id.view_address)).setText(student.getAddress());
    }
}
