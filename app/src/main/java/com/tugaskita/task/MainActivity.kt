package com.tugaskita.task

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_main.*

// Select All Component in the .xml view
class MainActivity : AppCompatActivity() {
    // Initializing an empty ArrayList to be filled with animals
    var task: ArrayList<String> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val session : Session = Session(this)

        // task_textbox onTextListener
        task_textbox.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(s: Editable) {

            }

            override fun beforeTextChanged(s: CharSequence, start: Int,
                                           count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence, start: Int,
                                       before: Int, count: Int) {
                if (count >= 50) {
                    Toast.makeText(this@MainActivity, "sorry, the writing limit is only 50 letters", Toast.LENGTH_SHORT).show()
                }
            }
        })

        // add_new_task listener
        add_new_task_button.setOnClickListener {
            if (session.addMore(task, task_textbox) == 1) {
                if (hang_layout.visibility == View.VISIBLE) {
                    hang_layout.visibility = View.INVISIBLE
                }
                hideKeyboard()
            }
        }

        // Creates a vertical Layout Manager
        task_list.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)

        // You can use GridLayoutManager if you want multiple columns. Enter the number of columns as a parameter.
        // rv_animal_list.layoutManager = GridLayoutManager(this, 2)

        // Access the RecyclerView Adapter and load the data into it
        task_list.adapter = TaskAdapter(task, task_list, hang_layout, this)

        // call it
        session.loadTasks(task, task_list, hang_layout)
    }

    fun Activity.hideKeyboard() {
        hideKeyboard(currentFocus ?: View(this))
    }

    fun Context.hideKeyboard(view: View) {
        val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
    }
}
