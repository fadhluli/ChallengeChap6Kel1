package com.fadtech.challengechap6kel1.ui.ranking

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.fadtech.challengechap6kel1.R
import com.fadtech.challengechap6kel1.data.local.room.UserRoomDatabase
import com.fadtech.challengechap6kel1.data.local.room.datasource.UserDataSource
import com.fadtech.challengechap6kel1.data.model.User
import com.fadtech.challengechap6kel1.databinding.ActivityRankingBinding

class RankingActivity : AppCompatActivity(), RankingContract.View {

    private lateinit var binding: ActivityRankingBinding
    private lateinit var rankingAdapter: RankingAdapter
    private lateinit var presenter: RankingContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRankingBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initView()
        onCloseClick()
        supportActionBar?.hide()
    }

    private fun onCloseClick(){
        binding.ivClose.setOnClickListener {
            finish()
        }
    }

    override fun getData() {
        presenter.getUserRankingList()
    }

    override fun onResume() {
        super.onResume()
        getData()
    }

    override fun onDataSuccess(users: List<User>) {
        users.let {
            rankingAdapter.items = it
        }
    }

    override fun onDataFailed(msg: String?) {
        Toast.makeText(this, msg ?: "Get Data Failed", Toast.LENGTH_SHORT).show()
    }

    override fun onDataEmpty() {
        rankingAdapter.items = mutableListOf()
    }

    override fun setLoadingStatus(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    override fun setEmptyStateVisibility(isDataEmpty: Boolean) {
        binding.tvMessage.text = getString(R.string.text_no_data_message)
        binding.tvMessage.visibility = if (isDataEmpty) View.VISIBLE else View.GONE
    }

    override fun initList() {
        rankingAdapter = RankingAdapter()
        binding.rvRanking.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = rankingAdapter
        }
    }

    override fun initView() {
        val dataSource = UserDataSource(UserRoomDatabase.getInstance(this).userDao())
        presenter = RankingPresenter(dataSource, this)
        initList()
    }

}