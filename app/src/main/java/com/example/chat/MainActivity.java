package com.example.chat;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ListView lvChatRoom;
    private Button btnNewRoomEnter;
    private EditText edtNewRoomName;
    private ArrayAdapter<String> roomAdapter;
    private ArrayList<String> roomList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lvChatRoom = (ListView) findViewById(R.id.lvChatRoom);
        btnNewRoomEnter = (Button) findViewById(R.id.btnNewRoomEnter);
        edtNewRoomName = (EditText) findViewById(R.id.edtNewRoomName);

        roomAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, roomList);
    }
}
