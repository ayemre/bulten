package com.emre.bulten.ui.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.emre.bulten.R
import com.emre.bulten.remote.models.Match
import kotlinx.android.synthetic.main.item_notices_match.view.*
import kotlinx.coroutines.delay


class MatchListAdapter(private val matchList: List<Match>, private val listener: Listener) :
    RecyclerView.Adapter<MatchListAdapter.ViewHolder>() {

    interface Listener {
        fun onItemClick(match: Match)
        fun changeFavoriteStatus(status: Boolean, position: Int, matchModel: Match)
    }


    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        fun bind(matchModel: Match, position: Int, listener: Listener) {
            itemView.team_home.text = matchModel.homeTeam
            itemView.team_away.text = matchModel.awayTeam
            itemView.match_day.text = matchModel.date
            itemView.match_hour.text = matchModel.time

            if (matchModel.isFavorite) {
                itemView.favorite_button.setImageResource(android.R.drawable.star_on)
            } else {
                itemView.favorite_button.setImageResource(android.R.drawable.star_off)
            }

            itemView.setOnClickListener {
                listener.onItemClick(matchModel)
            }

            itemView.setOnLongClickListener {
                listener.changeFavoriteStatus(false, position, matchModel)
                return@setOnLongClickListener true
            }

            itemView.favorite_button.setOnClickListener {
                matchModel.isFavorite = !matchModel.isFavorite
                listener.changeFavoriteStatus(matchModel.isFavorite, position, matchModel)
            }


        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_notices_match, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(matchList[position], position, listener)
    }

    override fun getItemCount(): Int {
        return matchList.count()
    }

}