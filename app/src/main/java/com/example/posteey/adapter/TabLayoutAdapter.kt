package com.example.posteey.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.posteey.features.*

const val TOTAL_NEWS = 7
class TabLayoutAdapter(
    fragmentActivity: FragmentActivity
): FragmentStateAdapter(fragmentActivity) {

    private val newsFragmentList: List<BaseNewsFragment> = listOf(
        GeneralNewsFragment.newInstance(),
        BusinessNewsFragment.newInstance(),
        TechnologyNewsFragment.newInstance(),
        EntertainmentNewsFragment.newInstance(),
        HealthNewsFragment.newInstance(),
        ScienceNewsFragment.newInstance(),
        SportsNewsFragment.newInstance()
    )

    override fun getItemCount(): Int = TOTAL_NEWS

    override fun createFragment(position: Int): Fragment {
       return newsFragmentList[position]
    }
}
