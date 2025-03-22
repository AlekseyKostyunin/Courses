package com.alekseykostyunin.courses.presentation.listcourses

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.alekseykostyunin.courses.R
import com.alekseykostyunin.courses.databinding.CourseItemBinding
import com.alekseykostyunin.courses.domain.model.Course
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.Random

class ListCoursesAdapter(
    private val context: Context
) : RecyclerView.Adapter<ListCoursesAdapter.ViewHolder>() {

    var onBookmarkClickListener: OnBookmarkClickListener? = null

    var courses = listOf<Course>()
        set(value) {
            val diffCallback = CourseDiffCallback(field, value)
            val diffResult = DiffUtil.calculateDiff(diffCallback)
            field = value
            diffResult.dispatchUpdatesTo(this)
        }

    var bookmarkCourses = listOf<Course>()
        set(value) {
            val diffCallback = CourseDiffCallback(field, value)
            val diffResult = DiffUtil.calculateDiff(diffCallback)
            field = value
            diffResult.dispatchUpdatesTo(this)
        }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private var binding: CourseItemBinding = CourseItemBinding.bind(itemView)
        var image = binding.imageCourse
        var rating = binding.rating
        var startDate = binding.startDate
        var iconBookmark = binding.iconBookmark
        var title = binding.title
        var description = binding.desc
        var price = binding.price
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.course_item,
            parent,
            false
        )
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = courses.size

    @SuppressLint("SetTextI18n", "UseCompatLoadingForDrawables")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val course = courses[position]
        val random = Random()
        val randomNumber = random.nextInt(3) + 1
        val imageName = "img$randomNumber"
        val resID = context.resources.getIdentifier(imageName, "drawable", context.packageName)
        val inputFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val date = inputFormat.parse(course.startDate)
        val outputFormat = SimpleDateFormat("d MMMM yyyy", Locale("ru"))
        val outputDateString = date?.let { outputFormat.format(it) }
        holder.apply {
            image.setImageResource(resID)
            image.post {
                val matrix = image.imageMatrix
                val scale: Float = image.width.toFloat() / image.drawable.intrinsicWidth
                matrix.setScale(scale, scale, 0f, 0f)
                image.imageMatrix = matrix
            }
            rating.text = course.rate
            startDate.text = outputDateString
            title.text = course.title
            description.text = course.text
            price.text = course.price + " \u20BD"

            if(course.id in bookmarkCourses.map { it.id }) {
                holder.iconBookmark.setColorFilter(ContextCompat.getColor(context, R.color.green))
                holder.iconBookmark.setImageDrawable(context.getDrawable(R.drawable.bookmark_filled))
            } else {
                holder.iconBookmark.setColorFilter(ContextCompat.getColor(context, R.color.white))
                holder.iconBookmark.setImageDrawable(context.getDrawable(R.drawable.bookmark))
            }

            iconBookmark.setOnClickListener {
                if(onBookmarkClickListener != null) {
                    onBookmarkClickListener?.onItemClick(course)
                }
            }
        }
    }

    interface OnBookmarkClickListener {
        fun onItemClick(course: Course)
    }
}

class CourseDiffCallback(
    private val oldList: List<Course>,
    private val newList: List<Course>
) : DiffUtil.Callback() {

    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].id == newList[newItemPosition].id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition] == newList[newItemPosition]
    }
}