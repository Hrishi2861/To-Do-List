package app.hrishikesh.todolist

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class TaskDatabaseHandler(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    override fun onCreate(db: SQLiteDatabase?) {
        val createTable =
            "CREATE TABLE $TABLE_TASKS ($KEY_ID INTEGER PRIMARY KEY, $KEY_TITLE TEXT, $KEY_DESCRIPTION TEXT, $KEY_COMPLETED INTEGER)"
        db?.execSQL(createTable)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_TASKS")
        onCreate(db)
    }

    fun addTask(task: Task) {
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put(KEY_TITLE, task.title)
            put(KEY_DESCRIPTION, task.description)
            put(KEY_COMPLETED, if (task.isCompleted) 1 else 0)
        }
        db.insert(TABLE_TASKS, null, values)
        db.close()
    }

    fun getAllTasks(): MutableList<Task> {
        val taskList = mutableListOf<Task>()
        val db = this.readableDatabase
        val selectQuery = "SELECT * FROM $TABLE_TASKS"
        val cursor = db.rawQuery(selectQuery, null)

        if (cursor.moveToFirst()) {
            do {
                val task = Task(
                    id = cursor.getInt(cursor.getColumnIndex(KEY_ID)),
                    title = cursor.getString(cursor.getColumnIndex(KEY_TITLE)),
                    description = cursor.getString(cursor.getColumnIndex(KEY_DESCRIPTION)),
                    isCompleted = cursor.getInt(cursor.getColumnIndex(KEY_COMPLETED)) == 1
                )
                taskList.add(task)
            } while (cursor.moveToNext())
        }
        cursor.close()
        db.close()
        return taskList
    }

    companion object {
        private const val DATABASE_VERSION = 1
        private const val DATABASE_NAME = "TaskDatabase"
        private const val TABLE_TASKS = "tasks"
        private const val KEY_ID = "id"
        private const val KEY_TITLE = "title"
        private const val KEY_DESCRIPTION = "description"
        private const val KEY_COMPLETED = "completed"
    }
}
