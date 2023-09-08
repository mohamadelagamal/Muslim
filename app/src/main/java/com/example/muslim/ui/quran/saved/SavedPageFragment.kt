package com.example.muslim.ui.quran.saved

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.muslim.R
import com.example.muslim.database.bookmark.SavedPage
import com.example.muslim.database.bookmark.mydatabase.QuranDataBase
import com.example.muslim.database.quran.SurahInfoItem
import com.example.muslim.databinding.FragmentSavedBinding
import com.example.muslim.extension.Constant
import com.example.muslim.ui.base.fragment.BaseFragment
import com.example.muslim.ui.quran.reading.ReadingQuranActivity
import com.example.muslim.ui.quran.saved.adapter.SavedPageFragmentAdapter
import com.example.muslim.ui.quran.soraa.adapter.SurahAdapters
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import java.text.FieldPosition


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
class SavedPageFragment : BaseFragment<FragmentSavedBinding,SavedViewModel>(),Navigator {

    var adapter= SavedPageFragmentAdapter(null)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // setLayoutManager for recycler
        viewModel.dataQuran(requireContext())
        viewModel.quranLiveData.observe(this, {
            Log.d("TAG", "onCreate: $it")
            adapter= SavedPageFragmentAdapter(it)
            viewDataBinding.RecyclerHome.adapter = adapter
        })
        adapter.onItemClickInterface=object : SavedPageFragmentAdapter.OnItemClickInterface{
            override fun onItemClick(position: Int) {
                Log.d("position Item Saved",position.toString())
                clickItemBookMark(position)
            }
        }
        adapter.onItemLongClick = object :SavedPageFragmentAdapter.setOnLongClickListener{
            override fun onItemClickLong(pos: Int, item: SavedPage) {
                MaterialAlertDialogBuilder(requireActivity()).setCancelable(true)
                    .setMessage("Delete").setPositiveButton("yes"){dialog,which->
                        deleteItem(pos)
                    }.show()
        }
        }
    }
    private fun deleteItem(position: Int) {
        val savedPageDao = QuranDataBase.getInstance(requireActivity()).savedPageDao()
        savedPageDao.deletePageByPageNumber(position)

    }

    override fun getLayoutID(): Int = R.layout.fragment_saved

    override fun makeViewModelProvider(): SavedViewModel = SavedViewModel()

    override fun onResume() { // After a pause OR at startup // refresh your list contents somehow
        super.onResume()
        this.onCreate(null)
    }

 fun clickItemBookMark(position: Int){
     val intent = Intent (requireActivity(), ReadingQuranActivity::class.java)
        intent.putExtra(Constant.SURA_ID,SurahInfoItem(index = "5000", count = position))
        startActivity(intent)
 }
}