package com.example.muslim.ui.quran.saved

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.muslim.R
import com.example.muslim.database.bookmark.SavedPage
import com.example.muslim.database.bookmark.mydatabase.QuranDataBase
import com.example.muslim.databinding.FragmentSavedBinding
import com.example.muslim.ui.base.fragment.BaseFragment
import com.example.muslim.ui.quran.saved.adapter.SavedPageFragmentAdapter


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
class SavedPageFragment : BaseFragment<FragmentSavedBinding,SavedViewModel>(),Navigator {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // setLayoutManager for recycler
        viewModel.dataQuran(requireContext())
        viewModel.quranLiveData.observe(this, {
            Log.d("TAG", "onCreate: $it")
            viewDataBinding.RecyclerHome.adapter = SavedPageFragmentAdapter(it)
        })

        }

    override fun getLayoutID(): Int = R.layout.fragment_saved

    override fun makeViewModelProvider(): SavedViewModel = SavedViewModel()

    override fun onResume() { // After a pause OR at startup // refresh your list contents somehow
        super.onResume()
        this.onCreate(null)
    }
}