package com.lessonscontrol.activities;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.graphics.Canvas;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;

import com.lessonscontrol.adapter.StudentListAdapter;
import com.lessonscontrol.data.entities.Student;
import com.lessonscontrol.data.viewModel.LessonViewModel;
import com.lessonscontrol.data.viewModel.StudentViewModel;
import com.lessonscontrol.utils.StudentCardSwipeController;

import java.util.List;

/**
 * Main activity.
 *
 * @author hblonski
 */
public class MainActivity extends AppCompatActivity {

    private StudentViewModel studentViewModel;

    private LessonViewModel lessonViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        studentViewModel = ViewModelProviders.of(this).get(StudentViewModel.class);
        lessonViewModel = ViewModelProviders.of(this).get(LessonViewModel.class);

        FloatingActionButton newStudentFAB = findViewById(R.id.new_student_fab);

        //Sets the FAB to open the new student activity (form screen).
        newStudentFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent newStudentActivityIntent = new Intent(MainActivity.this, EditStudentActivity.class);
                MainActivity.this.startActivityForResult(newStudentActivityIntent, EditStudentActivity.EDIT_STUDENT_ACTIVITY_REQUEST_CODE);
            }
        });

        RecyclerView recyclerView = findViewById(R.id.students_recycler_view);
        final StudentListAdapter studentListAdapter = new StudentListAdapter(this);
        recyclerView.setAdapter(studentListAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        this.observeDataChange(studentListAdapter);

        final StudentCardSwipeController studentCardSwipeController = new StudentCardSwipeController();
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(studentCardSwipeController);
        itemTouchHelper.attachToRecyclerView(recyclerView);
        recyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
                studentCardSwipeController.onDraw(c, getResources());
            }
        });
    }

    private void observeDataChange(final StudentListAdapter studentListAdapter) {
        this.studentViewModel.getAllStudents().observe(this, new Observer<List<Student>>() {
            @Override
            public void onChanged(@Nullable List<Student> students) {
                studentListAdapter.setStudents(students);
            }
        });
    }
}
