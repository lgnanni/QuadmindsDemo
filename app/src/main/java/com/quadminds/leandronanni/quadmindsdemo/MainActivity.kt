package com.quadminds.leandronanni.quadmindsdemo

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.widget.EditText
import android.widget.Toast
import com.quadminds.leandronanni.quadmindsdemo.adapters.NotesAdapter
import com.quadminds.leandronanni.quadmindsdemo.adapters.NotesAdapterListener
import com.quadminds.leandronanni.quadmindsdemo.pojo.Note
import com.quadminds.leandronanni.quadmindsdemo.pojo.Notes
import com.quadminds.leandronanni.quadmindsdemo.presenter.NotesPresenter
import com.quadminds.leandronanni.quadmindsdemo.view.NotesView
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import kotlinx.android.synthetic.main.notes_layout.*


class MainActivity : AppCompatActivity(), NotesAdapterListener, NotesView {

    private lateinit var presenter: NotesPresenter

    private lateinit var dialog: AlertDialog

    private lateinit var notes: Notes
    private var adapter: NotesAdapter? = null

    private var deleteIndex: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        presenter = NotesPresenter(this)

        presenter.getNotes()
        fab.setOnClickListener { _ ->
            openNoteDialog(Note("","",""))
        }

        swipe_container.setOnRefreshListener {
            presenter.getNotes()
        }
    }

    override fun openNoteDialog(note: Note) {
        dialog = AlertDialog.Builder(this).create()

        val li = this.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view = li.inflate(R.layout.notes_layout, null, false)

        dialog.setView(view)

        val title: EditText = view.findViewById(R.id.note_dialog_title)
        val content: EditText = view.findViewById(R.id.note_dialog_content)

        title.setText(note.title)
        content.setText(note.content)

        if (note.title.isEmpty()) {
            dialog.setTitle(getString(R.string.new_note))
        } else {
            dialog.setTitle(getString(R.string.modify_note))
        }

        dialog.setButton(AlertDialog.BUTTON_NEGATIVE, getString(R.string.cancel)) { dialog, _ ->  dialog.dismiss()}
        dialog.setButton(AlertDialog.BUTTON_POSITIVE, getString(R.string.save)) { _, _ ->
        }

        dialog.show()

        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener {_ ->
            if (title.text.isEmpty() || content.text.isEmpty()) {
                Toast.makeText(dialog.context, getString(R.string.empty_error),
                        Toast.LENGTH_LONG).show()
            } else {
                uploadNote(note, title.text.toString(), content.text.toString())
            }
        }
    }

    override fun openNoteRemoveDialog(note: Note) {
        val deleteDialog = AlertDialog.Builder(this).create()

        deleteDialog.setTitle(getString(R.string.delete))

        deleteDialog.setMessage(getString(R.string.delete_description))

        deleteDialog.setButton(AlertDialog.BUTTON_NEGATIVE, getString(R.string.cancel)) { dialog, _ ->  dialog.dismiss()}
        deleteDialog.setButton(AlertDialog.BUTTON_POSITIVE, getString(R.string.delete)) { _, _ ->
            deleteIndex = notes.data.indexOf(note)
            presenter.deleteNote(note.id)
            deleteDialog.dismiss()}

        deleteDialog.show()
    }

    override fun showProgress() {
        progress.visibility = View.VISIBLE
    }

    override fun hideProgress() {
        progress.visibility = View.GONE
    }

    override fun getNotesOk(notes: Notes) {

        if (notes.data.size > 0) {
            empty_notes.visibility = View.GONE
            this.notes = notes
            if (adapter != null) {
                adapter!!.setNotes(notes)
                swipe_container.isRefreshing = false
            } else {
                adapter = NotesAdapter(this, this, notes.data, notes.data.size)

                notes_recyclerview.adapter = adapter

                val layoutManager = LinearLayoutManager(this,
                        LinearLayoutManager.VERTICAL, false)
                notes_recyclerview.layoutManager = layoutManager
                notes_recyclerview.itemAnimator = DefaultItemAnimator()
            }
        } else {
            notes_recyclerview.visibility = View.GONE
        }
    }

    override fun saveNoteOk(note: Note?) {

        var noteIndex: Int = -1

        for (i in notes.data.indices) {
            if (notes.data[i].id.contentEquals(note!!.id)) {
                noteIndex = i
                break
            }
        }

        if (noteIndex > -1) {

            adapter!!.modify(noteIndex)
            notes.data.removeAt(noteIndex)
            notes.data.add(noteIndex, note!!)
        } else {
            adapter!!.add(note)
        }

        dialog.dialog_progress.visibility = View.GONE

        dialog.dismiss()
    }

    override fun removeNoteOk() {
        adapter!!.remove(notes.data[deleteIndex])
    }

    override fun apiError(error: String?) {
        dialog.dismiss()

        Toast.makeText(this, error, Toast.LENGTH_LONG).show()
    }

    fun uploadNote(note: Note, title: String, content: String?) {

        dialog.dialog_progress.visibility = View.VISIBLE

        dialog.note_dialog_title.visibility = View.GONE
        dialog.note_dialog_content.visibility = View.GONE
        dialog.note_dialog_divisor.visibility = View.GONE
        
        if(note.id.isEmpty()) {
            presenter.saveNewNote(title, content)
        } else {
            presenter.modifyNote(note.id, title, content)
        }
    }
}
