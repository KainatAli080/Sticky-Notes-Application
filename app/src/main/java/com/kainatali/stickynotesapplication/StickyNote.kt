package com.kainatali.stickynotesapplication

import android.content.Context
import java.io.BufferedReader
import java.io.File
import java.io.FileOutputStream
import java.io.FileReader

class StickyNote {
    private val fileName: String = "stickyNote.txt"
    private lateinit var file: File
    private lateinit var text: StringBuilder
    private lateinit var buffer: BufferedReader

    // used for writing data to a file in the form of a sequence of bytes
    private lateinit var fos: FileOutputStream

    public fun getStickyNoteDataFromFile(context: Context): String{
        file = File("${context.filesDir.path}/$fileName")
        text = StringBuilder()
        try{
            buffer = BufferedReader(FileReader(file))
            var lineText: String = ""
            while(buffer.readLine().also { lineText = it } != null){
                text.append(lineText)
                text.append("\n")
            }
            // always close this
            buffer.close()
        }
        catch (e: Exception){
            e.printStackTrace()
        }
        return text.toString()
    }

    public fun setStickyNoteDataToFile(textToSave: String, context: Context){
        val text:String = textToSave
        try{
            // this opens or creates a files on the apps internal storage
            fos = context.applicationContext.openFileOutput(fileName, Context.MODE_PRIVATE)
            fos.write(text.toByteArray())
        } catch(e: Exception){
            e.printStackTrace()
        } finally {
            if(fos != null){
                try{
                    fos.close()
                }catch(e: Exception){
                    e.printStackTrace()
                }
            }
        }
    }
}