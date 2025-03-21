package com.alekseykostyunin.courses.presentation.listcourses

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.alekseykostyunin.courses.data.datasource.BookmarkRepositoryImpl
import com.alekseykostyunin.courses.data.repository.CoursesRepositoryImpl
import com.alekseykostyunin.courses.databinding.FragmentListCoursesBinding
import com.alekseykostyunin.courses.domain.model.Course
import com.alekseykostyunin.courses.domain.repository.BookmarkRepository
import com.alekseykostyunin.courses.domain.repository.CoursesRepository
import com.alekseykostyunin.courses.presentation.bookmark.BookmarkViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class ListCoursesFragment : Fragment() {

    private var _binding: FragmentListCoursesBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentListCoursesBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val coursesRepository: CoursesRepository = CoursesRepositoryImpl
        val bookmarkRepository: BookmarkRepository = BookmarkRepositoryImpl
        val viewModelCourses = ListCoursesViewModel(requireContext(), coursesRepository)
        val viewModelBookmark = BookmarkViewModel(requireContext(), bookmarkRepository)
        val adapter = ListCoursesAdapter(requireContext())
        binding.recycler.adapter = adapter
        binding.recycler.layoutManager =
            LinearLayoutManager(
                requireContext(),
                LinearLayoutManager.VERTICAL,
                false
            )

        lifecycleScope.launch {
            viewModelBookmark.courses.collect { bookmarkList ->
                adapter.bookmarkCourses = bookmarkList
                adapter.notifyDataSetChanged()
            }
        }

        viewModelCourses.courses.onEach {
            adapter.courses = it

        }.launchIn(viewLifecycleOwner.lifecycleScope)

        binding.buttonSort.setOnClickListener {
            viewModelCourses.filterCourses()
            adapter.notifyDataSetChanged()
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