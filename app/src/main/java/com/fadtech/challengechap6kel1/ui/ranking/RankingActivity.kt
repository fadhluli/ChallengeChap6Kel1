package com.fadtech.challengechap6kel1.ui.ranking

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.fadtech.challengechap6kel1.R
import com.fadtech.challengechap6kel1.base.GenericViewModelFactory
import com.fadtech.challengechap6kel1.base.Resource
import com.fadtech.challengechap6kel1.data.local.room.UserRoomDatabase
import com.fadtech.challengechap6kel1.data.local.room.datasource.UserDataSource
import com.fadtech.challengechap6kel1.data.model.User
import com.fadtech.challengechap6kel1.databinding.ActivityRankingBinding
import com.fadtech.challengechap6kel1.ui.main.MainRepository
import com.fadtech.challengechap6kel1.ui.main.MainViewModel

class RankingActivity : AppCompatActivity(), RankingContract.View {

    private lateinit var binding: ActivityRankingBinding
    private lateinit var rankingAdapter: RankingAdapter
    private lateinit var viewModel: RankingViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRankingBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initView()
        onCloseClick()
        supportActionBar?.hide()
    }

    private fun onCloseClick() {
        binding.ivClose.setOnClickListener {
            finish()
        }
    }

    override fun getData() {
        viewModel.getUserRankingList()
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
        initViewModel()
        initList()
    }

    override fun initViewModel() {
        val dataSource = UserDataSource(UserRoomDatabase.getInstance(this).userDao())
        val repository = RankingRepository(dataSource)
        viewModel =
            GenericViewModelFactory(RankingViewModel(repository)).create(RankingViewModel::class.java)

        viewModel.rankingData.observe(this, {
            when (it) {
                is Resource.Loading -> {
                    setLoadingStatus(true)
                    setEmptyStateVisibility(false)
                }
                is Resource.Success -> {
                    setLoadingStatus(false)
                    it.data?.let { data ->
                        if (data.isNullOrEmpty()) {
                            onDataEmpty()
                            setEmptyStateVisibility(true)
                        } else {
                            onDataSuccess(data)
                        }
                    }
                }
                is Resource.Error -> {
                    setLoadingStatus(false)
                    setEmptyStateVisibility(false)
                    onDataFailed(it.message.orEmpty())
                }
            }
        })
    }

}