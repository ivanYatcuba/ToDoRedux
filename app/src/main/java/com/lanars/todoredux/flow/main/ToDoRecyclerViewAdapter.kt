package com.lanars.todoredux.flow.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.lanars.todoredux.R
import com.lanars.todoredux.flow.main.model.ToDo
import kotlinx.android.synthetic.main.item_todo.view.*
import java.util.*

class ToDoRecyclerViewAdapter(val onToDoClicked: (ToDo, Int) -> Unit) : RecyclerView.Adapter<ToDoRecyclerViewAdapter.ToDoViewHolder>() {

    var data: List<ToDo> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ToDoViewHolder {
        return ToDoViewHolder(
                LayoutInflater.from(parent.context).inflate(R.layout.item_todo, parent, false)
        )
    }

    fun setItems(data: List<ToDo>) {
        this.data = data
        notifyDataSetChanged()
    }

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: ToDoViewHolder, position: Int) = holder.bind(data[position], position)

    inner class ToDoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(item: ToDo, position: Int) = with(itemView) {
            tvToDo.text = item.title
            itemView.setOnClickListener { onToDoClicked(item, position) }
            if (item.completed) {
                ivDone.visibility = View.VISIBLE
            } else {
                ivDone.visibility = View.GONE
            }
        }
    }
}