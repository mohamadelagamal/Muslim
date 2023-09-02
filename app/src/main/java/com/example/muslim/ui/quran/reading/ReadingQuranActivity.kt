package com.example.muslim.ui.quran.reading

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.ColorFilter
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.SeekBar
import android.widget.SeekBar.OnSeekBarChangeListener
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.viewpager2.widget.ViewPager2
import com.airbnb.lottie.LottieProperty
import com.airbnb.lottie.SimpleColorFilter
import com.airbnb.lottie.model.KeyPath
import com.airbnb.lottie.value.LottieValueCallback
import com.example.muslim.R
import com.example.muslim.database.bookmark.SavedPage
import com.example.muslim.database.bookmark.mydatabase.QuranDataBase
import com.example.muslim.database.details.Details
import com.example.muslim.databinding.ActivityReadingQuranBinding
import com.example.muslim.extension.Constant
import com.example.muslim.database.quran.SurahInfoItem
import com.example.muslim.ui.base.activity.BaseActivity
import com.example.muslim.ui.quran.reading.adapter.ReadingQuranAdapter
import com.example.muslim.ui.quran.reading.details.DetailsActivityReadingQuran
import kotlinx.coroutines.launch


class ReadingQuranActivity : BaseActivity<ActivityReadingQuranBinding, ReadingQuranViewModel>(), Navigator {


    lateinit var suraId: String
    lateinit var imges: List<Int>
    lateinit var adapter: ReadingQuranAdapter
    lateinit var surahInfoItem: SurahInfoItem

    @SuppressLint("WrongConstant")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        surahInfoItem = intent.getParcelableExtra<SurahInfoItem>(Constant.SURA_ID)!!
        suraId = surahInfoItem?.index.toString()
        viewDataBinding.vmQuranReading = viewModel
        viewModel.navigator = this
        // create list of images from assets folder?
        showViewPager()
        clickListenerQuranAdapter()
        showSeekBar()
        // Double Click Listener implemented on the Text View
        // share image
        shareImageButton()
        // make bookMark for last page
        bookMark()

