package com.example.posteey

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.posteey.adapter.TabLayoutAdapter
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var adapter: TabLayoutAdapter
    private lateinit var newsFragmentTitleList: Array<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        newsFragmentTitleList = resources.getStringArray(R.array.news_categories)
        adapter = TabLayoutAdapter(this)
        pager.adapter = adapter
        TabLayoutMediator(tab_layout, pager) { tab, position ->
            tab.text = newsFragmentTitleList[position]
        }.attach()
    }
}
