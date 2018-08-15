package com.lessonscontrol.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lessonscontrol.activities.MainActivity;
import com.lessonscontrol.activities.R;
import com.lessonscontrol.activities.ViewStudentActivity;
import com.lessonscontrol.data.entities.Student;

import java.util.List;

/**
 * Adapter for a {@link Student} list.
 *
 * @author hblonski
 */
public class StudentListAdapter extends RecyclerView.Adapter<StudentListAdapter.StudentViewHolder> {

    class StudentViewHolder extends RecyclerView.ViewHolder {
        private final TextView nameView;
        private final TextView nextClassView;

        private StudentViewHolder(View itemView) {
            super(itemView);
            nameView = itemView.findViewById(R.id.student_name);
            nextClassView = itemView.findViewById(R.id.student_nextclass);
        }
    }

    private final LayoutInflater layoutInflater;

    private List<Student> students;

    public StudentListAdapter(Context context) {
        layoutInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public StudentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = layoutInflater.inflate(R.layout.student_card, parent, false);
        return new StudentViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(StudentViewHolder studentViewHolder, int position) {
        if (this.students != null) {
            Student current = this.students.get(position);
            studentViewHolder.nameView.setText(current.getName());
            //TODO colocar a data da proxima aula
            studentViewHolder.nextClassView.setText("DUMMY");
        } else {
            // Covers the case of data not being ready yet.
            studentViewHolder.nameView.setText("Data not ready.");
        }

        //Opens the ViewStudentActivity on click.
        studentViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity mainActivity = (MainActivity) v.getContext();
                Intent viewStudentIntent = new Intent(mainActivity, ViewStudentActivity.class);
                mainActivity.startActivityForResult(viewStudentIntent, ViewStudentActivity.VIEW_STUDENT_ACTIVITY_REQUEST_CODE);
            }
        });
    }

    public void setStudents(List<Student> students){
        this.students = students;
        notifyDataSetChanged();
    }

    // getItemCount() is called many times, and when it is first called,
    // mWords has not been updated (means initially, it's null, and we can't return null).
    @Override
    public int getItemCount() {
        if (this.students != null)
            return this.students.size();
        else return 0;
    }
}
