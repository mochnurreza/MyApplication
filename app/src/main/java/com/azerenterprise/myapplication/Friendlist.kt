package com.azerenterprise.myapplication

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.activity_friendlist.*

class Friendlist : AppCompatActivity() {

    val adapter = GroupAdapter<ViewHolder>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_friendlist)

        fetchUser()



    }

    private fun fetchUser() {
        val firedb = FirebaseDatabase.getInstance().getReference("/user/")
        firedb.addListenerForSingleValueEvent(object : ValueEventListener{
            override fun onCancelled(p0: DatabaseError) {

            }

            override fun onDataChange(p0: DataSnapshot) {
                p0.children.forEach{
                    val user = it.getValue(User::class.java) as User
                    if(user != null){
                    adapter.add(AdapterFriendList(user))
                }}

                adapter.setOnItemClickListener{item, view ->
                    val friendItem = item as AdapterFriendList
                    val intent = Intent(view.context, Chatroom::class.java)
                    intent.putExtra(FRIEND_KEY,friendItem.user)
                    startActivity(intent)
                }
                rv_friend_list.adapter = adapter
            }

        })
    }


    companion object{

        val FRIEND_KEY = "friend_key"
        fun launchIntent(context: Context){
            val intent = Intent(context, Friendlist::class.java)
            context.startActivity(intent)
        }

    }
}