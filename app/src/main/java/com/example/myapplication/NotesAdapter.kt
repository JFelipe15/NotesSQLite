package com.example.myapplication

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView

class NotesAdapter(private var notes:List<Note>,context:Context) :
    RecyclerView.Adapter<NotesAdapter.NoteViewHolder>() {


        private val db:NotesDatabaseHelper = NotesDatabaseHelper(context)

    class NoteViewHolder(itemView: View) :RecyclerView.ViewHolder(itemView){
        val txtTitleCardView : TextView = itemView.findViewById(R.id.txtTitleCardView);
        val txtContentCardView : TextView = itemView.findViewById(R.id.txtContentCardView)
        val editButton : ImageView = itemView.findViewById(R.id.imgNoteEdit)
        val deleteButton : ImageView = itemView.findViewById(R.id.imgNoteDelete)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
       val view = LayoutInflater.from(parent.context).inflate(R.layout.cardview_notes, parent, false)
        return NoteViewHolder(view)

    }

    override fun getItemCount(): Int = notes.size


    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        val note = notes[position]
        holder.txtTitleCardView.text = note.title
        holder.txtContentCardView.text = note.content

        holder.editButton.setOnClickListener{
            val intent = Intent(holder.itemView.context,UpdateNoteActivity::class.java).apply {
                putExtra("note_id",note.id)
            }
            holder.itemView.context.startActivity(intent)
        }

        holder.deleteButton.setOnClickListener{
            db.deleteNote(note.id)
            refreshData(db.getAllNotes())
            Toast.makeText(holder.itemView.context,"Note deleted", Toast.LENGTH_SHORT).show()
        }
    }

    fun refreshData(newNotes : List<Note>){
        notes = newNotes
        notifyDataSetChanged()
    }
}