package com.example.jobbys

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.ktx.Firebase
import com.example.jobbys.Message as Message

class Chats : AppCompatActivity() {

    private lateinit var chatRecycleView : RecyclerView
    private lateinit var messageBox : EditText
    private lateinit var sendButton : ImageView
    private lateinit var messageAdapter: MessageAdapter
    private lateinit var messageList: ArrayList<Message>
    private lateinit var dbf : DatabaseReference


    var receiverRoom : String? = null
    var senderRoom : String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chats)

        val name =  intent.getStringExtra("name")
        val receiveruid = intent.getStringExtra("uid")

        val senderUI = FirebaseAuth.getInstance().currentUser?.uid
        dbf =  FirebaseDatabase.getInstance().getReference()

        senderRoom = receiveruid + senderUI
        receiverRoom = senderUI + receiveruid

        supportActionBar?.title = name

        chatRecycleView = findViewById(R.id.chatsRecycleView)
        messageBox = findViewById(R.id.messageBox)
        sendButton = findViewById(R.id.sendImage)
        messageList = ArrayList()
        messageAdapter = MessageAdapter(this,messageList)


        chatRecycleView.layoutManager = LinearLayoutManager(this)
        chatRecycleView.adapter = messageAdapter

        dbf.child("chats").child(senderRoom!!).child("messages")
            .addValueEventListener(object : ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    messageList.clear()
                    for (postSnapshot in snapshot.children){
                        val message = postSnapshot.getValue(Message::class.java)
                        messageList.add(message!!)
                    }
                    messageAdapter.notifyDataSetChanged()
                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }

            })

        sendButton.setOnClickListener {
            Toast.makeText(this,"Hello Im A Message uwu", Toast.LENGTH_SHORT).show()
            var message = messageBox.text.toString()
            var messageObject = Message( message, senderUI)
            dbf.child("chats").child(senderRoom!!).child("messages").push()
                .setValue(messageObject).addOnSuccessListener {
                    dbf.child("chats").child(receiverRoom!!).child("messages").push()
                        .setValue(messageObject)
                }
            messageBox.setText("")

        }
    }

}