package uz.frodo.sherlarsms

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Gravity
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.navigation.fragment.findNavController
import uz.frodo.sherlarsms.databinding.DialogBinding
import uz.frodo.sherlarsms.databinding.FragmentLikedBinding
import uz.frodo.sherlarsms.databinding.FragmentSecondBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [LikedFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class LikedFragment : Fragment() {
    lateinit var list:ArrayList<Poem>
    lateinit var adapter: PoemAdapter
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        MySharedPref.init(requireContext())
        val binding = FragmentLikedBinding.inflate(layoutInflater)

        list = MySharedPref.list

        adapter = PoemAdapter(list,object :PoemAdapter.Click{
            override fun click(poem: Poem) {
                val dialog = AlertDialog.Builder(requireContext()).create()
                val custom = DialogBinding.inflate(layoutInflater)
                custom.nameDialog.text = poem.name
                custom.textDialog.text = poem.text

                val a = MySharedPref.list.any { it.text == poem.text }
                if (a){
                    custom.heart.isChecked = true
                }

                dialog.setView(custom.root)


                custom.smsDialog.setOnClickListener {
                    val intent = Intent(Intent.ACTION_MAIN)
                    intent.addCategory(Intent.CATEGORY_APP_MESSAGING)
                    startActivity(intent)


                    val clipboardManager = requireContext().getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                    val clip = ClipData.newPlainText("Label for Clip", poem.text)
                    clipboardManager.setPrimaryClip(clip)
                }

                custom.heart.setOnCheckedChangeListener { buttonView, isChecked ->
                    if (isChecked){
                        adapter.add(poem)
                    }else{
                        adapter.remove(poem)
                    }
                }
                custom.shareDialog.setOnClickListener {
                    val i = Intent(Intent.ACTION_SEND)
                    i.type = "text/plain"
                    i.putExtra(Intent.EXTRA_TEXT,poem.text)
                    startActivity(Intent.createChooser(i,"Share using"))
                }
                custom.copyDialog.setOnClickListener {
                    val clipboardManager = requireContext().getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                    val clip = ClipData.newPlainText("Label for Clip", poem.text)
                    clipboardManager.setPrimaryClip(clip)
                    Toast.makeText(this@LikedFragment.context, "Copied", Toast.LENGTH_SHORT).show()
                }


                dialog.window?.setGravity(Gravity.BOTTOM)
                dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                dialog.show()
            }

        })
        binding.rv.adapter = adapter

        binding.arrow.setOnClickListener {
            findNavController().popBackStack()
        }
        return binding.root
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment LikedFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            LikedFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}