package com.student.scheduler.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.student.scheduler.R
import com.student.scheduler.model.LessonUi
import com.student.scheduler.model.TaskUi
import com.student.scheduler.model.WeekDay

/**
 * ViewModel for the Home ("Сегодня") screen.
 *
 * TEMPORARY: data below is hardcoded mock data matching the approved
 * mockup, so the UI can be built and demoed before the database exists.
 * From week 2, [loadMockData] will be replaced with calls into a
 * LessonRepository / TaskRepository backed by Room, and the Fragment
 * will not need to change at all — it only observes LiveData.
 */
class HomeViewModel : ViewModel() {

    private val _weekDays = MutableLiveData<List<WeekDay>>()
    val weekDays: LiveData<List<WeekDay>> = _weekDays

    private val _todayLessons = MutableLiveData<List<LessonUi>>()
    val todayLessons: LiveData<List<LessonUi>> = _todayLessons

    private val _todayTasks = MutableLiveData<List<TaskUi>>()
    val todayTasks: LiveData<List<TaskUi>> = _todayTasks

    init {
        loadMockData()
    }

    private fun loadMockData() {
        _weekDays.value = listOf(
            WeekDay("Пн", 11, isSelected = false),
            WeekDay("Вт", 12, isSelected = false),
            WeekDay("Ср", 13, isSelected = true),
            WeekDay("Чт", 14, isSelected = false),
            WeekDay("Пт", 15, isSelected = false),
            WeekDay("Сб", 16, isSelected = false)
        )

        _todayLessons.value = listOf(
            LessonUi(
                subjectName = "Математический анализ",
                timeRange = "09:00–10:30",
                room = "Ауд. 305",
                teacher = "Проф. Иванов А.М.",
                accentColor = R.color.accent_blue
            ),
            LessonUi(
                subjectName = "Физика (Лекция)",
                timeRange = "10:45–12:15",
                room = "Ауд. 112",
                teacher = "Проф. Петрова Е.В.",
                accentColor = R.color.accent_coral
            ),
            LessonUi(
                subjectName = "Программирование",
                timeRange = "13:00–14:30",
                room = "Ауд. 408",
                teacher = "Доц. Сидоров К.П.",
                accentColor = R.color.accent_green
            )
        )

        _todayTasks.value = listOf(
            TaskUi(
                title = "Лабораторная работа №3",
                dueLabel = "Срок: Сегодня, 18:00",
                tagName = "Физика",
                tagColor = R.color.accent_coral
            ),
            TaskUi(
                title = "Домашнее задание №5",
                dueLabel = "Срок: До завтра",
                tagName = "Мат. анализ",
                tagColor = R.color.accent_blue
            )
        )
    }

    /** Called by the Fragment when a task checkbox is toggled. */
    fun onTaskCheckedChanged(position: Int, isChecked: Boolean) {
        val current = _todayTasks.value.orEmpty().toMutableList()
        if (position !in current.indices) return
        current[position] = current[position].copy(isDone = isChecked)
        _todayTasks.value = current
        // TODO (week 6): persist this change via TaskRepository.update(...)
    }
}
