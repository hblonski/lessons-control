package com.lessonscontrol.lessonscontrol;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;


public class MainActivity extends AppCompatActivity {

    private static final int NEW_STUDENT_ACTIVITY_REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FloatingActionButton fab = findViewById(R.id.new_student_fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent newStudentActivityIntent = new Intent(MainActivity.this, NewStudentActivity.class);
                MainActivity.this.startActivityForResult(newStudentActivityIntent, NEW_STUDENT_ACTIVITY_REQUEST_CODE);
            }
        });
    }
}
