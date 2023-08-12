package uz.frodo.sherlarsms

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import uz.frodo.sherlarsms.databinding.FragmentMainBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [MainFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class MainFragment : Fragment() {
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
        MySharedPref.init(this.requireContext())
        val binding = FragmentMainBinding.inflate(layoutInflater)

        binding.savedCount.text = MySharedPref.list.size.toString()

        val options = NavOptions.Builder()
        options.setEnterAnim(androidx.transition.R.anim.abc_fade_in)
        options.setExitAnim(androidx.appcompat.R.anim.abc_fade_out)
        options.setPopEnterAnim(androidx.transition.R.anim.abc_fade_in)
        options.setPopExitAnim(androidx.transition.R.anim.abc_fade_out)

        binding.cardNews.setOnClickListener {
            val bundle = Bundle()
            bundle.putString("second","cardNews")
            findNavController().navigate(R.id.secondFragment,bundle,options.build())
        }
        binding.cardSaved.setOnClickListener {
            val bundle = Bundle()
            bundle.putString("second","cardSaved")
            findNavController().navigate(R.id.secondFragment,bundle,options.build())
        }
        binding.cardLove.setOnClickListener {
            val bundle = Bundle()
            bundle.putString("second","cardLove")
            findNavController().navigate(R.id.secondFragment,bundle,options.build())
        }
        binding.cardLonging.setOnClickListener {
            val bundle = Bundle()
            bundle.putString("second","cardLonging")
            findNavController().navigate(R.id.secondFragment,bundle,options.build())
        }
        binding.cardCongrats.setOnClickListener {
            val bundle = Bundle()
            bundle.putString("second","cardCongrats")
            findNavController().navigate(R.id.secondFragment,bundle,options.build())
        }
        binding.cardParents.setOnClickListener {
            val bundle = Bundle()
            bundle.putString("second","cardParents")
            findNavController().navigate(R.id.secondFragment,bundle,options.build())
        }
        binding.cardFriendship.setOnClickListener {
            val bundle = Bundle()
            bundle.putString("second","cardFriendship")
            findNavController().navigate(R.id.secondFragment,bundle,options.build())
        }
        binding.cardJokes.setOnClickListener {
            val bundle = Bundle()
            bundle.putString("second","cardJokes")
            findNavController().navigate(R.id.secondFragment,bundle,options.build())
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
         * @return A new instance of fragment MainFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            MainFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}