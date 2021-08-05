package com.fadtech.challengechap6kel1.ui.history

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.fadtech.challengechap6kel1.R
import com.fadtech.challengechap6kel1.base.GenericViewModelFactory
import com.fadtech.challengechap6kel1.base.Resource
import com.fadtech.challengechap6kel1.data.network.datasource.HistoryDataSource
import com.fadtech.challengechap6kel1.data.network.entity.responses.History.GetHistoryData
import com.fadtech.challengechap6kel1.data.network.service.HistoryApiServices
import com.fadtech.challengechap6kel1.databinding.ActivityHistoryBinding
import com.fadtech.challengechap6kel1.preference.SessionPreference

class HistoryActivity : AppCompatActivity(), HistoryContract.View {

    private lateinit var binding: ActivityHistoryBinding
    private lateinit var viewModel: HistoryViewModel
    private lateinit var historyAdapter: HistoryAdapter
    private lateinit var sessionPreference: SessionPreference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHistoryBinding.inflate(layoutInflater)
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
        viewModel.getHistory()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }

    override fun onResume() {
        super.onResume()
        getData()
    }

    override fun onDataSuccess(data: List<GetHistoryData>) {
        data.let {
            historyAdapter.items = it
        }
    }

    override fun onDataFailed(msg: String?) {
        Toast.makeText(this, msg ?: "Get Data Failed", Toast.LENGTH_SHORT).show()
    }

    override fun onDataEmpty() {
        historyAdapter.items = mutableListOf()
    }

    override fun setLoadingStatus(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    override fun setEmptyStateVisibility(isDataEmpty: Boolean) {
        binding.tvMessage.text = getString(R.string.text_no_data_message)
        binding.tvMessage.visibility = if (isDataEmpty) View.VISIBLE else View.GONE
    }

    override fun initList() {
        historyAdapter = HistoryAdapter()
        binding.rvHistory.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = historyAdapter
        }
    }

    override fun initView() {
        initViewModel()
        initList()
    }

    override fun initViewModel() {
        sessionPreference = SessionPreference(this)
        sessionPreference.authToken =
            "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJfaWQiOiI2MTA5MmI5Y2Q0YTU2ZjAwMTdkYjE0M2QiLCJ1c2VybmFtZSI6ImFmZmFkZGQiLCJlbWFpbCI6ImFmZmFkZGRAZ21haWwuY29tIiwiaWF0IjoxNjI4MTM1OTk5LCJleHAiOjE2MjgxNDMxOTl9.iNU7KGjlHu0fii1VUQ9bU79VKl4E_79wl5katriCX_I"
        val apiServices = HistoryApiServices.getInstance(sessionPreference)
        apiServices?.let {
            val dataSource = HistoryDataSource(it)
            val repository = HistoryRepository(dataSource)
            viewModel = GenericViewModelFactory(HistoryViewModel(repository))
                .create(HistoryViewModel::class.java)
        }
        viewModel.newsData.observe(this, {
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
        viewModel.getHistory()
    }
}