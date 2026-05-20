package com.example.myanimecollection.ui.layout

import android.graphics.Paint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.myanimecollection.data.Todo
import com.example.myanimecollection.databinding.ItemTaskBinding

class TodoAdapter (
    private val onToggleDone: (Todo) -> Unit,
    private val onDelete: (Todo) -> Unit) : ListAdapter<Todo, TodoAdapter.TodoViewHolder>(DiffCallBack()) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): TodoViewHolder {
        val binding = ItemTaskBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TodoViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: TodoViewHolder,
        position: Int
    ) {
        val todo = getItem(position)
        with(holder.binding){
            textviewTitle.text = todo.title
            checkboxDone.isChecked = todo.isDone
            textviewTitle.paintFlags = if(todo.isDone)
                textviewTitle.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
            else
                textviewTitle.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()

            checkboxDone.setOnCheckedChangeListener { button, bool ->  onToggleDone(todo)}
            buttonDelete.setOnClickListener { onDelete(todo) }
        }
    }

    class TodoViewHolder(val binding: ItemTaskBinding): RecyclerView.ViewHolder(binding.root)

    class DiffCallBack : DiffUtil.ItemCallback<Todo>(){
        override fun areItemsTheSame(oldItem: Todo, newItem: Todo): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Todo, newItem: Todo): Boolean {
            return oldItem == newItem
        }
    }
}