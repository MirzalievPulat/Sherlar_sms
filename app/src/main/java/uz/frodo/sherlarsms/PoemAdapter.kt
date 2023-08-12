package uz.frodo.sherlarsms

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder

class PoemAdapter(var list:ArrayList<Poem>,var click: Click):RecyclerView.Adapter<ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val item = LayoutInflater.from(parent.context).inflate(R.layout.item,parent,false)
        return object :ViewHolder(item){}
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.findViewById<TextView>(R.id.item_name).text = list[position].name
        holder.itemView.findViewById<TextView>(R.id.item_text).text = list[position].text
        holder.itemView.findViewById<CardView>(R.id.card).setOnClickListener {
            click.click(position)
        }
    }

    interface Click{
        fun click(position: Int)
    }
}