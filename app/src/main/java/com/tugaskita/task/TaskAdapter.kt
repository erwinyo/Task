package com.tugaskita.task

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.RadioButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.task_list_item.view.*

class TaskAdapter(val items : ArrayList<String>, val task_list : RecyclerView, val hang_layout : LinearLayout, val context: Context) : RecyclerView.Adapter<ViewHolder>() {
    val session : Session = Session(context)

    // Gets the number of animals in the list
    override fun getItemCount(): Int {
        return items.size
    }

    // Inflates the item views
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(context).inflate(
                R.layout.task_list_item,
                parent,
                false
            )
        )
    }

    // Binds each animal in the ArrayList to a view
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.task.text = items.get(position)
        holder.check.setOnClickListener {
            var sess = session.getStringsession("tasks")?.split("%\$%") as ArrayList
            println("LAST $sess")
            sess.removeAt(position)

            // reset session
            session.setSession("", "tasks")
            for (i in sess) {
                // exclude last index
                if (i.isEmpty())
                    break
                val last_session = session.getStringsession("tasks")
                session.setSession("$last_session$i%\$%", "tasks")
            }
            session.loadTasks(items, task_list, hang_layout)
        }
    }
}

class ViewHolder (view: View) : RecyclerView.ViewHolder(view) {
    // Holds the TextView that will add each animal to
    val task: TextView = view.task
    val check: RadioButton = view.check
}