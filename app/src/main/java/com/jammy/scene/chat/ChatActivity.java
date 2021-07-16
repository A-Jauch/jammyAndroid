package com.jammy.scene.chat;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.jammy.R;
import com.jammy.fileManager.FileManager;
import com.jammy.model.Message;
import com.jammy.responseModel.ResponseCreateMessage;
import com.jammy.responseModel.ResponseError;
import com.jammy.responseModel.ResponseMessage;
import com.jammy.retrofit.RetrofitClientInstance;
import com.jammy.routes.MessageRoutes;
import com.jammy.scene.friends.FriendsAdapter;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChatActivity extends AppCompatActivity {
    FileManager fileManager = new FileManager();
    String token = "Bearer " + fileManager.readFile("token.txt").trim();
    ChatAdapter chatAdapter;
    RecyclerView.LayoutManager layoutManager;
    private List<Message> messageList = new ArrayList<>();
    ImageView sendMessageImage;
    private int receiver_id;
    private int sender_id;
    EditText messageContent;
    private MessageRoutes messageRoutes;

    public static final String ID_USER_CHAT_RECEIVER = "com.jammy.scene.chat.ID_USER_CHAT_RECEIVER";
    public static final String ID_USER_CHAT_SENDER = "com.jammy.scene.chat.ID_USER_CHAT_SENDER";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        sendMessageImage = findViewById(R.id.send_message_image);
        messageContent = findViewById(R.id.message_input);
        Intent intent = getIntent();
         receiver_id = intent.getIntExtra(FriendsAdapter.ID_USER_RECEIVER, 0);
         sender_id = intent.getIntExtra(FriendsAdapter.ID_USER_SENDER, 0);

        if (receiver_id ==0 && sender_id == 0){
            receiver_id =  intent.getIntExtra(ChatActivity.ID_USER_CHAT_RECEIVER, 0);
            sender_id =  intent.getIntExtra(ChatActivity.ID_USER_CHAT_SENDER, 0);
        }


        sendMessageImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(messageContent.getText().toString())){
                    Toast.makeText(ChatActivity.this, "Cannot be empty", Toast.LENGTH_SHORT).show();
                } else {

                    String content = messageContent.getText().toString();
                    Message message = new Message(content, receiver_id, sender_id);
                    sendMessage(message);
                    messageContent.getText().clear();
                    Intent intent1 = new Intent(ChatActivity.this, ChatActivity.class);
                    intent1.putExtra(ID_USER_CHAT_RECEIVER, receiver_id);
                    intent1.putExtra(ID_USER_CHAT_SENDER, sender_id);
                    startActivity(intent1);
                    overridePendingTransition(0,0);
                    finish();
                   // retrieveMessage(receiver_id,sender_id);
                }
            }
        });

        retrieveMessage(receiver_id,sender_id);
        retrieveMessage(sender_id,receiver_id);
    }

    private void retrieveMessage(int receiver_id, int sender_id) {
        messageRoutes = RetrofitClientInstance.getRetrofitInstance().create(MessageRoutes.class);
        Call<ResponseMessage> getMessageByReceiverAndSender = messageRoutes.findByReceiverAndSender(receiver_id,sender_id,token);
        getMessageByReceiverAndSender.enqueue(new Callback<ResponseMessage>() {
            @Override
            public void onResponse(Call<ResponseMessage> call, Response<ResponseMessage> response) {
                if (response.isSuccessful()){
                    ResponseMessage responseMessage = response.body();
                    populateChatAdapter(responseMessage.getResults());
                } else {
                    Gson gson = new Gson();
                    ResponseError errorMessage = gson.fromJson(response.errorBody().charStream(), ResponseError.class);
                    Toast.makeText(ChatActivity.this, errorMessage.getMessage(), Toast.LENGTH_SHORT).show();
                }

                if (response.code() == 500){
                    Toast.makeText(ChatActivity.this, response.errorBody().toString(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseMessage> call, Throwable t) {
                Toast.makeText(ChatActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void populateChatAdapter(List<Message> results) {
        for (Message message: results){
            messageList.add(message);
            RecyclerView rvMessage = findViewById(R.id.rvMessage);
            rvMessage.setHasFixedSize(true);
            layoutManager = new LinearLayoutManager(this);
            rvMessage.setLayoutManager(layoutManager);
            chatAdapter = new ChatAdapter(this, messageList, rvMessage);
            chatAdapter.submitList(messageList);
            rvMessage.setAdapter(chatAdapter);
        }
    }

    private void updateRv(){
    }

    private void sendMessage(Message message) {
        messageRoutes = RetrofitClientInstance.getRetrofitInstance().create(MessageRoutes.class);
        Call<ResponseCreateMessage> sendMessage = messageRoutes.postMessage(message,token);
        sendMessage.enqueue(new Callback<ResponseCreateMessage>() {
            @Override
            public void onResponse(Call<ResponseCreateMessage> call, Response<ResponseCreateMessage> response) {
                if (response.isSuccessful()){
                    ResponseCreateMessage responseMessage = response.body();
                    Toast.makeText(ChatActivity.this,   responseMessage.getResults().getContent(), Toast.LENGTH_SHORT).show();
                   // Intent intent = new Intent(CreatePostActivity.this, PostReceiver.class);
                   // intent.putExtra(ID_POST_CREATED_THREAD, responsePost.getResults().getThread_id());
                  //  startActivity(intent);
                  //  finish();
                } else {
                    Gson gson = new Gson();
                    ResponseError errorMessage = gson.fromJson(response.errorBody().charStream(), ResponseError.class);
                    Toast.makeText(ChatActivity.this, errorMessage.getMessage(), Toast.LENGTH_SHORT).show();
                }

                if (response.code() == 500){
                    Toast.makeText(ChatActivity.this, response.errorBody().toString(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseCreateMessage> call, Throwable t) {
                Toast.makeText(ChatActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}