package uz.frodo.sherlarsms

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

object MySharedPref {
    private lateinit var shPref: SharedPreferences
    fun init(context: Context){
        shPref = context.getSharedPreferences("DATA",Context.MODE_PRIVATE)
    }

    private inline fun SharedPreferences.edit(operation:(SharedPreferences.Editor) -> Unit){
        val editor = edit()
        operation(editor)
        editor.apply()
    }

    var list:ArrayList<Poem>
        get() {
            val json = shPref.getString("keyList",null)
            return if (json != null){
                val typeToken = object : TypeToken<MutableList<Poem>>() {}.type
                Gson().fromJson(json, typeToken)
            }else
                arrayListOf()
        }
        set(value) {
            val json = Gson().toJson(value)
            shPref.edit {
                if (json != null){
                    it.putString("keyList",json)
                }
            }
        }
    fun addPoem(poem: Poem) {
        var updatedPoemList = list
        println(updatedPoemList.toString())
        poem.liked = true
        updatedPoemList.add(poem)
        println(updatedPoemList.toString())
        list = updatedPoemList
    }
    fun removePoem(poem: Poem) {
        var updatedPoemList = list
        println(updatedPoemList.map { it.name })
        updatedPoemList.remove(updatedPoemList.find { it.text == poem.text })
        println(updatedPoemList.map { it.name })
        list = updatedPoemList
    }
}