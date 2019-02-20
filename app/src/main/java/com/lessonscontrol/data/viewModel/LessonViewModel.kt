package com.lessonscontrol.data.viewModel

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData

import com.lessonscontrol.data.entities.Lesson
import com.lessonscontrol.data.entities.Student
import com.lessonscontrol.data.repositories.LessonRepository

/**
 * Student View Model.
 *
 * @author hblonski
 */
class LessonViewModel(application: Application) : AndroidViewModel(application) {

    private val lessonRepository: LessonRepository = LessonRepository(application)

    fun insert(lesson: Lesson) {
        lessonRepository.insert(lesson)
    }

    fun update(lesson: Lesson) {
        lessonRepository.update(lesson)
    }

    fun delete(lesson: Lesson) {
        lessonRepository.delete(lesson)
    }

    fun findLessonsByStudent(student: Student): LiveData<List<Lesson>> {
        return lessonRepository.findLessonsByStudent(student)
    }
}
