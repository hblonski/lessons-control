package com.lessonscontrol.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class EditLessonActivity extends AppCompatActivity {

    /**
     * Request code for this activity. This code will be returned when the activity exits.
     * @see android.support.v4.app.FragmentActivity#startActivityForResult
     */
    public static final int EDIT_LESSON_ACTIVITY_REQUEST_CODE = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_lesson);
    }
}
