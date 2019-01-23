package com.lessonscontrol.data.viewModel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;

import com.lessonscontrol.data.entities.Student;
import com.lessonscontrol.data.repositories.StudentRepository;

import java.util.List;

/**
 * Student View Model.
 *
 * @author hblonski
 */
public class StudentViewModel extends AndroidViewModel {

    private StudentRepository studentRepository;

    private LiveData<List<Student>> allStudents;

    public StudentViewModel(Application application) {
        super(application);
        studentRepository = new StudentRepository(application);
        allStudents = studentRepository.getAllStudents();

    }

    public LiveData<List<Student>> getAllStudents() {
        return this.allStudents;
    }

    public void insert(Student student) {
        this.studentRepository.insert(student);
    }

    public void update(Student student) {
        this.studentRepository.update(student);
    }

    public void delete(Student student) {
        this.studentRepository.delete(student);
    }
}
