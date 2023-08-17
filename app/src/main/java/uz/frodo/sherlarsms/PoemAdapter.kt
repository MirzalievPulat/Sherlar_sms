package uz.frodo.sherlarsms

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder

class PoemAdapter(var list:ArrayList<Poem>,var click: Click):RecyclerView.Adapter<ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        MySharedPref.init(parent.context)
        val item = LayoutInflater.from(parent.context).inflate(R.layout.item,parent,false)
        return object :ViewHolder(item){}
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.findViewById<TextView>(R.id.item_name).text = list[position].name
        holder.itemView.findViewById<TextView>(R.id.item_text).text = list[position].text
        if (list[position].liked) {
            holder.itemView.findViewById<ImageView>(R.id.red_heart).visibility = View.VISIBLE
        }
        holder.itemView.findViewById<CardView>(R.id.card).setOnClickListener {
            click.click(list[position])
        }
    }
    fun add(poem: Poem){
        MySharedPref.addPoem(poem)
        list.clear()
        list = MySharedPref.list
        notifyDataSetChanged()
    }
    fun remove(poem: Poem){
        MySharedPref.removePoem(poem)
        list.clear()
        list = MySharedPref.list
        notifyDataSetChanged()
    }

    interface Click{
        fun click(poem: Poem)
    }
}