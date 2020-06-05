package com.azerenterprise.myapplication

import com.xwray.groupie.Item
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.itemchatdari.view.*

class Adapterpesandari(val text : String) : Item<ViewHolder>() {
    override fun getLayout(): Int {
        return R.layout.itemchatdari
    }

    override fun bind(viewHolder: ViewHolder, position: Int) {
        viewHolder.itemView.tv_pesan_dari.text = text
    }
}