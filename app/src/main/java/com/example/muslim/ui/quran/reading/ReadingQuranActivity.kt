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
import com.example.muslim.model.findCurrentPage
import com.example.muslim.model.updateImagesQuran
import com.example.muslim.ui.base.activity.BaseActivity
import com.example.muslim.ui.quran.reading.adapter.ReadingQuranAdapter
import com.example.muslim.ui.quran.reading.details.DetailsActivityReadingQuran
import kotlinx.coroutines.launch


class ReadingQuranActivity : BaseActivity<ActivityReadingQuranBinding, ReadingQuranViewModel>(), Navigator {


    lateinit var suraId: String
    lateinit var imges: List<Int>
    lateinit var adapter: ReadingQuranAdapter
    lateinit var surahInfoItem: SurahInfoItem
    lateinit var savedNumber:String


    @SuppressLint("WrongConstant")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        surahInfoItem = intent.getParcelableExtra(Constant.SURA_ID) !!
        suraId = surahInfoItem.index.toString()
        savedNumber = surahInfoItem.count.toString()
        viewDataBinding.vmQuranReading = viewModel
        viewModel.navigator = this
        // create list of images from assets folder?
        showViewPager()
        showSeekBar()
        clickListenerQuranAdapter()
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
        if (changeBackGround) {            viewDataBinding.savedLottie.setMinAndMaxProgress(minProgress, maxProgress)
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
           // shareLottie.playAnimation()
            shareQuranButton.setOnClickListener {
                lifecycleScope.launch {
                    //shareLottie.playAnimation()
                   try {
                       shareImage()
                   }catch (e:Exception){
                       Log.e("share Image Error",e.message.toString())
                   }

                }
            }
        }
    }
    suspend fun shareImage(){
        // share image form drawable
        val bitmap = BitmapFactory.decodeResource(resources, imges[viewDataBinding.quranPager.currentItem])
        // change bitmap image background to white
        val whiteBitmap = Bitmap.createBitmap(bitmap.width, bitmap.height, bitmap.config)
        val canvas = Canvas(whiteBitmap)
        canvas.drawColor(Color.WHITE)
        canvas.drawBitmap(bitmap, 0f, 0f, null)
        // save image in storage
        // share image
        val path = MediaStore.Images.Media.insertImage(contentResolver,whiteBitmap,"Image",null)
        val uriPage = Uri.parse(path)
        val intent = Intent(Intent.ACTION_SEND)
        intent.type = "image/png"
        intent.putExtra(Intent.EXTRA_STREAM, uriPage)
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
                viewDataBinding.linearLayout.visibility = View.GONE
            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {
            }

            override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                // TODO Auto-generated method stub
                viewDataBinding.quranPager.setCurrentItem(progress, true)
                viewDataBinding.linearLayout.visibility = View.VISIBLE
                viewDataBinding.pageNumber.text = (progress + 1).toString()
            }
        })

    }

    private fun showViewPager() {
        imges = updateImagesQuran()
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
       val resultMapSearch = findCurrentPage(suraId.toInt())
        Log.e("Juzz Reading"," d is ${resultMapSearch.toString()},suraId is: ${suraId.toInt()}")
        if (resultMapSearch==null){
            viewDataBinding.quranPager.setCurrentItem( savedNumber.toInt() , true)
        }else{
            viewDataBinding.quranPager.setCurrentItem( resultMapSearch , true)
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

    companion object{

    }

}
