package com.emre.bulten.ui.matchdialog

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.emre.bulten.R
import com.emre.bulten.remote.models.CurrentMatch
import kotlinx.android.synthetic.main.item_last_match.view.*


class MatchDetailHistoryAdapter(private val historyList: List<CurrentMatch>) :
    RecyclerView.Adapter<MatchDetailHistoryAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        fun bind(item: CurrentMatch, position: Int) {
            itemView.home_team.text = item.homeTeam?.name
            itemView.away_team.text = item.awayTeam?.name
            itemView.full_time.text = item.ftResult
            itemView.half_time.text = item.htResult
            itemView.league.text = item.league

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_last_match, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(historyList[position], position)
    }

    override fun getItemCount(): Int {
        return historyList.count()
    }

}