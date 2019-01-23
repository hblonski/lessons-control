package com.lessonscontrol.data.viewModel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;

import com.lessonscontrol.data.entities.Lesson;
import com.lessonscontrol.data.entities.Student;
import com.lessonscontrol.data.repositories.LessonRepository;

import java.util.List;

/**
 * Student View Model.
 *
 * @author hblonski
 */
public class LessonViewModel extends AndroidViewModel {

    private LessonRepository lessonRepository;

    public LessonViewModel(Application application) {
        super(application);
        lessonRepository = new LessonRepository(application);
    }

    public void insert(Lesson lesson) {
        lessonRepository.insert(lesson);
    }

    public void update(Lesson lesson) {
        lessonRepository.update(lesson);
    }

    public void delete(Lesson lesson) {
        lessonRepository.delete(lesson);
    }

    public LiveData<List<Lesson>> findLessonsByStudent(Student student) {
        return lessonRepository.findLessonsByStudent(student);
    }
}
