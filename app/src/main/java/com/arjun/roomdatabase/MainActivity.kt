package com.arjun.roomdatabase

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.lifecycle.Observer
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.Date

class MainActivity : AppCompatActivity() {

    lateinit var database: ContactDatabase

    val text : TextView
    get() = findViewById(R.id.text)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        database = ContactDatabase.getDatabase(this)

        database.contactDao().getContact().observe(this){
            text.text = it.toString()
            Log.d("Cheezy", it.toString())
    }

    }

    fun getData(view: View) {

        GlobalScope.launch {
            database.contactDao().insertContact(Contact(0, "Hii", "12345", Date(), 1))
        }}
}