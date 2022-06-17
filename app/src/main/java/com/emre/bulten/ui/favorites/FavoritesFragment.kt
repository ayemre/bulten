package com.emre.bulten.ui.favorites

import android.content.Context
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
import com.emre.bulten.remote.models.Detail
import com.emre.bulten.remote.models.Match
import com.emre.bulten.ui.home.HomeViewModel
import com.emre.bulten.ui.home.MatchListAdapter
import com.emre.bulten.ui.matchdialog.matchDetailDialog
import com.google.gson.Gson

class FavoritesFragment : Fragment(), MatchListAdapter.Listener {

    private var _binding: FragmentHomeBinding? = null

    private val binding get() = _binding!!

    private var homeViewModel: HomeViewModel? = null

    private var matchListAdapter: MatchListAdapter? = null

    var mPrefs: SharedPreferences? = null
    var prefsEditor: SharedPreferences.Editor? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)

        mPrefs = requireActivity().getPreferences(Context.MODE_PRIVATE)
        prefsEditor = mPrefs?.edit()

        val recyclerView: RecyclerView = binding.rvNotices
        val loadingView: FrameLayout = binding.loadingLay
        loadingView.visibility = View.GONE

        matchListAdapter = MatchListAdapter(getFavorites(), this)
        recyclerView.adapter = matchListAdapter
        loadingView.visibility = View.GONE


        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    private fun getFavorites(): ArrayList<Match> {
        val gson = Gson()
        val favoriteList = mPrefs?.getString("favoriteMatches", "")
        val res = gson.fromJson(favoriteList, Array<Match>::class.java) ?: return ArrayList()

        return ArrayList(res.toList())
    }

    override fun onItemClick(match: Match) {
        matchDetailDialog(
            requireContext(),
            match,
            homeViewModel!!,
            viewLifecycleOwner
        ).show()
    }

    override fun changeFavoriteStatus(status: Boolean, position: Int, matchModel: Match) {
        removeFavorite(matchModel, status)
    }

    private fun removeFavorite(match: Match, status: Boolean) {
        val gson = Gson()

        val newFavorites = getFavorites()
        if (status) {
            newFavorites.add(match)
        } else {
            newFavorites.remove(match)
        }

        val json = gson.toJson(newFavorites)

        prefsEditor?.putString("favoriteMatches", json)
        prefsEditor?.commit()

        matchListAdapter = MatchListAdapter(getFavorites(), this)
        binding.rvNotices.adapter = matchListAdapter

    }
}