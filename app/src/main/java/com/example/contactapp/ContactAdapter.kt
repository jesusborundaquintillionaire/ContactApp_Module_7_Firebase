package com.example.contactapp

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView

class ContactAdapter(private val context: Context, private val contacts: MutableList<Contact>) : BaseAdapter() {
    override fun getCount(): Int = contacts.size
    override fun getItem(position: Int): Any = contacts[position]
    override fun getItemId(position: Int): Long = position.toLong()

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view = convertView ?: LayoutInflater.from(context).inflate(android.R.layout.simple_list_item_1, parent, false)
        val contact = contacts[position]
        val textView = view.findViewById<TextView>(android.R.id.text1)
        textView.text = "${contact.name} - ${contact.phone}"

        view.setOnClickListener {
            (context as MainActivity).showContactDialog(contact)
        }

        return view
    }
}