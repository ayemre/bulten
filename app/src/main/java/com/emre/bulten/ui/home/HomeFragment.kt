package com.emre.bulten.ui.home

import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.emre.bulten.databinding.FragmentHomeBinding
import com.emre.bulten.remote.models.Match
import com.emre.bulten.ui.SharedViewModel
import com.emre.bulten.ui.matchdialog.matchDetailDialog
import com.google.gson.Gson
import kotlinx.android.synthetic.main.dialog_match_detail.view.*


class HomeFragment : Fragment(), MatchListAdapter.Listener {

    private var sharedViewModel: SharedViewModel? = null

    private var _binding: FragmentHomeBinding? = null

    private val binding get() = _binding!!

    private var matchListAdapter: MatchListAdapter? = null

    private var mPrefs: SharedPreferences? = null
    private var prefsEditor: SharedPreferences.Editor? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        sharedViewModel =
            ViewModelProvider(this).get(SharedViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val recyclerView: RecyclerView = binding.rvNotices
        val loadingView: FrameLayout = binding.loadingLay

        mPrefs = requireActivity().getPreferences(MODE_PRIVATE)
        prefsEditor = mPrefs?.edit()

        sharedViewModel?.noticesResponse?.observe(viewLifecycleOwner) { response ->

            response.notice?.matchList?.let { list ->

                val favorites = getFavorites()
                list.filter { it.type == 1 }.forEach { match ->
                    if (favorites.contains(match)) {
                        match.isFavorite = true
                    }
                }
                matchListAdapter = MatchListAdapter(list.filter { it.type == 1 }, this)
                recyclerView.adapter = matchListAdapter
                loadingView.visibility = View.GONE
            }

        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onItemClick(match: Match) {
        matchDetailDialog(
            requireContext(),
            match,
            sharedViewModel!!,
            viewLifecycleOwner
        ).show()

    }

    override fun changeFavoriteStatus(status: Boolean, position: Int, matchModel: Match) {
        matchListAdapter?.notifyItemChanged(position)
        changeFavorite(matchModel, status)
    }


    private fun getFavorites(): ArrayList<Match> {
        val gson = Gson()
        val favoriteList = mPrefs?.getString("favoriteMatches", "")
        val res = gson.fromJson(favoriteList, Array<Match>::class.java) ?: return ArrayList()

        return ArrayList(res.toList())
    }

    private fun changeFavorite(match: Match, status: Boolean) {
        val gson = Gson()

        val newFavorites = getFavorites()
        if (status) {
            newFavorites.add(match)
        } else {
            newFavorites.remove(match)
        }

        val stringFavoriteList = gson.toJson(newFavorites)

        prefsEditor?.putString("favoriteMatches", stringFavoriteList)
        prefsEditor?.commit()

    }

}