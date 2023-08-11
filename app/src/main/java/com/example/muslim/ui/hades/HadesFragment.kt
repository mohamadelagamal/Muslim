package com.example.muslim.ui.hades

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.muslim.R
import com.example.muslim.database.HadesData
import com.example.muslim.databinding.FragmentHadesBinding
import com.example.muslim.ui.base.fragment.BaseFragment
import com.example.muslim.ui.hades.adapter.HadesAdapter

class HadesFragment : BaseFragment<FragmentHadesBinding, HadesViewModel>(), Navigator {

    val list = listOf(
        HadesData("Sahih Bukhari", "Sahih al-Bukhari (Arabic: صحيح البخاري, romanized: Ṣaḥīḥ al-Bukhārī)[note 1] is a hadith collection and a book of sunnah compiled by the scholar Muḥammad ibn Ismā‘īl al-Bukhārī (810–870) around 846. Alongside Sahih Muslim, it is one of the most valued books in Sunni Islam after the Quran. Both books are part of the Kutub al-Sittah, the six major Sunni collections of hadith of the Islamic prophet Muhammad. It consists of an estimated 7,563 hadith narrations across its 97 chapters."),
        HadesData("Sahih Muslim", "Sahih Muslim (Arabic: صحيح مسلم, romanized : Ṣaḥīḥ Muslim) [note 1] is a 9th-century hadith collection and a book of sunnah compiled by the Persian scholar Muslim ibn al-Ḥajjāj (815–875). It is one of the most valued books in Sunni Islam after the Quran, alongside Sahih al-Bukhari."),
        HadesData("Sunan Nasai", "Sunan an-Nasa'i is a collection of hadith compiled by Imam Ahmad an-Nasa'i (rahimahullah). His collection is unanimously considered to be one of the six canonical collections of hadith (Kutub as-Sittah) of the Sunnah of the Prophet (ﷺ). It contains roughly 5700 hadith (with repetitions) in 52 books.\n"),
        HadesData("Sunan Abi Dawud", "Sunan Abi Dawud is a collection of hadith compiled by Imam Abu Dawud Sulayman ibn al-Ash’ath as-Sijistani (rahimahullah). It is widely considered to be among the six canonical collections of hadith (Kutub as-Sittah) of the Sunnah of the Prophet (ﷺ). It consists of 5274 ahadith in 43 books1.\n"),
        HadesData("Sunan Tirmidhi", "Jamiat-Tirmidhi is a collection of hadith compiled by Imam AbuIsa Muhammad at-Tirmidhi (rahimahullah). His collection is unanimously considered to be one of the six canonical collections of hadith (Kutub as-Sittah) of the Sunnah of the Prophet (ﷺ). It contains roughly 4400 hadith (with repetitions) in 46 books1.\n" +
                "\n"),
        HadesData("Sunan Ibn Majah", "Sunan Ibn Majah is a collection of hadith compiled by Imam Muhammad bin Yazid Ibn Majah al-Qazvini (rahimahullah). It is widely considered to be the sixth of the six canonical collection of hadith (Kutub as-Sittah) of the Sunnah of the Prophet (ﷺ). It consists of 4341 ahadith in 37 books1.\n" +
                "\n"),
        HadesData("Muwatta Imam Malik", "The Muwatta Imam Malik is a collection of hadith compiled by Imam Malik ibn Anas (rahimahullah). It is one of the earliest collections of hadith and is considered to be one of the most authentic collections of hadith. The Muwatta consists of 1,720 ahadith in 61 books.\n" +
                "\n"),
        HadesData("Sunan Darimi", "Sunan al-Darimi is a collection of hadith compiled by Imam Abdullah ibn Abd ar-Rahman ad-Darimi (d. 255 AH/869 CE - rahimahullah). It is considered to be one of the important collections of reports of the Sunnah of the Prophet Muhammad (ﷺ), typically included in the “Nine Books” of hadith collections. It contains approximately 3,400 hadith1.\n" +
                "\n"),
        HadesData("Musnad Ahmad", "Musnad Ahmad is a collection of hadith compiled by Imam Ahmad ibn Hanbal (d. 241 AH/855 AD - rahimahullah). It is one of the most famous and important collections of reports of the Sunnah of the Prophet Muhammad (ﷺ). It is the largest of the main books of hadith containing approximately 28,199 hadith sectioned based on individual Companions1.\n" +
                "\n"),
    )

    override fun getLayoutID(): Int {
        return R.layout.fragment_hades
    }

    override fun makeViewModelProvider(): HadesViewModel {
        return HadesViewModel()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewDataBinding.vmHades = viewModel
        viewModel.navigator = this
        val adapter = HadesAdapter(list)
        viewDataBinding.rvHades.adapter = adapter

    }
}