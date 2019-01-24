package com.lessonscontrol.activities;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.lessonscontrol.data.entities.Student;
import com.lessonscontrol.data.viewModel.StudentViewModel;

/**
 * New student form activity.
 * @author hblonski
 */
public class EditStudentActivity extends AppCompatActivity {

    /**
     * Request code for this activity. This code will be returned when the activity exits.
     * @see android.support.v4.app.FragmentActivity#startActivityForResult
     */
    public static final int EDIT_STUDENT_ACTIVITY_REQUEST_CODE = 1;

    private StudentViewModel studentViewModel;

    private Student student;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_student);
        studentViewModel = ViewModelProviders.of(this).get(StudentViewModel.class);

        this.student = this.getIntent().getParcelableExtra(Student.STUDENT_EXTRA_KEY);

        if (this.student != null) {
            this.populateActivityWithStudentInfo();
        }

        FloatingActionButton doneFAB = findViewById(R.id.edit_student_form_done_fab);
        doneFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent resultIntent = new Intent();

                try {
                    updateStudentInfoFromActivityData();
                    resultIntent.putExtra(Student.STUDENT_EXTRA_KEY, student);
                    setResult(RESULT_OK, resultIntent);
                    finish();
                } catch (IllegalArgumentException e) {
                    Toast.makeText(
                            getApplicationContext(),
                            getString(R.string.required_fields_empty),
                            Toast.LENGTH_LONG).show();
                    Log.e(EditStudentActivity.this.getClass().toString(),
                            "Required argument missing when trying to create student.");
                }
            }
        });
    }

    private void populateActivityWithStudentInfo() {
        ((EditText) findViewById(R.id.edit_name)).setText(student.getName());
        ((EditText) findViewById(R.id.edit_phone)).setText(student.getPhone());
        ((EditText) findViewById(R.id.edit_mail)).setText(student.getEmail());
        ((EditText) findViewById(R.id.edit_address)).setText(student.getAddress());
    }

    /**
     * Updates the activity {@link Student} or, if it is null, creates a new one using UI input data.
     */
    private void updateStudentInfoFromActivityData() throws IllegalArgumentException {
        String name = ((EditText) findViewById(R.id.edit_name)).getText().toString();
        String email = ((EditText) findViewById(R.id.edit_mail)).getText().toString();
        String address = ((EditText) findViewById(R.id.edit_address)).getText().toString();
        String phone = ((EditText) findViewById(R.id.edit_phone)).getText().toString();

        if (name == null || name.isEmpty()) {
            ((TextInputLayout) findViewById(R.id.input_layout_name)).setError(getString(R.string.required_field));
            throw new IllegalArgumentException("Student name is missing");
        } else if (this.student != null) {
            this.student.setName(name);
            this.student.setAddress(address);
            this.student.setEmail(email);
            this.student.setPhone(phone);
            this.studentViewModel.update(this.student);
        } else {
            this.student = new Student(name, address, email, phone);
            this.studentViewModel.insert(student);
        }
    }
}
