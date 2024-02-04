package app.hrishikesh.todolist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class TaskAdapter(
    private var tasks: MutableList<Task>,
    private val listener: OnItemClickListener
) : RecyclerView.Adapter<TaskAdapter.TaskViewHolder>() {

    interface OnItemClickListener {
        fun onItemClicked(position: Int)
    }

    inner class TaskViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener {
        val textViewTitle: TextView = itemView.findViewById(R.id.textViewTitle)

        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            val position = adapterPosition
            if (position != RecyclerView.NO_POSITION) {
                listener.onItemClicked(position)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item_task, parent, false)
        return TaskViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val currentItem = tasks[position]
        holder.textViewTitle.text = currentItem.title
    }

    override fun getItemCount(): Int {
        return tasks.size
    }

    fun updateTasks(updatedTasks: MutableList<Task>) {
        tasks = updatedTasks
        notifyDataSetChanged()
    }
}
