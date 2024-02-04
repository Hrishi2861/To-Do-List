import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var taskAdapter: TaskAdapter
    private lateinit var taskDatabaseHandler: TaskDatabaseHandler

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        taskDatabaseHandler = TaskDatabaseHandler(this)
        taskAdapter = TaskAdapter(taskDatabaseHandler.getAllTasks(), this)

        recyclerViewTasks.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = taskAdapter
        }

        buttonAddTask.setOnClickListener {
            val title = editTextTaskTitle.text.toString()
            val description = editTextTaskDescription.text.toString()
            val task = Task(title = title, description = description)
            taskDatabaseHandler.addTask(task)
            taskAdapter.updateTasks(taskDatabaseHandler.getAllTasks())
            clearFields()
        }
    }

    private fun clearFields() {
        editTextTaskTitle.text.clear()
        editTextTaskDescription.text.clear()
    }
}
