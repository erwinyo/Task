package com.tugaskita.task

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import android.view.View
import android.widget.EditText
import android.widget.LinearLayout
import androidx.recyclerview.widget.RecyclerView

class Session(cntx: Context?) {
    private val prefs: SharedPreferences

    fun sessionCheckKeyword(keyword: String?): Boolean {
        return prefs.contains(keyword)
    }

    // Store
    fun setSession(data: String?, key: String?) {
        val editor = prefs.edit()
        editor.putString(key, data)
        editor.apply()
    }

    fun getStringsession(key: String?): String? {
        return prefs.getString(key, null)
    }

    fun loadTasks(task : ArrayList<String>, task_list : RecyclerView, hang_layout : LinearLayout) {
        // Empty the list
        task.clear()
        // Load session
        if (sessionCheckKeyword("tasks")) {
            val t = getStringsession("tasks")?.split("%\$%")
            for (x in t!!) {
                // exclude the last index
                if (x.isEmpty())
                    break
                task.add(x.split("#\$#")[0])
            }
        }
        // Notified change
        task_list.removeAllViews()
        task_list.adapter?.notifyDataSetChanged()

        if (task.isEmpty()) {
            hang_layout.visibility = View.VISIBLE
        } else {
            hang_layout.visibility = View.INVISIBLE
        }
    }

    fun addMore(task : ArrayList<String>, task_textbox : EditText): Int {
        val newTask : String = task_textbox.text.toString()
        if (newTask != "") {
            // Add New Task To Array
            task.add(newTask)
            val lastSession = getStringsession("tasks")
            setSession("$lastSession$newTask#$#unchecked%$%", "tasks")
            // reset
            task_textbox.setText("")
            return 1
        }
        return 0
    }

    init { // TODO Auto-generated constructor stub
        prefs = PreferenceManager.getDefaultSharedPreferences(cntx)
    }
}