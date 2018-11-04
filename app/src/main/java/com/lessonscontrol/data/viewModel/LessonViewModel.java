package com.lessonscontrol.data.viewModel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;

import com.lessonscontrol.data.entities.Lesson;
import com.lessonscontrol.data.entities.Student;
import com.lessonscontrol.data.repositories.LessonRepository;
import com.lessonscontrol.data.repositories.StudentRepository;

import java.util.List;

/**
 * Student View Model.
 *
 * @author hblonski
 */
public class LessonViewModel extends AndroidViewModel {

    private LessonRepository lessonRepository;

    private LiveData<List<Lesson>> lessons;

    public LessonViewModel(Application application) {
        super(application);
        lessonRepository = new LessonRepository(application);
    }

    public void insert(Lesson lesson) {
        this.lessonRepository.insert(lesson);
    }

    public LiveData<List<Lesson>> findLessonsByStudent(Student student) {
        return this.lessonRepository.findLessonsByStudent(student);
    }
}
