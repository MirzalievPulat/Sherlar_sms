package uz.frodo.sherlarsms

import android.content.ClipData
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.content.ClipboardManager
import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import uz.frodo.sherlarsms.databinding.DialogBinding
import uz.frodo.sherlarsms.databinding.FragmentSecondBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [SecondFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class SecondFragment : Fragment() {
    lateinit var list:ArrayList<Poem>
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
        val binding = FragmentSecondBinding.inflate(layoutInflater)

        when(arguments?.getString("second")){
            "cardNews" -> {
                loadNews()
                binding.name.text = "YANGI \nSHE'RLAR"
            }
            "cardSaved" -> {
                loadSaved()
                binding.name.text = "SAQLANGAN \nSHE'RLAR"
            }
            "cardLove" -> {
                loadLove()
                binding.name.text = "SEVGI \nSHE'RLARI"
            }
            "cardLonging" -> {
                loadLonging()
                binding.name.text = "SOG'INCH \nARMON"
            }
            "cardCongrats" -> {
                loadCongrats()
                binding.name.text = "TABRIK \nSHE'RLAR"
            }
            "cardParents" -> {
                loadParents()
                binding.name.text = "OTA-ONA \nHAQIDA"
            }
            "cardFriendship" -> {
                loadFriendship()
                binding.name.text = "DO'STLIK \nSHERLARI"
            }
            "cardJokes" -> {
                loadJokes()
                binding.name.text = "HAZIL \nSHE'RLAR"
            }
        }

        binding.rv.adapter = PoemAdapter(list,object :PoemAdapter.Click{
            override fun click(position: Int) {
                val dialog = this@SecondFragment.context?.let { AlertDialog.Builder(it).create()}
                val custom = DialogBinding.inflate(layoutInflater)
                custom.nameDialog.text = list[position].name
                custom.textDialog.text = list[position].text
                dialog?.setView(custom.root)

                custom.smsDialog.setOnClickListener {
                    val intent = Intent(Intent.ACTION_MAIN)
                    intent.addCategory(Intent.CATEGORY_APP_MESSAGING)
                    startActivity(intent)


                    val clipboardManager = requireContext().getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                    val clip = ClipData.newPlainText("Label for Clip", list[position].text)
                    clipboardManager.setPrimaryClip(clip)
                }
                custom.heartDialog.setOnClickListener {
                    val a = MySharedPref.list.any { it.text == list[position].text }
                    if (!a){
                        MySharedPref.addPoem(list[position])
                        Toast.makeText(requireContext(), "Saved", Toast.LENGTH_SHORT).show()
                    }else{
                        Toast.makeText(requireContext(), "Alredy saved", Toast.LENGTH_SHORT).show()
                    }

                }
                custom.shareDialog.setOnClickListener {
                    val i = Intent(Intent.ACTION_SEND)
                    i.type = "text/plain"
                    i.putExtra(Intent.EXTRA_TEXT,list[position].text)
                    startActivity(Intent.createChooser(i,"Share using"))
                }
                custom.copyDialog.setOnClickListener {
                    val clipboardManager = requireContext().getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                    val clip = ClipData.newPlainText("Label for Clip", list[position].text)
                    clipboardManager.setPrimaryClip(clip)
                    Toast.makeText(this@SecondFragment.context, "Copied", Toast.LENGTH_SHORT).show()
                }
                dialog?.window?.setGravity(Gravity.BOTTOM)
                dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                dialog?.show()
            }

        })

        binding.arrow.setOnClickListener {
            findNavController().popBackStack()
        }

        return binding.root
    }

    private fun loadJokes() {
        list = ArrayList()
        list.add(Poem("Piyola va choynak","Garchi shuncha mag'rur tursa ham,\n" +
                "Ayoliga egilar erkak.\n" +
                "Shunday ekan manmanlik nechun,\n" +
                "Ularga riyo nimaga kerak.?\n" +
                "\n" +
                "Kamtarin bo'l ayol,\n" +
                "Bir qadam chiqma ostonangdan.\n" +
                "Erkak zotin bilmaysanda,\n" +
                "Erib ketar \"Hop\" so'zingdan."))
        list.add(Poem("Nozli qiz","Sizgacha ham quyosh bo'lgan oy bo'lgan\n" +
                "Sizgacha ham ne-ne ohu kol'z bo'lgan.\n" +
                "Bino qo'ymang o'zingizga ey, suluv,\n" +
                "Yalmog'iz ham bir payt sizday qiz bo'lgan"))
        list.add(Poem("Zamonaviy sevgi izhori:","Xayolimdasan uyquga qadar,\n" +
                "Xayolimdasan uyg'onsam sahar.\n" +
                "Hatto tushlarimda ko'raman seni\n" +
                "Shirin tushlarimga qo'shasan zahar!"))
        list.add(
            Poem(
                "Boylik", "Dunyo ekan....\n" +
                        "Pul degan savdo\n" +
                        "Bormi faqat mening boshimda.\n" +
                        "Kimni yemas bul azob,\n" +
                        "Kim ishlamas mening yoshimda...\n" +
                        "\n" +
                        "Boylik ilk bor tushdiyu qalbga,\n" +
                        "Hayotimning bo'ldi mazmuni.\n" +
                        "U tanamga berib shijoat,\n" +
                        "Ko'zlarimdan oldi uyquvni.\n" +
                        "\n" +
                        "Oldi butun fikru hayolim,\n" +
                        "Ketdi dildan orom halovat.\n" +
                        "Boylik butun borimni olib,\n" +
                        "Bitta ko'ylak qoldirdi faqat.\n" +
                        "\n" +
                        "Borliq olam ko'zimga ming ming,\n" +
                        "G'aflatga to'la ko'rindi.\n" +
                        "Gullar menga boyligimning,\n" +
                        "Shodalari bo'lib ko'rindi.\n" +
                        "\n" +
                        "Ayb etmangiz,\n" +
                        "Do'stlar, bu savdo\n" +
                        "Bormi faqat mening boshimda.?\n" +
                        "Kimni etmas bu ko'ngil shaydo,\n" +
                        "Kim xazil qilmas mening yoshimda. ")
        )
    }

    private fun loadFriendship() {
        list = ArrayList()
        list.add(
                Poem(
                    "Do'stimga", "Do’stlar yaxshilarni avaylab asrang.\n" +
                            "Salom degan soz’ni salmog’in oqlang.\n" +
                            "O’lganda yuz soat yig’lab turguncha,\n" +
                            "Uni tirigida bir soat yo’qlang.")
                )
        list.add(Poem("Eslarmikansan","Hayot nogoh sinovga tortsa\n" +
                "Baxli baxtsiz kunlar boshlansa\n" +
                "Dardlaringdan kuzing yoshlansa\n" +
                "Mendek dusting eslarmikansan\n" +
                "Bir kun kelib amaling oshsa\n" +
                "Amalinga xushtor dustlaring ortsa\n" +
                "Ularning davrasi uziga tortsa\n" +
                "Mendek dusting eslarmikansan\n" +
                "Jondan aziz sevganing yonigda bulsa\n" +
                "Shirin farzandlaring jilmayib kulsa\n" +
                "Kuzlaring baxtdan porlab tursa\n" +
                "Mendek dusting eslarmikansan."))
        list.add(Poem("Do'stginam","Yaxshi bo’lib kеtar, yaxshi bo’lib kеtar,\n" +
                "Bu dardsiz so’zlardan yurak to’lib kеtar.\n" +
                "Gar do’stim ekansan, bugundan so’zla,\n" +
                "Yaxshi bo’lguncha to, odam o’lib kеtar.\n" +
                "G’amxo’rlik qilishga o’zga so’z yo’qmi?\n" +
                "So’niq ko’zlarimni ko’rar ko’z yo’qmi?\n" +
                "Siniq toqatimni tiklay olmasdan,\n" +
                "Abgor turganimdan yo ko’ngling to’qmi?\n" +
                "Hol so’rab kеlganda do’stning oldiga,\n" +
                "Do’st dеgan tushadi, do’st ahvoliga.\n" +
                "Bir nafas o’tirgay, birga xo’rsinib,\n" +
                "Qarar ming bir yonib o’chgan holiga.\n" +
                "Hеch qachon so’zlama yolg’on so’zlardan,\n" +
                "Bo’lmagin bеgona, yomon do’stlardan.\n" +
                "Do’st bo’lsang, o’lmasman, umid shamimni,\n" +
                "Ming birinchi bora yoqmay so’nmasman."))
    }

    private fun loadParents() {
        list = ArrayList()
        list.add(Poem("SODDA TILAK","Har kimning ham sochlariga oq tushsin,\n" +
                "Ajin tushsin yuzlariga, dog‘ tushsin.\n" +
                "Har kimning ham quvvat ketib belidan,\n" +
                "Qo‘llariga aso — bir tayoq tushsin.\n" +
                "\n" +
                "Imoni sof yuzga kirib yorug‘ yuz,\n" +
                "To‘ylar ko‘rib yelkasidan tog‘ tushsin.\n" +
                "Va jismiga so‘nggi safar paytida\n" +
                "O‘z bolasin qo‘lidan tuproq tushsin…"))
        list.add(Poem("To'rtlik","Dunyoda eng yakhshi, mehribon ona,\n" +
                "Bu mening onamdir, biling, yoronlar!\n" +
                "Ey, o’g’il-qizlarim, bo’ling parvona\n" +
                "Onamda qolmasin dardu armonlar!"))
        list.add(Poem("SHUHRAT O, ONA…","Ko’z ochib olamga kelgandan beri\n" +
                "Nelarni ko’rmadi bu boshim mening.\n" +
                "Ozmi-ko’p tanidim hayotning sirin,\n" +
                "Qirqqa ham etibdi bu yoshim mening.\n" +
                "\n" +
                "Yildan-yil orttirdim talay do’stu yor,\n" +
                "Hurmatlab dedilar, «do’stim», «o’rtog’im»,\n" +
                "Ulg’aydim: ko’ksimga bosh qo’yib dildor,\n" +
                "U dedi: «Bakhtimsan, suyangan tog’im!..»\n" +
                "\n" +
                "O, ona, hech biri emasdur shirin\n" +
                "«Bolam!» deb bir og’iz aytgan so’zingdan!\n" +
                "O, ona mehringda quyosh yashirin,\n" +
                "Ne ajab gul unsa har bir izingda!"))

    }

    private fun loadCongrats() {
        list = ArrayList()
        list.add(Poem("Har kuningiz o’tsin bayramday har on!","Baxor chechaklari bo’lsin armug’on,\n" +
                        "Siz nurli insonsiz baxtli begumon,\n" +
                        "Bayramla qutlayman bo’ling shodumon,\n" +
                        "Har kuningiz o’tsin bayramday har on!\n" +
                        "Tug’ilgan kuningiz muborak bo’lsin!"))
        list.add(Poem("Sizdek insonlarni ko’proq yaratsin!","Tilaklar ichida izlab topganim,\n" +
                "Alloh nigohini sizga qaratsin.\n" +
                "Yagona o’tinchim Allohdan bu kun,\n" +
                "Sizdek insonlarni ko’proq yaratsin!\n" +
                "Tavallud ayyomingiz qutlug’ bo’lsin!\n" +
                "\n"))
        list.add(Poem("Eng shirin orzularni tilayman sizga","Muborak ayyomning tiniq tongiday,\n" +
                "Musaffo ranglarga to’lganda borliq.\n" +
                "Xushbo’y tabiatning yetti rangiday,\n" +
                "Osmon gardishida qolganda yorliq.\n" +
                "Eng porloq tuyg’ularni tilayman sizga,\n" +
                "Eng shirin orzularni tilayman sizga.\n" +
                "Tug’ilgan kuningiz bo’lsin muborak!"))
        list.add(Poem("8-mart tabrik sher\n","Bahor gullaridan sizga guldasta,\n" +
                "Chin yurak so’zlarim unga payvasta.\n" +
                "Bir asr yashangu yana nim chorak.\n" +
                "8-mart ayyomingiz muborak!"))
        list.add(Poem("Hech qachon do’stlardan ketmasin ishonch","Yangi kun keltirsin baxt, omad, quvonch,\n" +
                "Hech qachon do’stlardan ketmasin ishonch,\n" +
                "Samimiy bo’lsin suhbatda so’zlar,\n" +
                "Yoshlansa quvonchdan yoshlansin ko’zlar.\n" +
                "Yaxshi so’z maqtovlar yangrasin doim,\n" +
                "Omad yaqin do’stingiz bo’lsin, ilohim."))

    }

    private fun loadLonging() {
        list = ArrayList()
        list.add(
            Poem(
                "Sog'inch", "Bir xovlida yashar edik chugʻurlashib,\n" +
                        "Neki boʻlsa, birga baham koʻrar edik.\n" +
                        "Tortishardik gohi tinmay bidirlashib,\n" +
                        "Baxtning daryosida suzib yurar edik.\n" +
                        "Shamoldek tez yillarimdan ogʻrinaman,\n" +
                        "Sogʻinaman, bolaligim, sogʻinaman.")
        )
        list.add(
            Poem(
                "Sog'indim", "Qo'l uzatdim qo'llaring ushlamoqqa\n" +
                        "Hayot yo'llarida birga ketmoqqa.\n" +
                        "Bugun esa mendan ketding uzoqqa\n" +
                        "Sog'inganim bilarmisan endi yor\n" +
                        "Sog'indim deb kelarmisan ey dildor.")
        )
        list.add(Poem("Yana maktub, keraksiz qog'oz...","Yana maktub, keraksiz qog'oz,\n" +
                "Qo'lingizda titrab turibdi.\n" +
                "Yana ko'ngil tentirab bir oz,\n" +
                "Mexringizni izlab yuribdi !"))

    }

    private fun loadLove() {
        list = ArrayList()
        list.add(Poem(
                "Sizni qattiq sevgan yurak sizniki!", "Uyqusiz tunlarim qalbim sizniki,\n" +
                        "Ko’zimning yoshi ko’nglim sizniki,\n" +
                        "Shiring orzularim dardim sizniki,\n" +
                        "Sizni qattiq sevgan yurak sizniki!")
        )
        list.add(Poem("O’lsam ham baribir sevaman seni!","Ko’zlarim ko’r bo’lsa, ko’rmasdim seni,\n" +
                "Yuragim tosh bo’lsa, sevmasdim seni,\n" +
                "Mayli xijron azobi qiynasin meni,\n" +
                "O’lsam ham baribir sevaman seni!"))
        list.add(Poem("Hayotda ma’no yo’q yashashdan sensiz.","Kimgadur visolsan, kimgadur hijron.\n" +
                "Kimgadur orzusan, kimgadur armon.\n" +
                "Kimgadur bir umr, kimgadur bir on.\n" +
                "Kimgadur tomchisan, kimgadur ummon.\n" +
                "Kimgadur achchiqsan, kimgadur shirin.\n" +
                "Hullas «MUHABBATSAN» dunyoda tensiz.\n" +
                "Hayotda ma’no yo’q yashashdan sensiz."))
        list.add(Poem("SMS yozsangu, yozmasa yomon","Birovni sevsangu sevmasa yomon\n" +
                "Kimnidir kutsangu kelmasa yomon.\n" +
                "Hajrida yonsangu bilmasa yomon\n" +
                "SMS yozsangu, yozmasa yomon."))
        list.add(Poem("Men seni sevardim, sevaman hamon","Yo’q seni unitmoq emasdur oson.\n" +
                "Qalbimni o’rtaydi hali ham hijron.\n" +
                "Yurakda bir tuyg’u uradi hamon,\n" +
                "Men seni sevardim, sevaman hamon"))
        list.add(Poem("Sizni o’ylab-o’ylab bugun ham o’tdi","Tun cho’kib borliqni domiga yutdi.\n" +
                "Kimdur meni eslab, kimdur unutdi.\n" +
                "Yuragim eridi shirin tush kabi,\n" +
                "Sizni o’ylab-o’ylab bugun ham o’tdi."))
    }

    private fun loadSaved() {
        list = ArrayList()
        list = MySharedPref.list
    }

    private fun loadNews() {
        list = ArrayList()
        list.add(Poem(
                "Sizni qattiq sevgan yurak sizniki!", "Uyqusiz tunlarim qalbim sizniki,\n" +
                        "Ko’zimning yoshi ko’nglim sizniki,\n" +
                        "Shiring orzularim dardim sizniki,\n" +
                        "Sizni qattiq sevgan yurak sizniki!")
        )
        list.add(Poem("O’lsam ham baribir sevaman seni!","Ko’zlarim ko’r bo’lsa, ko’rmasdim seni,\n" +
                "Yuragim tosh bo’lsa, sevmasdim seni,\n" +
                "Mayli xijron azobi qiynasin meni,\n" +
                "O’lsam ham baribir sevaman seni!"))
        list.add(Poem(
            "Har kuningiz o’tsin bayramday har on!\n","Baxor chechaklari bo’lsin armug’on,\n" +
                    "Siz nurli insonsiz baxtli begumon,\n" +
                    "Bayramla qutlayman bo’ling shodumon,\n" +
                    "Har kuningiz o’tsin bayramday har on!\n" +
                    "Tug’ilgan kuningiz muborak bo’lsin!"))
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment SecondFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            SecondFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}