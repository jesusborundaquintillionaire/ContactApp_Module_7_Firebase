package com.example.contactapp

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import timber.log.Timber

class MainActivity : AppCompatActivity() {
    lateinit var sharedPreferencesHelper: SharedPreferencesHelper
    lateinit var contacts: MutableList<Contact>
    private lateinit var adapter: ArrayAdapter<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        sharedPreferencesHelper = SharedPreferencesHelper(this)
        contacts = sharedPreferencesHelper.getContacts().toMutableList()

        val nameEditText: EditText = findViewById(R.id.nameEditText)
        val phoneEditText: EditText = findViewById(R.id.phoneEditText)
        val addButton: Button = findViewById(R.id.addButton)
        val contactsListView: ListView = findViewById(R.id.contactsListView)

        adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, contacts.map { "${it.name} - ${it.phone}" })
        contactsListView.adapter = adapter

        addButton.setOnClickListener {
            try {
                val name = nameEditText.text.toString()
                val phone = phoneEditText.text.toString()
                if (name.isNotEmpty() && phone.isNotEmpty()) {
                    val contact = Contact(contacts.size, name, phone)
                    contacts.add(contact)
                    sharedPreferencesHelper.saveContacts(contacts)
                    adapter.add("${contact.name} - ${contact.phone}")
                    nameEditText.text.clear()
                    phoneEditText.text.clear()
                }
            } catch (e: Exception) {
                Timber.e(e, "Error adding contact")
            }
        }

        contactsListView.setOnItemClickListener { _, _, position, _ ->
            try {
                val contact = contacts[position]
                nameEditText.setText(contact.name)
                phoneEditText.setText(contact.phone)
                addButton.text = "Update Contact"
                addButton.setOnClickListener {
                    try {
                        contact.name = nameEditText.text.toString()
                        contact.phone = phoneEditText.text.toString()
                        sharedPreferencesHelper.saveContacts(contacts)
                        adapter.clear()
                        adapter.addAll(contacts.map { "${it.name} - ${it.phone}" })
                        nameEditText.text.clear()
                        phoneEditText.text.clear()
                        addButton.text = "Add Contact"
                        addButton.setOnClickListener {
                            val name = nameEditText.text.toString()
                            val phone = phoneEditText.text.toString()
                            if (name.isNotEmpty() && phone.isNotEmpty()) {
                                val contact = Contact(contacts.size, name, phone)
                                contacts.add(contact)
                                sharedPreferencesHelper.saveContacts(contacts)
                                adapter.add("${contact.name} - ${contact.phone}")
                                nameEditText.text.clear()
                                phoneEditText.text.clear()
                            }
                        }
                    } catch (e: Exception) {
                        Timber.e(e, "Error updating contact")
                    }
                }
            } catch (e: Exception) {
                Timber.e(e, "Error selecting contact")
            }
        }

        contactsListView.setOnItemLongClickListener { _, _, position, _ ->
            try {
                contacts.removeAt(position)
                sharedPreferencesHelper.saveContacts(contacts)
                adapter.clear()
                adapter.addAll(contacts.map { "${it.name} - ${it.phone}" })
                true
            } catch (e: Exception) {
                Timber.e(e, "Error deleting contact")
                false
            }
        }

        // Creates a button that mimics a crash when pressed
        val crashButton = Button(this)
        crashButton.text = "Test Crash"
        crashButton.setOnClickListener {
            throw RuntimeException("Test Crash") // Force a crash
        }

        addContentView(crashButton, ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT))
    }

    fun showContactDialog(contact: Contact) {

    }
}