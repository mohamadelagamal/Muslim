package com.example.muslim.ui.quran.reading.details

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.muslim.R
import com.example.muslim.database.details.Details
import com.example.muslim.database.quran.SurahInfoItem
import com.example.muslim.extension.Constant
import com.example.muslim.ui.quran.reading.ReadingQuranActivity
import com.example.muslim.ui.quran.reading.details.about.AboutFragment
import com.example.muslim.ui.quran.reading.details.parts.JuzzFragment
import com.example.muslim.ui.quran.reading.details.reading.ReadingPageQuarnFragment
import com.example.muslim.ui.quran.reading.details.search.SearchQuranFragment

class DetailsActivityReadingQuran : AppCompatActivity() {

    var readingInformation: Details? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        readingInformation = intent.getParcelableExtra(Constant.DETAILS)!!
        setContentView(R.layout.activity_details_reading_quran)

        when(readingInformation!!.selector){
            1->{
                transactFragment(JuzzFragment())
            }
            2->{
                transactFragment(ReadingPageQuarnFragment())
            }
            3->{
                transactFragment(AboutFragment())
            }
            else->{
                transactFragment(SearchQuranFragment())
            }

        }
    }
    fun transactFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().replace(R.id.detailsFrameContainer, fragment).commit()

    }

}