package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.myapplication.databinding.ActivityUpdateBinding

class UpdateNoteActivity : AppCompatActivity() {

    private  lateinit var binding : ActivityUpdateBinding
    private lateinit var db : NotesDatabaseHelper
    private var noteId : Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUpdateBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = NotesDatabaseHelper(this)

        noteId = intent.getIntExtra("note_id",-1)
        if(noteId == -1 ){
            finish()
            return
        }

        val note = db.getNoteById(noteId)
        binding.edtEditTitle.setText(note.title)
        binding.edtEditContentNote.setText(note.content)

        binding.btnSaveEditNote.setOnClickListener{
            val newTitle = binding.edtEditTitle.text.toString()
            val newContent = binding.edtEditContentNote.text.toString()
            val updateNote = Note(noteId,newTitle,newContent)
            db.updateNote(updateNote)
            Toast.makeText(this,"Changes saved",Toast.LENGTH_SHORT).show()
        }
    }
}