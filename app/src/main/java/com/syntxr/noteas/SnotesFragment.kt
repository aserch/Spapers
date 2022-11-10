package com.syntxr.noteas

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.syntxr.noteas.R
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.syntxr.noteas.note.NoteListFragment
import com.syntxr.noteas.todo.ToDoListFragment
import com.syntxr.noteas.ui.ViewPagerAdapter

class SnotesFragment : Fragment(R.layout.fragment_spapers) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val tabLayout = view.findViewById<TabLayout>(R.id.tab_layout)
        val viewPager = view.findViewById<ViewPager2>(R.id.view_pager2)
        val fragmentlist = listOf(NoteListFragment(), ToDoListFragment())
        val adapter = ViewPagerAdapter(requireParentFragment(),fragmentlist)
        viewPager.adapter = adapter

        val titles = listOf("Notes","Todo")
        TabLayoutMediator(tabLayout, viewPager){tab, position ->
            tab.text = titles[position]
        }.attach()

    }


}