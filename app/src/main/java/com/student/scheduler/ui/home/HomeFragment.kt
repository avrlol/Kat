package com.student.scheduler.ui.home

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.student.scheduler.R
import com.student.scheduler.adapters.LessonAdapter
import com.student.scheduler.adapters.TaskAdapter
import com.student.scheduler.adapters.WeekDayAdapter
import com.student.scheduler.viewmodel.HomeViewModel

/**
 * "Сегодня" — the app's home screen: greeting, week-day strip,
 * today's lessons and today's tasks. All content currently comes
 * from [HomeViewModel]'s mock data and will switch to Room-backed
 * LiveData once the database is in place (week 2 / week 4).
 */
class HomeFragment : Fragment(R.layout.fragment_home) {

    private val viewModel: HomeViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerWeekDays = view.findViewById<RecyclerView>(R.id.recycler_week_days)
        val recyclerLessons = view.findViewById<RecyclerView>(R.id.recycler_lessons)
        val recyclerTasks = view.findViewById<RecyclerView>(R.id.recycler_tasks)

        recyclerLessons.layoutManager = LinearLayoutManager(requireContext())
        recyclerTasks.layoutManager = LinearLayoutManager(requireContext())

        viewModel.weekDays.observe(viewLifecycleOwner) { days ->
            recyclerWeekDays.adapter = WeekDayAdapter(days) { position ->
                // TODO (week 5): selecting a day should filter lessons for that day
                // once Schedule screen and Room queries by date are in place.
            }
        }

        viewModel.todayLessons.observe(viewLifecycleOwner) { lessons ->
            recyclerLessons.adapter = LessonAdapter(lessons)
        }

        viewModel.todayTasks.observe(viewLifecycleOwner) { tasks ->
            recyclerTasks.adapter = TaskAdapter(tasks) { position, isChecked ->
                viewModel.onTaskCheckedChanged(position, isChecked)
            }
        }
    }
}
