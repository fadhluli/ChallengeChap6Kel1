package com.fadtech.challengechap6kel1.ui.landingpage

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.fadtech.challengechap6kel1.R
import com.fadtech.challengechap6kel1.databinding.ActivityLandingPageBinding
import com.fadtech.challengechap6kel1.databinding.FragmentLandingPage3Binding
import com.fadtech.challengechap6kel1.ui.landingpage.adapter.ViewPagerAdapter
import com.fadtech.challengechap6kel1.ui.landingpage.fragment.LandingPage1Fragment
import com.fadtech.challengechap6kel1.ui.landingpage.fragment.LandingPage2Fragment
import me.relex.circleindicator.CircleIndicator
import com.fadtech.challengechap6kel1.ui.landingpage.fragment.LandingPage3Fragment as LandingPage3Fragment

class LandingPageActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLandingPageBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLandingPageBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()
        initViewPager()
        onPageChange()
    }

    private fun initViewPager() {
        val viewPagerAdapter = ViewPagerAdapter(supportFragmentManager, lifecycle)
        viewPagerAdapter.addFragment(
            LandingPage1Fragment()
        )
        viewPagerAdapter.addFragment(
            LandingPage2Fragment()
        )
        viewPagerAdapter.addFragment(
            LandingPage3Fragment()
        )
        binding.viewPager.apply {
            adapter = viewPagerAdapter
        }
        binding.circleIndicator.setViewPager(binding.viewPager)
    }

    private fun onPageChange() {
        val viewPager = binding.viewPager
        val ivBtnNext = binding.ivBtnNext
        val circleIndicator = binding.circleIndicator

        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                if (position == 2) {
                    ivBtnNext.visibility = View.GONE
                    circleIndicator.visibility = View.GONE

                } else {
                    ivBtnNext.visibility = View.VISIBLE
                    circleIndicator.visibility = View.VISIBLE
                    onClick()
                }
            }
        })
    }

    private fun onClick() {
        binding.ivBtnNext.setOnClickListener {
            binding.viewPager.apply {
                beginFakeDrag()
                fakeDragBy(-10f)
                endFakeDrag()
            }
        }
    }
}