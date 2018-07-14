package com.lessonscontrol.activities;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.lessonscontrol.data.entities.Student;
import com.lessonscontrol.data.viewModel.StudentViewModel;

/**
 * New student form activity.
 * @author hblonski
 */
public class NewStudentActivity extends AppCompatActivity {

    private StudentViewModel studentViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_student);
        studentViewModel = ViewModelProviders.of(this).get(StudentViewModel.class);

        FloatingActionButton doneFAB = findViewById(R.id.new_student_form_done_fab);
        doneFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                Student student = NewStudentActivity.this.createStudentFromInputData();

                if (student == null) {
                    //TODO remover esse toast e marcar os required na tela
                    Toast.makeText(
                            getApplicationContext(),
                            new String("One or more required fields are empty."),
                            Toast.LENGTH_LONG).show();
                } else {
                    studentViewModel.insert(student);
                    setResult(RESULT_OK, intent);
                    finish();
                }
            }
        });
    }

    /**
     * Generates a {@link Student} using UI input data.
     * @return The {@link Student} object. If required fields are empty, the return will be null.
     */
    private Student createStudentFromInputData() {
        String name = ((EditText) findViewById(R.id.name)).getText().toString();
        String email = ((EditText) findViewById(R.id.mail)).getText().toString();
        String address = ((EditText) findViewById(R.id.address)).getText().toString();
        String phone = ((EditText) findViewById(R.id.phone)).getText().toString();

        if (name != null) {
            return new Student(name, address, email, phone);
        }

        return null;
    }
}
