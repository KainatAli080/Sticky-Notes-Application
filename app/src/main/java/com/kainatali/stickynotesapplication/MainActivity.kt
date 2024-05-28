package com.kainatali.stickynotesapplication

import android.annotation.SuppressLint
import android.appwidget.AppWidgetManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.android.material.floatingactionbutton.FloatingActionButton
import android.content.ComponentName
import android.graphics.Paint
import android.graphics.Typeface
import android.widget.RemoteViews

class MainActivity : AppCompatActivity() {

    private lateinit var editText: EditText
    private lateinit var saveBtn: FloatingActionButton
    private lateinit var decreaseSize: Button
    private lateinit var increaseSize: Button
    private lateinit var displayTextSize: TextView
    private lateinit var boldBtn: Button
    private lateinit var italicBtn: Button
    private lateinit var underlineBtn: Button
    private var currentSize: Float = 0.0F
    private var stickyNote: StickyNote = StickyNote()

    companion object {
        const val MAX_SIZE = 50
        const val MIN_SIZE = 12
    }

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        editText = findViewById(R.id.edit_text_id)
        saveBtn = findViewById(R.id.save_btn_id)
        decreaseSize = findViewById(R.id.decreaseSize_btn)
        increaseSize = findViewById(R.id.increaseSize_btn)
        displayTextSize = findViewById(R.id.text_size_display)
        boldBtn = findViewById(R.id.bold_txt_btn)
        italicBtn = findViewById(R.id.italic_txt_btn)
        underlineBtn = findViewById(R.id.underline_txt_btn)

        currentSize = editText.textSize.toFloat()
        displayTextSize.text = currentSize.toString()

        increaseSize.setOnClickListener { increaseSizeOperation() }
        decreaseSize.setOnClickListener { decreaseSizeOperation() }
        boldBtn.setOnClickListener { makeTextBold() }
        italicBtn.setOnClickListener { makeTextItalic() }
        underlineBtn.setOnClickListener { makeTextUnderline() }
    }

    fun increaseSizeOperation(){
        if (currentSize < MAX_SIZE) {
            currentSize++
            displayTextSize.text = currentSize.toString()
            editText.textSize = currentSize.toFloat()
        }
        else{
            Toast.makeText(this, "Text size cannot be made bigger...", Toast.LENGTH_SHORT).show()
        }
    }
    fun decreaseSizeOperation(){
        if (currentSize > MIN_SIZE) {
            currentSize--
            displayTextSize.text = currentSize.toString()
            editText.textSize = currentSize.toFloat()
        }
        else{
            Toast.makeText(this, "Text size cannot be made smaller...", Toast.LENGTH_SHORT).show()
        }
    }
    fun makeTextBold(){
        if(editText.typeface.isBold){
            // meaning our text is already bold
            editText.setTypeface(Typeface.DEFAULT)
        }
        else{
            editText.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD))
        }
    }
    fun makeTextItalic(){
        if(editText.typeface.isItalic){
            // meaning our text is already bold
            editText.setTypeface(Typeface.DEFAULT)
        }
        else{
            editText.setTypeface(Typeface.defaultFromStyle(Typeface.ITALIC))
        }
    }
    fun makeTextUnderline(){
        if(editText.paintFlags == 8){
            //meaning already underlined
            editText.paintFlags = editText.paintFlags and Paint.UNDERLINE_TEXT_FLAG.inv()
        }
        else{
            editText.paintFlags = Paint.UNDERLINE_TEXT_FLAG
        }
    }

    // Action to perform when save button is clicked
    fun saveNote(view: View) {
        stickyNote.setStickyNoteDataToFile(editText.text.toString(), this)
        updateWidget()
        Toast.makeText(this, "Note updated...", Toast.LENGTH_SHORT).show()
    }
    fun updateWidget(){
        var manager = AppWidgetManager.getInstance(this)
        var remoteView = RemoteViews(this.packageName, R.layout.homescreen_widget_layout)
        var compName = ComponentName(this, AppWidget::class.java)
        remoteView.setTextViewText(R.id.widgetTextViewID, editText.text.toString())
        manager.updateAppWidget(compName, remoteView)
    }
}