package com.emre.bulten.ui.matchdialog

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.emre.bulten.R
import com.emre.bulten.remote.models.Match
import com.emre.bulten.ui.home.HomeViewModel
import kotlinx.android.synthetic.main.dialog_match_detail.view.*

fun matchDetailDialog(
    context: Context,
    match: Match,
    viewModel: HomeViewModel,
    viewLifecycleOwner: LifecycleOwner
): AlertDialog {

    viewModel.getMatchDetail(matchCode = match.matchCode)

    val mAlertDialog = AlertDialog.Builder(context).create()
    val mDialogView =
        LayoutInflater.from(context).inflate(R.layout.dialog_match_detail, null, false)
    mAlertDialog.setView(mDialogView)
    mAlertDialog.setCancelable(false)

    viewModel.detailResponse.observe(viewLifecycleOwner) { response ->
        val detail = response?.Detail
        mDialogView.match_name.text =
            String.format("%s - %s", detail?.homeTeam?.name, detail?.awayTeam?.name)
        mDialogView.match_week.text = detail?._id?.currentMatch?.roundDetail
        mDialogView.match_date.text = match.day

        mDialogView.close.setOnClickListener {

            mAlertDialog.dismiss()
        }

        detail?.homeTeam?.lastMatches?.let { teamHistory ->

            mDialogView.rv_results.addItemDecoration(
                DividerItemDecoration(
                    context,
                    LinearLayoutManager.VERTICAL
                )
            )

            val matchDetailHistoryAdapter = MatchDetailHistoryAdapter(teamHistory)
            mDialogView.rv_results.adapter = matchDetailHistoryAdapter
            Handler(Looper.getMainLooper()).postDelayed({
                mDialogView.loading_lay.visibility = View.GONE
            }, 1000)

        }

    }

    return mAlertDialog
}