        // check if viewPager is swipe or not
        checkSwipe()
        // check if page is added or not
        checkPageAdded(viewDataBinding.quranPager.currentItem)

    }

    private fun clickListenerQuranAdapter() {
        viewDataBinding.bottomConstraintContainer.visibility = View.GONE
        adapter.onItemClickListener = object : ReadingQuranAdapter.OnItemClickListener {
            override fun onItemClick(position: Int) {
                if (viewDataBinding.bottomConstraintContainer.visibility == View.VISIBLE) {
                    viewDataBinding.linearLayout.visibility = View.GONE
                    viewDataBinding.bottomConstraintContainer.visibility = View.GONE
                } else {
                    viewDataBinding.bottomConstraintContainer.visibility = View.VISIBLE
                    viewDataBinding.linearLayout.visibility = View.VISIBLE

                }
            }

        }
    }

    private fun checkPageAdded( numberPageChecked:Int) {
        val savedPageDao = QuranDataBase.getInstance(this).savedPageDao()
        val savedPage = savedPageDao.getAllQuran()
        savedPage.forEach {
            // add it to list
            if (it.pageNumber == numberPageChecked) {
              //  adapter.changeListBookMark(savedPage) // change color for item
                changeBookMark(0.0f, 0.5f, "delete", true)
            }

        }
        // check if button text is add or delete

    }

    private fun checkSwipe() {
        viewDataBinding.quranPager.registerOnPageChangeCallback(object :
            ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                Log.d("TAG", "onPageSelected: $position")
                // how return savedLottie to default value
                changeBookMark(0.5f, 1.0f, "add")
                checkPageAdded(position)

            }
        })
    }

    private fun bookMark() {
        viewDataBinding.bookMarkButton.setOnClickListener {
            // send current page to adapter
            if (viewDataBinding.bookMarkButton.text == "delete") {
                 deleteItem()
            } else {
                addItem()
            }
        }

    }

    private fun deleteItem() {
        val savedPageDao = QuranDataBase.getInstance(this).savedPageDao()
        val savedPage = savedPageDao.deletePageByPageNumber(viewDataBinding.quranPager.currentItem)
        changeBookMark(0.5f, 1.0f, "add")
        
    }

    private fun addItem() {
        viewModel.addPage(context = this, SavedPage(pageNumber = viewDataBinding.quranPager.currentItem))
        lifecycleScope.launch {
            viewModel.mutableState.collect {
                if (it == Constant.ADDING) {
                    changeBookMark(0.0f, 0.5f, "delete", true)
                }
            }
        }

    }

    private fun changeBookMark(
        minProgress: Float,
        maxProgress: Float,
        word: String,
        changeBackGround: Boolean = false
    ) {
        var yourColor:Int ?= null
        if (changeBackGround) {
            yourColor = ContextCompat.getColor(this, R.color.read)
        } else {
            yourColor = ContextCompat.getColor(this, R.color.white)
        }
        val filter = SimpleColorFilter(yourColor)
        val keyPath = KeyPath("**")
        val callback: LottieValueCallback<ColorFilter> = LottieValueCallback(filter)
        if (changeBackGround) {
            viewDataBinding.savedLottie.setMinAndMaxProgress(minProgress, maxProgress)
            viewDataBinding.savedLottie.playAnimation()
            viewDataBinding.bookMarkButton.text = word
            viewDataBinding.savedLottie.addValueCallback(keyPath, LottieProperty.COLOR_FILTER, callback)

            Log.d("TAG", "changeBookMark: ${viewDataBinding.quranPager.currentItem}")
            //adapter.itemCount = viewDataBinding.quranPager.currentItem
            //adapter.notifyDataSetChanged()

        } else {
            viewDataBinding.bookMarkButton.text = word
            // set default color for lottie
            viewDataBinding.savedLottie.addValueCallback(keyPath, LottieProperty.COLOR_FILTER, callback)
        }

    }

    private fun shareImageButton() {
        viewDataBinding.run {
            shareLottie.playAnimation()
            shareQuranButton.setOnClickListener {
                shareLottie.playAnimation()
                lifecycleScope.launch { shareImage() } }
        }
    }
    suspend fun shareImage(){
        // share image form drawable
        val bitmap = BitmapFactory.decodeResource(
            resources,
            imges[viewDataBinding.quranPager.currentItem]
        )
        // change bitmap image background to white
        val whiteBitmap = Bitmap.createBitmap(bitmap.width, bitmap.height, bitmap.config)
        val canvas = Canvas(whiteBitmap)
        canvas.drawColor(Color.WHITE)
        canvas.drawBitmap(bitmap, 0f, 0f, null)
        // save image in storage
        // share image
        val path = MediaStore.Images.Media.insertImage(contentResolver,whiteBitmap,"",null)
        val uri = Uri.parse(path)
        val intent = Intent(Intent.ACTION_SEND)
        intent.type = "image/png"
        intent.putExtra(Intent.EXTRA_STREAM, uri)
        startActivity(Intent.createChooser(intent, "Share Image"))
        // refresh path
        MediaStore.Images.Media.insertImage(contentResolver,whiteBitmap,"",null)

    }
    private fun showSeekBar() {
        viewDataBinding.seekBar.max = imges.size
        // seekBar start from right to left
        viewDataBinding.seekBar.layoutDirection = View.LAYOUT_DIRECTION_RTL
        // set animation for viewpager2
        // click on seekBar to change page
        viewDataBinding.seekBar.progress = viewDataBinding.quranPager.currentItem

        viewDataBinding.seekBar.setOnSeekBarChangeListener(object : OnSeekBarChangeListener {
            override fun onStopTrackingTouch(seekBar: SeekBar) {
                // TODO Auto-generated method stub
            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {
            }

            override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                // TODO Auto-generated method stub
                viewDataBinding.quranPager.setCurrentItem(progress, true)
            }
        })

    }

    private fun showViewPager() {
        imges = listOf(
            R.drawable.page001,
            R.drawable.page002,
            R.drawable.page003,
            R.drawable.page004,
            R.drawable.page005,
            R.drawable.page006,
            R.drawable.page007,
            R.drawable.page008,
            R.drawable.page009,
            R.drawable.page010,
            R.drawable.page011,
            R.drawable.page012,
            R.drawable.page013,
            R.drawable.page014,
            R.drawable.page015,
            R.drawable.page016,
            R.drawable.page017,
            R.drawable.page018,
            R.drawable.page019,
            R.drawable.page020,
            R.drawable.page021,
            R.drawable.page022,

            R.drawable.page023,
            R.drawable.page024,
            R.drawable.page025,
            R.drawable.page026,

            R.drawable.page027,
            R.drawable.page028,
            R.drawable.page029,
            R.drawable.page030,
            R.drawable.page031,
            R.drawable.page032,
            R.drawable.page033,
            R.drawable.page034,
            R.drawable.page035,
            R.drawable.page036,
            R.drawable.page037,
            R.drawable.page038,
            R.drawable.page039,
            R.drawable.page040,
            R.drawable.page041,
            R.drawable.page042,
            R.drawable.page043,

            R.drawable.page044,
            R.drawable.page045,
            R.drawable.page046,
            R.drawable.page047,
            R.drawable.page048,
            R.drawable.page049,
            R.drawable.page050,
            R.drawable.page051,
            R.drawable.page052,
            R.drawable.page053,
            R.drawable.page054,
            R.drawable.page055,
            R.drawable.page056,
            R.drawable.page057,
            R.drawable.page058,
            R.drawable.page059,
            R.drawable.page060,
            R.drawable.page061,
            R.drawable.page062,
            R.drawable.page063,
            R.drawable.page064,
            R.drawable.page065,
            R.drawable.page066,
            R.drawable.page067,

            R.drawable.page068,
            R.drawable.page069,

            R.drawable.page070,
            R.drawable.page071,
            R.drawable.page072,
            R.drawable.page073,
            R.drawable.page074,
            R.drawable.page075,
            R.drawable.page076,
            R.drawable.page077,
            R.drawable.page078,
            R.drawable.page079,
            R.drawable.page080,
            R.drawable.page081,
            R.drawable.page082,
            R.drawable.page083,
            R.drawable.page084,
            R.drawable.page085,
            R.drawable.page086,

            R.drawable.page087,
            R.drawable.page088,
            R.drawable.page089,
            R.drawable.page090,
            R.drawable.page091,
            R.drawable.page092,
            R.drawable.page093,
            R.drawable.page094,
            R.drawable.page095,
            R.drawable.page096,

            R.drawable.page097,
            R.drawable.page098,
            R.drawable.page099,
            R.drawable.page100,
            R.drawable.page101,
            R.drawable.page102,
            R.drawable.page103,
            R.drawable.page104,
            R.drawable.page105,
            R.drawable.page106,
            R.drawable.page107,
            R.drawable.page108,
            R.drawable.page109,
            R.drawable.page110,
            R.drawable.page111,
            R.drawable.page112,
            R.drawable.page113,
            R.drawable.page114,
            R.drawable.page115,
            R.drawable.page116,
            R.drawable.page117,
            R.drawable.page118,
            R.drawable.page119,
            R.drawable.page120,
            R.drawable.page121,
            R.drawable.page122,
            R.drawable.page123,
            R.drawable.page124,
            R.drawable.page125,
            R.drawable.page126,
            R.drawable.page127,
            R.drawable.page128,
            R.drawable.page129,
            R.drawable.page130,
            R.drawable.page131,
            R.drawable.page132,
            R.drawable.page133,
            R.drawable.page134,
            R.drawable.page135,
            R.drawable.page136,
            R.drawable.page137,
            R.drawable.page138,
            R.drawable.page139,
            R.drawable.page140,
            R.drawable.page141,
            R.drawable.page142,
            R.drawable.page143,
            R.drawable.page144,
            R.drawable.page145,
            R.drawable.page146,
            R.drawable.page147,
            R.drawable.page148,
            R.drawable.page149,
            R.drawable.page150,
            R.drawable.page151,
            R.drawable.page152,
            R.drawable.page153,
            R.drawable.page154,
            R.drawable.page155,
            R.drawable.page156,
            R.drawable.page157,
            R.drawable.page158,
            R.drawable.page159,
            R.drawable.page160,
            R.drawable.page161,
            R.drawable.page162,
            R.drawable.page163,
            R.drawable.page164,
            R.drawable.page165,
            R.drawable.page166,
            R.drawable.page167,
            R.drawable.page168,
            R.drawable.page169,
            R.drawable.page170,
            R.drawable.page171,
            R.drawable.page172,
            R.drawable.page173,
            R.drawable.page174,
            R.drawable.page175,
            R.drawable.page176,
            R.drawable.page177,
            R.drawable.page178,
            R.drawable.page179,
            R.drawable.page180,
            R.drawable.page181,
            R.drawable.page182,
            R.drawable.page183,
            R.drawable.page184,
            R.drawable.page185,
            R.drawable.page186,
            R.drawable.page187,
            R.drawable.page188,
            R.drawable.page189,
            R.drawable.page190,
            R.drawable.page191,
            R.drawable.page192,
            R.drawable.page193,
            R.drawable.page194,
            R.drawable.page195,
            R.drawable.page196,
            R.drawable.page197,
            R.drawable.page198,
            R.drawable.page199,
            R.drawable.page200,
            R.drawable.page201,
            R.drawable.page202,
            R.drawable.page203,
            R.drawable.page204,
            R.drawable.page205,
            R.drawable.page206,
            R.drawable.page207,
            R.drawable.page208,
            R.drawable.page209,
            R.drawable.page210,
            R.drawable.page211,
            R.drawable.page212,
            R.drawable.page213,
            R.drawable.page214,
            R.drawable.page215,
            R.drawable.page216,
            R.drawable.page217,
            R.drawable.page218,
            R.drawable.page219,
            R.drawable.page220,
            R.drawable.page221,
            R.drawable.page222,
            R.drawable.page223,
            R.drawable.page224,
            R.drawable.page225,
            R.drawable.page226,
            R.drawable.page227,
            R.drawable.page228,
            R.drawable.page229,
            R.drawable.page230,
            R.drawable.page231,
            R.drawable.page232,
            R.drawable.page233,
            R.drawable.page234,
            R.drawable.page235,
            R.drawable.page236,
            R.drawable.page237,
            R.drawable.page238,
            R.drawable.page239,
            R.drawable.page240,
            R.drawable.page241,
            R.drawable.page242,
            R.drawable.page243,
            R.drawable.page244,
            R.drawable.page245,
            R.drawable.page246,
            R.drawable.page247,
            R.drawable.page248,
            R.drawable.page249,
            R.drawable.page250,
            R.drawable.page251,
            R.drawable.page252,
            R.drawable.page253,
            R.drawable.page254,
            R.drawable.page255,
            R.drawable.page256,
            R.drawable.page257,
            R.drawable.page258,
            R.drawable.page259,
            R.drawable.page260,
            R.drawable.page261,
            R.drawable.page262,
            R.drawable.page263,
            R.drawable.page264,
            R.drawable.page265,
            R.drawable.page266,
            R.drawable.page267,
            R.drawable.page268,
            R.drawable.page269,
            R.drawable.page270,
            R.drawable.page271,
            R.drawable.page272,
            R.drawable.page273,
            R.drawable.page274,
            R.drawable.page275,
            R.drawable.page276,
            R.drawable.page277,
            R.drawable.page278,
            R.drawable.page279,
            R.drawable.page280,
            R.drawable.page281,
            R.drawable.page282,
            R.drawable.page283,
            R.drawable.page284,
            R.drawable.page285,
            R.drawable.page286,
            R.drawable.page287,
            R.drawable.page288,
            R.drawable.page289,
            R.drawable.page290,
            R.drawable.page291,
            R.drawable.page292,
            R.drawable.page293,
            R.drawable.page294,
            R.drawable.page295,
            R.drawable.page296,
            R.drawable.page297,
            R.drawable.page298,
            R.drawable.page299,
            R.drawable.page300,
            R.drawable.page301,
            R.drawable.page302,
            R.drawable.page303,
            R.drawable.page304,
            R.drawable.page305,
            R.drawable.page306,
            R.drawable.page307,
            R.drawable.page308,
            R.drawable.page309,
            R.drawable.page310,
            R.drawable.page311,
            R.drawable.page312,
            R.drawable.page313,
            R.drawable.page314,
            R.drawable.page315,
            R.drawable.page316,
            R.drawable.page317,
            R.drawable.page318,
            R.drawable.page319,
            R.drawable.page320,
            R.drawable.page321,
            R.drawable.page322,
            R.drawable.page323,
            R.drawable.page324,
            R.drawable.page325,
            R.drawable.page326,
            R.drawable.page327,
            R.drawable.page328,
            R.drawable.page329,
            R.drawable.page330,
            R.drawable.page331,
            R.drawable.page332,
            R.drawable.page333,
            R.drawable.page334,
            R.drawable.page335,
            R.drawable.page336,
            R.drawable.page337,
            R.drawable.page338,
            R.drawable.page339,
            R.drawable.page340,
            R.drawable.page341,
            R.drawable.page342,
            R.drawable.page343,
            R.drawable.page344,
            R.drawable.page345,
            R.drawable.page346,
            R.drawable.page347,
            R.drawable.page348,
            R.drawable.page349,
            R.drawable.page350,
            R.drawable.page351,
            R.drawable.page352,
            R.drawable.page353,
            R.drawable.page354,
            R.drawable.page355,
            R.drawable.page356,
            R.drawable.page357,
            R.drawable.page358,
            R.drawable.page359,
            R.drawable.page360,
            R.drawable.page361,
            R.drawable.page362,
            R.drawable.page363,
            R.drawable.page364,
            R.drawable.page365,
            R.drawable.page366,
            R.drawable.page367,
            R.drawable.page368,
            R.drawable.page369,
            R.drawable.page370,
            R.drawable.page371,
            R.drawable.page372,
            R.drawable.page373,
            R.drawable.page374,
            R.drawable.page375,
            R.drawable.page376,
            R.drawable.page377,
            R.drawable.page378,
            R.drawable.page379,
            R.drawable.page380,
            R.drawable.page381,
            R.drawable.page382,
            R.drawable.page383,
            R.drawable.page384,
            R.drawable.page385,
            R.drawable.page386,
            R.drawable.page387,
            R.drawable.page388,
            R.drawable.page389,
            R.drawable.page390,
            R.drawable.page391,
            R.drawable.page392,
            R.drawable.page393,
            R.drawable.page394,
            R.drawable.page395,
            R.drawable.page396,
            R.drawable.page397,
            R.drawable.page398,
            R.drawable.page399,
            R.drawable.page400,
            R.drawable.page401,
            R.drawable.page402,
            R.drawable.page403,
            R.drawable.page404,
            R.drawable.page405,
            R.drawable.page406,
            R.drawable.page407,
            R.drawable.page408,
            R.drawable.page409,
            R.drawable.page410,
            R.drawable.page411,
            R.drawable.page412,
            R.drawable.page413,
            R.drawable.page414,
            R.drawable.page415,
            R.drawable.page416,
            R.drawable.page417,
            R.drawable.page418,
            R.drawable.page419,
            R.drawable.page420,
            R.drawable.page421,
            R.drawable.page422,
            R.drawable.page423,
            R.drawable.page424,
            R.drawable.page425,
            R.drawable.page426,
            R.drawable.page427,
            R.drawable.page428,
            R.drawable.page429,
            R.drawable.page430,
            R.drawable.page431,
            R.drawable.page432,
            R.drawable.page433,
            R.drawable.page434,
            R.drawable.page435,
            R.drawable.page436,
            R.drawable.page437,
            R.drawable.page438,
            R.drawable.page439,
            R.drawable.page440,
            R.drawable.page441,
            R.drawable.page442,
            R.drawable.page443,
            R.drawable.page444,
            R.drawable.page445,
            R.drawable.page446,
            R.drawable.page447,
            R.drawable.page448,
            R.drawable.page449,
            R.drawable.page450,
            R.drawable.page451,
            R.drawable.page452,
            R.drawable.page453,
            R.drawable.page454,
            R.drawable.page455,
            R.drawable.page456,
            R.drawable.page457,
            R.drawable.page458,
            R.drawable.page459,
            R.drawable.page460,
            R.drawable.page461,
            R.drawable.page462,
            R.drawable.page463,
            R.drawable.page464,
            R.drawable.page465,
            R.drawable.page466,
            R.drawable.page467,
            R.drawable.page468,
            R.drawable.page469,
            R.drawable.page470,
            R.drawable.page471,
            R.drawable.page472,
            R.drawable.page473,
            R.drawable.page474,
            R.drawable.page475,
            R.drawable.page476,
            R.drawable.page477,
            R.drawable.page478,
            R.drawable.page479,
            R.drawable.page480,
            R.drawable.page481,
            R.drawable.page482,
            R.drawable.page483,
            R.drawable.page484,
            R.drawable.page485,
            R.drawable.page486,
            R.drawable.page487,
            R.drawable.page488,
            R.drawable.page489,
            R.drawable.page490,
            R.drawable.page491,
            R.drawable.page492,
            R.drawable.page493,
            R.drawable.page494,
            R.drawable.page495,
            R.drawable.page496,
            R.drawable.page497,
            R.drawable.page498,
            R.drawable.page499,
            R.drawable.page500,
            R.drawable.page501,
            R.drawable.page502,
            R.drawable.page503,
            R.drawable.page504,
            R.drawable.page505,
            R.drawable.page506,
            R.drawable.page507,
            R.drawable.page508,
            R.drawable.page509,
            R.drawable.page510,
            R.drawable.page511,
            R.drawable.page512,
            R.drawable.page513,
            R.drawable.page514,
            R.drawable.page515,
            R.drawable.page516,
            R.drawable.page517,
            R.drawable.page518,
            R.drawable.page519,
            R.drawable.page520,
            R.drawable.page521,
            R.drawable.page522,
            R.drawable.page523,
            R.drawable.page524,
            R.drawable.page525,
            R.drawable.page526,
            R.drawable.page527,
            R.drawable.page528,
            R.drawable.page529,
            R.drawable.page530,
            R.drawable.page531,
            R.drawable.page532,
            R.drawable.page533,
            R.drawable.page534,
            R.drawable.page535,
            R.drawable.page536,
            R.drawable.page537,
            R.drawable.page538,
            R.drawable.page539,
            R.drawable.page540,
            R.drawable.page541,
            R.drawable.page542,
            R.drawable.page543,
            R.drawable.page544,
            R.drawable.page545,
            R.drawable.page546,
            R.drawable.page547,
            R.drawable.page548,
            R.drawable.page549,
            R.drawable.page550,
            R.drawable.page551,
            R.drawable.page552,
            R.drawable.page553,
            R.drawable.page554,
            R.drawable.page555,
            R.drawable.page556,
            R.drawable.page557,
            R.drawable.page558,
            R.drawable.page559,
            R.drawable.page560,
            R.drawable.page561,
            R.drawable.page562,
            R.drawable.page563,
            R.drawable.page564,
            R.drawable.page565,
            R.drawable.page566,
            R.drawable.page567,
            R.drawable.page568,
            R.drawable.page569,
            R.drawable.page570,
            R.drawable.page571,
            R.drawable.page572,
            R.drawable.page573,
            R.drawable.page574,
            R.drawable.page575,
            R.drawable.page576,
            R.drawable.page577,
            R.drawable.page578,
            R.drawable.page579,
            R.drawable.page580,
            R.drawable.page581,
            R.drawable.page582,
            R.drawable.page583,
            R.drawable.page584,
            R.drawable.page585,
            R.drawable.page586,
            R.drawable.page587,
            R.drawable.page588,
            R.drawable.page589,
            R.drawable.page590,
            R.drawable.page591,
            R.drawable.page592,
            R.drawable.page593,
            R.drawable.page594,
            R.drawable.page595,
            R.drawable.page596,
            R.drawable.page597,
            R.drawable.page598,
            R.drawable.page599,
            R.drawable.page600,
            R.drawable.page601,
            R.drawable.page602,
            R.drawable.page603,
            R.drawable.page604,

            )
        // set adapter for viewpager2
        adapter = ReadingQuranAdapter(imges)
        viewDataBinding.quranPager.adapter = adapter
        // how to get current page in viewpager2
        viewDataBinding.quranPager.registerOnPageChangeCallback(object :
            ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                viewDataBinding.pageNumber.text = (position + 1).toString()
            }
        })
        viewDataBinding.quranPager.orientation = ViewPager2.ORIENTATION_HORIZONTAL
        viewDataBinding.quranPager.layoutDirection = View.LAYOUT_DIRECTION_RTL
        when (suraId) {
            "1" -> {
                viewDataBinding.quranPager.setCurrentItem(0, true)
                viewDataBinding.seekBar.progress = 0
            }

            "2" -> {
                viewDataBinding.quranPager.setCurrentItem(1, true)
                viewDataBinding.seekBar.progress = 1
            }

            "3" -> {
                viewDataBinding.quranPager.setCurrentItem(49, true)
                viewDataBinding.seekBar.progress = 49
            }

            "4" -> {
                viewDataBinding.quranPager.setCurrentItem(76, true)
                viewDataBinding.seekBar.progress = 76
            }

            "5" -> {
                viewDataBinding.quranPager.setCurrentItem(105, true)
                viewDataBinding.seekBar.progress = 105
            }

            "6" -> {
                viewDataBinding.quranPager.setCurrentItem(127, true)
                viewDataBinding.seekBar.progress = 127
            }

            "7" -> {
                viewDataBinding.quranPager.setCurrentItem(150, true)
                viewDataBinding.seekBar.progress = 150
            }

            "8" -> {
                viewDataBinding.quranPager.setCurrentItem(176, true)
                viewDataBinding.seekBar.progress = 176
            }

            "9" -> {
                viewDataBinding.quranPager.setCurrentItem(186, true)
                viewDataBinding.seekBar.progress = 186
            }

            "10" -> {
                viewDataBinding.quranPager.setCurrentItem(207, true)
                viewDataBinding.seekBar.progress = 207
            }

            "11" -> {
                viewDataBinding.quranPager.setCurrentItem(220, true)
                viewDataBinding.seekBar.progress = 220
            }

            "12" -> {
                viewDataBinding.quranPager.setCurrentItem(234, true)
                viewDataBinding.seekBar.progress = 234
            }

            "13" -> {
                viewDataBinding.quranPager.setCurrentItem(248, true)
                viewDataBinding.seekBar.progress = 248
            }

            "14" -> {
                viewDataBinding.quranPager.setCurrentItem(254, true)
                viewDataBinding.seekBar.progress = 254
            }

            "15" -> {
                viewDataBinding.quranPager.setCurrentItem(261, true)
                viewDataBinding.seekBar.progress = 261
            }

            "16" -> {
                viewDataBinding.quranPager.setCurrentItem(266, true)
                viewDataBinding.seekBar.progress = 266
            }

            "17" -> {
                viewDataBinding.quranPager.setCurrentItem(281, true)
                viewDataBinding.seekBar.progress = 281
            }

            "18" -> {
                viewDataBinding.quranPager.setCurrentItem(292, true)
                viewDataBinding.seekBar.progress = 292
            }

            "19" -> {
                viewDataBinding.quranPager.setCurrentItem(304, true)
                viewDataBinding.seekBar.progress = 304
            }

            "20" -> {
                viewDataBinding.quranPager.setCurrentItem(311, true)
                viewDataBinding.seekBar.progress = 311
            }

            "21" -> {
                viewDataBinding.quranPager.setCurrentItem(321, true)
                viewDataBinding.seekBar.progress = 321
            }

            "22" -> {
                viewDataBinding.quranPager.setCurrentItem(331, true)
                viewDataBinding.seekBar.progress = 331
            }

            "23" -> {
                viewDataBinding.quranPager.setCurrentItem(341, true)
                viewDataBinding.seekBar.progress = 341
            }

            "24" -> {
                viewDataBinding.quranPager.setCurrentItem(349, true)
                viewDataBinding.seekBar.progress = 349
            }

            "25" -> {
                viewDataBinding.quranPager.setCurrentItem(358, true)
                viewDataBinding.seekBar.progress = 358
            }

            "26" -> {
                viewDataBinding.quranPager.setCurrentItem(366, true)
                viewDataBinding.seekBar.progress = 366
            }

            "27" -> {
                viewDataBinding.quranPager.setCurrentItem(376, true)
                viewDataBinding.seekBar.progress = 376
            }

            "28" -> {
                viewDataBinding.quranPager.setCurrentItem(384, true)
                viewDataBinding.seekBar.progress = 384
            }

            "29" -> {
                viewDataBinding.quranPager.setCurrentItem(395, true)
                viewDataBinding.seekBar.progress = 395
            }

            "30" -> {
                viewDataBinding.quranPager.setCurrentItem(403, true)
                viewDataBinding.seekBar.progress = 403
            }

            "31" -> {
                viewDataBinding.quranPager.setCurrentItem(410, true)
                viewDataBinding.seekBar.progress = 410
            }

            "32" -> {
                viewDataBinding.quranPager.setCurrentItem(414, true)
                viewDataBinding.seekBar.progress = 414
            }

            "33" -> {
                viewDataBinding.quranPager.setCurrentItem(417, true)
                viewDataBinding.seekBar.progress = 417
            }

            "34" -> {
                viewDataBinding.quranPager.setCurrentItem(427, true)
                viewDataBinding.seekBar.progress = 427
            }

            "35" -> {
                viewDataBinding.quranPager.setCurrentItem(433, true)
                viewDataBinding.seekBar.progress = 433
            }

            "36" -> {
                viewDataBinding.quranPager.setCurrentItem(439, true)
                viewDataBinding.seekBar.progress = 439
            }

            "37" -> {
                viewDataBinding.quranPager.setCurrentItem(445, true)
                viewDataBinding.seekBar.progress = 445
            }

            "38" -> {
                viewDataBinding.quranPager.setCurrentItem(452, true)
                viewDataBinding.seekBar.progress = 452
            }

            "39" -> {
                viewDataBinding.quranPager.setCurrentItem(457, true)
                viewDataBinding.seekBar.progress = 457
            }

            "40" -> {
                viewDataBinding.quranPager.setCurrentItem(466, true)
                viewDataBinding.seekBar.progress = 466
            }

            "41" -> {
                viewDataBinding.quranPager.setCurrentItem(476, true)
                viewDataBinding.seekBar.progress = 476
            }

            "42" -> {
                viewDataBinding.quranPager.setCurrentItem(482, true)
                viewDataBinding.seekBar.progress = 482
            }

            "43" -> {
                viewDataBinding.quranPager.setCurrentItem(489, true)
                viewDataBinding.seekBar.progress = 489
            }

            "44" -> {
                viewDataBinding.quranPager.setCurrentItem(495, true)
                viewDataBinding.seekBar.progress = 495
            }

            "45" -> {
                viewDataBinding.quranPager.setCurrentItem(498, true)
                viewDataBinding.seekBar.progress = 498
            }

            "46" -> {
                viewDataBinding.quranPager.setCurrentItem(501, true)
                viewDataBinding.seekBar.progress = 501
            }

            "47" -> {
                viewDataBinding.quranPager.setCurrentItem(506, true)
                viewDataBinding.seekBar.progress = 506
            }

            "48" -> {
                viewDataBinding.quranPager.setCurrentItem(510, true)
                viewDataBinding.seekBar.progress = 510
            }

            "49" -> {
                viewDataBinding.quranPager.setCurrentItem(514, true)
                viewDataBinding.seekBar.progress = 514
            }

            "50" -> {
                viewDataBinding.quranPager.setCurrentItem(517, true)
                viewDataBinding.seekBar.progress = 517
            }

            "51" -> {
                viewDataBinding.quranPager.setCurrentItem(519, true)
                viewDataBinding.seekBar.progress = 519
            }

            "52" -> {
                viewDataBinding.quranPager.setCurrentItem(522, true)
                viewDataBinding.seekBar.progress = 522
            }

            "53" -> {
                viewDataBinding.quranPager.setCurrentItem(525, true)
                viewDataBinding.seekBar.progress = 525
            }

            "54" -> {
                viewDataBinding.quranPager.setCurrentItem(527, true)
                viewDataBinding.seekBar.progress = 527
            }

            "55" -> {
                viewDataBinding.quranPager.setCurrentItem(530, true)
                viewDataBinding.seekBar.progress = 530
            }

            "56" -> {
                viewDataBinding.quranPager.setCurrentItem(533, true)
                viewDataBinding.seekBar.progress = 533
            }

            "57" -> {
                viewDataBinding.quranPager.setCurrentItem(536, true)
                viewDataBinding.seekBar.progress = 536
            }

            "58" -> {
                viewDataBinding.quranPager.setCurrentItem(541, true)
                viewDataBinding.seekBar.progress = 541
            }

            "59" -> {
                viewDataBinding.quranPager.setCurrentItem(544, true)
                viewDataBinding.seekBar.progress = 544
            }

            "60" -> {
                viewDataBinding.quranPager.setCurrentItem(548, true)
                viewDataBinding.seekBar.progress = 548
            }

            "61" -> {
                viewDataBinding.quranPager.setCurrentItem(550, true)
                viewDataBinding.seekBar.progress = 550
            }

            "62" -> {
                viewDataBinding.quranPager.setCurrentItem(552, true)
                viewDataBinding.seekBar.progress = 552
            }

            "63" -> {
                viewDataBinding.quranPager.setCurrentItem(553, true)
                viewDataBinding.seekBar.progress = 553
            }

            "64" -> {
                viewDataBinding.quranPager.setCurrentItem(555, true)
                viewDataBinding.seekBar.progress = 555
            }

            "65" -> {
                viewDataBinding.quranPager.setCurrentItem(557, true)
                viewDataBinding.seekBar.progress = 557
            }

            "66" -> {
                viewDataBinding.quranPager.setCurrentItem(559, true)
                viewDataBinding.seekBar.progress = 559
            }

            "67" -> {
                viewDataBinding.quranPager.setCurrentItem(561, true)
                viewDataBinding.seekBar.progress = 561
            }

            "68" -> {
                viewDataBinding.quranPager.setCurrentItem(563, true)
                viewDataBinding.seekBar.progress = 563
            }

            "69" -> {
                viewDataBinding.quranPager.setCurrentItem(565, true)
                viewDataBinding.seekBar.progress = 565
            }

            "70" -> {
                viewDataBinding.quranPager.setCurrentItem(567, true)
                viewDataBinding.seekBar.progress = 567
            }

            "71" -> {
                viewDataBinding.quranPager.setCurrentItem(569, true)
                viewDataBinding.seekBar.progress = 569
            }

            "72" -> {
                viewDataBinding.quranPager.setCurrentItem(571, true)
                viewDataBinding.seekBar.progress = 571
            }

            "73" -> {
                viewDataBinding.quranPager.setCurrentItem(573, true)
                viewDataBinding.seekBar.progress = 573
            }

            "74" -> {
                viewDataBinding.quranPager.setCurrentItem(574, true)
                viewDataBinding.seekBar.progress = 574
            }

            "75" -> {
                viewDataBinding.quranPager.setCurrentItem(576, true)
                viewDataBinding.seekBar.progress = 576
            }

            "76" -> {
                viewDataBinding.quranPager.setCurrentItem(577, true)
                viewDataBinding.seekBar.progress = 577
            }

            "77" -> {
                viewDataBinding.quranPager.setCurrentItem(579, true)
                viewDataBinding.seekBar.progress = 579
            }

            "78" -> {
                viewDataBinding.quranPager.setCurrentItem(581, true)
                viewDataBinding.seekBar.progress = 581
            }

            "79" -> {
                viewDataBinding.quranPager.setCurrentItem(582, true)
                viewDataBinding.seekBar.progress = 582
            }

            "80" -> {
                viewDataBinding.quranPager.setCurrentItem(584, true)
                viewDataBinding.seekBar.progress = 584
            }

            "81" -> {
                viewDataBinding.quranPager.setCurrentItem(585, true)
                viewDataBinding.seekBar.progress = 585
            }

            "82" -> {
                viewDataBinding.quranPager.setCurrentItem(586, true)
                viewDataBinding.seekBar.progress = 586
            }

            "83" -> {
                viewDataBinding.quranPager.setCurrentItem(586, true)
                viewDataBinding.seekBar.progress = 586
            }

            "84" -> {
                viewDataBinding.quranPager.setCurrentItem(588, true)
                viewDataBinding.seekBar.progress = 588
            }

            "85" -> {
                viewDataBinding.quranPager.setCurrentItem(589, true)
                viewDataBinding.seekBar.progress = 589
            }

            "86" -> {
                viewDataBinding.quranPager.setCurrentItem(590, true)
                viewDataBinding.seekBar.progress = 590
            }

            "87" -> {
                viewDataBinding.quranPager.setCurrentItem(590, true)
                viewDataBinding.seekBar.progress = 590
            }

            "88" -> {
                viewDataBinding.quranPager.setCurrentItem(591, true)
                viewDataBinding.seekBar.progress = 591
            }

            "89" -> {
                viewDataBinding.quranPager.setCurrentItem(592, true)
                viewDataBinding.seekBar.progress = 592
            }

            "90" -> {
                viewDataBinding.quranPager.setCurrentItem(593, true)
                viewDataBinding.seekBar.progress = 593
            }

            "91" -> {
                viewDataBinding.quranPager.setCurrentItem(594, true)
                viewDataBinding.seekBar.progress = 594
            }

            "92" -> {
                viewDataBinding.quranPager.setCurrentItem(594, true)
                viewDataBinding.seekBar.progress = 594
            }

            "93" -> {
                viewDataBinding.quranPager.setCurrentItem(595, true)
                viewDataBinding.seekBar.progress = 595
            }

            "94" -> {
                viewDataBinding.quranPager.setCurrentItem(595, true)
                viewDataBinding.seekBar.progress = 595
            }

            "95" -> {
                viewDataBinding.quranPager.setCurrentItem(596, true)
                viewDataBinding.seekBar.progress = 596
            }

            "96" -> {
                viewDataBinding.quranPager.setCurrentItem(596, true)
                viewDataBinding.seekBar.progress = 596
            }

            "97" -> {
                viewDataBinding.quranPager.setCurrentItem(597, true)
                viewDataBinding.seekBar.progress = 597
            }

            "98" -> {
                viewDataBinding.quranPager.setCurrentItem(597, true)
                viewDataBinding.seekBar.progress = 597
            }

            "99" -> {
                viewDataBinding.quranPager.setCurrentItem(598, true)
                viewDataBinding.seekBar.progress = 598
            }

            "100" -> {
                viewDataBinding.quranPager.setCurrentItem(598, true)
                viewDataBinding.seekBar.progress = 598
            }

            "101" -> {
                viewDataBinding.quranPager.setCurrentItem(599, true)
                viewDataBinding.seekBar.progress = 599
            }

            "102" -> {
                viewDataBinding.quranPager.setCurrentItem(599, true)
                viewDataBinding.seekBar.progress = 599
            }

            "103" -> {
                viewDataBinding.quranPager.setCurrentItem(600, true)
                viewDataBinding.seekBar.progress = 600
            }

            "104" -> {
                viewDataBinding.quranPager.setCurrentItem(600, true)
                viewDataBinding.seekBar.progress = 600
            }

            "105" -> {
                viewDataBinding.quranPager.setCurrentItem(600, true)
                viewDataBinding.seekBar.progress = 600
            }

            "106" -> {
                viewDataBinding.quranPager.setCurrentItem(601, true)
                viewDataBinding.seekBar.progress = 601
            }

            "107" -> {
                viewDataBinding.quranPager.setCurrentItem(601, true)
                viewDataBinding.seekBar.progress = 601
            }

            "108" -> {
                viewDataBinding.quranPager.setCurrentItem(601, true)
                viewDataBinding.seekBar.progress = 601
            }

            "109" -> {
                viewDataBinding.quranPager.setCurrentItem(602, true)
                viewDataBinding.seekBar.progress = 602
            }

            "110" -> {
                viewDataBinding.quranPager.setCurrentItem(602, true)
                viewDataBinding.seekBar.progress = 602
            }

            "111" -> {
                viewDataBinding.quranPager.setCurrentItem(602, true)
                viewDataBinding.seekBar.progress = 602
            }

            "112" -> {
                viewDataBinding.quranPager.setCurrentItem(603, true)
            }

            "113" -> {
                viewDataBinding.quranPager.setCurrentItem(603, true)
                viewDataBinding.seekBar.progress = 603
            }

            "114" -> {
                viewDataBinding.seekBar.progress = 603
                viewDataBinding.quranPager.setCurrentItem(603, true)
            }
        }

    }

    override fun getLayoutID(): Int = R.layout.activity_reading_quran

    override fun makeViewModelProvider(): ReadingQuranViewModel =
        ViewModelProvider(this).get(ReadingQuranViewModel::class.java)

    override fun openParts() {
      movingDetailsActivity(Constant.SELECTOR_PARTS)
    }

    override fun openReadingQuran() {
        movingDetailsActivity(Constant.SELECTOR_READING)
    }

    override fun openAbout() {
        movingDetailsActivity(Constant.SELECTOR_ABOUT)
    }

    override fun openSearch() {
        movingDetailsActivity(Constant.SELECTOR_SEARCH)
    }

    fun movingDetailsActivity(value: Int){
        val intent = Intent(this,DetailsActivityReadingQuran::class.java)
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
        intent.putExtra(Constant.DETAILS,Details(selector = value))
        startActivity(intent)
    }


}
