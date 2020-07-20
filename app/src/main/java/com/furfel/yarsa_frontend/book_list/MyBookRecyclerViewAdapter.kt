package com.furfel.yarsa_frontend.book_list

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import com.furfel.yarsa_frontend.R

import com.furfel.yarsa_frontend.models.BookModel

/**
 * [RecyclerView.Adapter] that can display a [DummyItem].
 * TODO: Replace the implementation with code for your data type.
 */
class MyBookRecyclerViewAdapter(
    var values: List<BookModel>
) : RecyclerView.Adapter<MyBookRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_books, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = values[position]
        holder.titleView.text = item.title
        holder.authorView.text = item.author

        holder.deleteBtn.setOnClickListener{ onDeleteClickListener?.invoke(item.id) }
        holder.viewBtn.setOnClickListener { onViewClickListener?.invoke(item.id) }
    }

    override fun getItemCount(): Int = values.size

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val titleView = view.findViewById<TextView>(R.id.title)
        val authorView = view.findViewById<TextView>(R.id.author)

        val deleteBtn = view.findViewById<Button>(R.id.delete)
        val viewBtn = view.findViewById<Button>(R.id.view)
    }

    private var onDeleteClickListener: ((String) -> (Unit))? = null
    private var onViewClickListener: ((String) -> (Unit))? = null
}