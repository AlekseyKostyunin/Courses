package com.alekseykostyunin.courses.presentation.bookmark

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.alekseykostyunin.courses.data.datasource.BookmarkRepositoryImpl
import com.alekseykostyunin.courses.databinding.FragmentBookmarkBinding
import com.alekseykostyunin.courses.domain.model.Course
import com.alekseykostyunin.courses.domain.repository.BookmarkRepository
import com.alekseykostyunin.courses.presentation.listcourses.ListCoursesAdapter
import kotlinx.coroutines.launch

class BookmarkFragment : Fragment() {

    private var _binding: FragmentBookmarkBinding? = null
    private val binding get() = _binding!!

    private val bookmarkRepository: BookmarkRepository = BookmarkRepositoryImpl

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBookmarkBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val viewModelBookmark = BookmarkViewModel(requireContext(), bookmarkRepository)
        val adapter = ListCoursesAdapter(requireContext())

        binding.recyclerBookmark.adapter = adapter
        binding.recyclerBookmark.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

        lifecycleScope.launch {
            viewModelBookmark.courses.collect { courses ->
                adapter.courses = courses
                adapter.bookmarkCourses = courses
            }
        }

        adapter.onBookmarkClickListener = object : ListCoursesAdapter.OnBookmarkClickListener {
            override fun onItemClick(course: Course) {
                if (course.hasLike) {
                    viewModelBookmark.deleteCourseOfBookmark(course)
                } else {
                    viewModelBookmark.addCourseOfBookmark(course)
                }

            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}