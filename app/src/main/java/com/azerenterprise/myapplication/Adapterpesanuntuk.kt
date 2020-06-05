package com.azerenterprise.myapplication

import com.xwray.groupie.Item
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.itemchatdari.view.*
import kotlinx.android.synthetic.main.itemchatuntuk.view.*

class Adapterpesanuntuk(val text : String) : Item<ViewHolder>() {
    override fun getLayout(): Int {
        return R.layout.itemchatuntuk
    }

    override fun bind(viewHolder: ViewHolder, position: Int) {
        viewHolder.itemView.tv_pesan_untuk.text = text
    }
}