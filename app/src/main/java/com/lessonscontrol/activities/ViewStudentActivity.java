package com.lessonscontrol.activities;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.lessonscontrol.adapter.LessonListAdapter;
import com.lessonscontrol.data.entities.Lesson;
import com.lessonscontrol.data.entities.Student;
import com.lessonscontrol.data.viewModel.LessonViewModel;

import java.security.InvalidParameterException;
import java.util.List;


public class ViewStudentActivity extends AppCompatActivity {

    /**
     * Request code for this activity. This code will be returned when the activity exits.
     * @see android.support.v4.app.FragmentActivity#startActivityForResult
     */
    public static final int VIEW_STUDENT_ACTIVITY_REQUEST_CODE = 2;

    private Student student;

    private LessonViewModel lessonViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_student);
        this.lessonViewModel = ViewModelProviders.of(this).get(LessonViewModel.class);

        this.student = this.getIntent().getParcelableExtra(Student.STUDENT_EXTRA_KEY);

        if (this.student == null) {
            String invalidParameterMessage = "Student should not be null on ViewStudentActivity.";
            Log.e(this.getClass().toString(), invalidParameterMessage);
            throw new InvalidParameterException(invalidParameterMessage);
        }

        this.populateActivityWithStudentInfo();

        RecyclerView lessonsRecyclerView = findViewById(R.id.lessons_recycler_view);
        final LessonListAdapter lessonListAdapter = new LessonListAdapter(this);
        lessonsRecyclerView.setAdapter(lessonListAdapter);
        lessonsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        this.observeDataChange(lessonListAdapter);

        ImageButton editStudentButton = findViewById(R.id.button_edit_student);
        editStudentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent editStudentActivityIntent = new Intent(ViewStudentActivity.this, EditStudentActivity.class);
                editStudentActivityIntent.putExtra(Student.STUDENT_EXTRA_KEY, student);
                ViewStudentActivity.this.startActivityForResult(editStudentActivityIntent, EditStudentActivity.EDIT_STUDENT_ACTIVITY_REQUEST_CODE);
            }
        });

        ImageButton addLessonButton = findViewById(R.id.button_add_lesson);
        addLessonButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent newLessonIntent = new Intent(ViewStudentActivity.this, EditLessonActivity.class);
                newLessonIntent.putExtra(EditLessonActivity.STUDENT_ID_KEY, student.getID());
                ViewStudentActivity.this.startActivityForResult(newLessonIntent, EditLessonActivity.EDIT_LESSON_ACTIVITY_REQUEST_CODE);
            }
        });
    }

    private void observeDataChange(final LessonListAdapter lessonListAdapter) {
        this.lessonViewModel.findLessonsByStudent(this.student).observe(this, new Observer<List<Lesson>>() {
            @Override
            public void onChanged(@Nullable List<Lesson> lessons) {
                lessonListAdapter.setLessons(lessons);
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //Updates the screen if the EditStudentActivity returns success (student updated)
        if (requestCode == EditStudentActivity.EDIT_STUDENT_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
            this.student = data.getParcelableExtra(Student.STUDENT_EXTRA_KEY);
            this.populateActivityWithStudentInfo();
        }
    }

    private void populateActivityWithStudentInfo() {
        ((TextView) findViewById(R.id.view_name)).setText(student.getName());
        ((TextView) findViewById(R.id.view_phone)).setText(student.getPhone());
        ((TextView) findViewById(R.id.view_mail)).setText(student.getEmail());
        ((TextView) findViewById(R.id.view_address)).setText(student.getAddress());
    }
}
