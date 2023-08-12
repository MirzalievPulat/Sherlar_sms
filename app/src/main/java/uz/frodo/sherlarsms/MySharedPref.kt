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
        val updatedPoemList = list
        updatedPoemList.add(poem)
        list = updatedPoemList
    }
}