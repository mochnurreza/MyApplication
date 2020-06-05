package com.azerenterprise.myapplication

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.activity_chatroom.*

class Chatroom : AppCompatActivity() {

    lateinit  var friend :User
    val adapter = GroupAdapter<ViewHolder>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chatroom)

        friend = intent.getParcelableExtra<User>(Friendlist.FRIEND_KEY)
        supportActionBar!!.title = friend.name


        rv_chat_room_list.adapter = adapter

        initview()
        loadMessageFromFirebase()
    }

    private fun loadMessageFromFirebase() {
        val messageDbReference = FirebaseDatabase.getInstance().getReference("/message")
        messageDbReference.addChildEventListener(object : ChildEventListener{
            override fun onCancelled(p0: DatabaseError) {

            }

            override fun onChildMoved(p0: DataSnapshot, p1: String?) {

            }

            override fun onChildChanged(p0: DataSnapshot, p1: String?) {

            }

            override fun onChildAdded(p0: DataSnapshot, p1: String?) {
                val messageCollection = p0.getValue(Pesan::class.java)
                if(messageCollection != null){
                    if (messageCollection.fromid==FirebaseAuth.getInstance().uid){
                        adapter.add(Adapterpesanuntuk(messageCollection.text))
                    }else {
                        adapter.add(Adapterpesandari(messageCollection.text))
                    }
                }
            }

            override fun onChildRemoved(p0: DataSnapshot) {

            }

        })
    }

    private fun initview() {
        btn_chat_send.setOnClickListener{
            sendMessage()
        }
    }

    private fun sendMessage() {
        val message = et_chat.text.toString()
        val messageDbReference = FirebaseDatabase.getInstance().getReference("/message").push()
        val id = messageDbReference.key.toString()
        val fromid = FirebaseAuth.getInstance().uid.toString()
        val toid = friend.uid
        val text = et_chat.text.toString()
        val time = System.currentTimeMillis()/1000

        messageDbReference.setValue(Pesan(id, fromid, toid, text, time))

            .addOnSuccessListener {
                Toast.makeText(applicationContext, "berhasil dikirim", Toast.LENGTH_LONG).show()
            }
    }

    companion object{
        fun launchIntent(context: Context){
            val intent = Intent(context, Chatroom::class.java)
            context.startActivity(intent)
        }

    }
}