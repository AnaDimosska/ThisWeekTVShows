package com.example.thisweektvshows.util

import androidx.recyclerview.widget.DiffUtil

class DiffUtilCallback(private val oldList: List<Any>, private val newList: List<Any>) :
    DiffUtil.Callback() {

    // old size
    override fun getOldListSize(): Int = oldList.size

    // new list size
    override fun getNewListSize(): Int = newList.size

    // if items are same
    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldItem = oldList[oldItemPosition]
        val newItem = newList?.get(newItemPosition)
        return oldItem.javaClass == newItem?.javaClass
    }

    // check if contents are same
    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldItem = oldList[oldItemPosition]
        val newItem = newList?.get(newItemPosition)

        return oldItem.hashCode() == newItem.hashCode()
    }
}