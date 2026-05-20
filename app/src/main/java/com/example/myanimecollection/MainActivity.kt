package com.example.myanimecollection

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.enableEdgeToEdge
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myanimecollection.databinding.ActivityMainBinding
import com.example.myanimecollection.ui.layout.TodoAdapter
import com.example.myanimecollection.viewmodel.TodoViewModel

class MainActivity : ComponentActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var  viewModel: TodoViewModel
    private lateinit var adapter: TodoAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this)[TodoViewModel::class.java]

        adapter = TodoAdapter(
            onToggleDone = {viewModel.toggleDone(it)},
            onDelete = {viewModel.deleteTodo(it)}
         )

        binding.rvTodos.adapter = adapter

        binding.rvTodos.layoutManager = LinearLayoutManager(this)


        binding.btnAdd.setOnClickListener {
            val text = binding.etTodo.editText?.text.toString()
            if(text.isNotBlank())
            {
                viewModel.insertTodo(text)
                binding.etTodo.editText?.text?.clear()
            }
        }

        viewModel.todos.observe(this){
            todos -> adapter.submitList(todos)
        }
    }
}