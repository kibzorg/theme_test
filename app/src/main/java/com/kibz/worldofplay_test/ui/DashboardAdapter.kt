package com.kibz.worldofplay_test.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.kibz.worldofplay_test.R
import com.kibz.worldofplay_test.data.Story
import com.kibz.worldofplay_test.databinding.ItemDashboardBinding
import com.kibz.worldofplay_test.ui.callback.Interfaces

class DashboardAdapter(
    dataList1: ArrayList<Story>,
    private val listener: Interfaces.OnRecyclerItemClick
) :
    RecyclerView.Adapter<DashboardAdapter.DashboardHolder>() {

    private var dataList = ArrayList(dataList1)

    fun updateData(newList: List<Story>) {
        dataList.clear()
        dataList = ArrayList(newList)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DashboardHolder {
        val binding = DataBindingUtil.inflate<ItemDashboardBinding>(
            LayoutInflater.from(parent.context), R.layout.item_dashboard,
            parent, false
        )
        return DashboardHolder(binding)
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    override fun onBindViewHolder(holder: DashboardHolder, position: Int) {
        holder.bind(dataList[position], listener)
    }

    class DashboardHolder(var bindingView: ItemDashboardBinding) :
        RecyclerView.ViewHolder(bindingView.root) {
        fun bind(
            story: Story, listener: Interfaces.OnRecyclerItemClick
        ) {
            bindingView.story = story
            bindingView.listener = listener
            bindingView.executePendingBindings()
        }
    }
}