package com.azerenterprise.myapplication

import com.squareup.picasso.Picasso
import com.xwray.groupie.Item
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.friendlist.view.*

class AdapterFriendList(val user:User) : Item <ViewHolder>() {
    override fun getLayout(): Int {
        return R.layout.friendlist
    }

    override fun bind(viewHolder: ViewHolder, position: Int) {

        val itemView = viewHolder.itemView
        itemView.et_friend_name.text   = user.name
        Picasso.get().load(user.photo).into(itemView.iv_friend_photo)
    }
}