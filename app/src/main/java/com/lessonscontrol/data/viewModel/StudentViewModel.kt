package com.lessonscontrol.data.viewModel

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData

import com.lessonscontrol.data.entities.Student
import com.lessonscontrol.data.repositories.StudentRepository

/**
 * Student View Model.
 *
 * @author hblonski
 */
class StudentViewModel(application: Application) : AndroidViewModel(application) {

    private val studentRepository: StudentRepository = StudentRepository(application)

    val allStudents: LiveData<List<Student>>

    init {
        allStudents = studentRepository.allStudents
    }

    fun insert(student: Student) {
        this.studentRepository.insert(student)
    }

    fun update(student: Student) {
        this.studentRepository.update(student)
    }

    fun delete(student: Student) {
        this.studentRepository.delete(student)
    }
}
