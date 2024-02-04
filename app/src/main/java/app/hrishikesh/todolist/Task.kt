package app.hrishikesh.todolist

data class Task(
    var id: Int = 0,
    var title: String,
    var description: String = "",
    var isCompleted: Boolean = false
)
