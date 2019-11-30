package com.example.chat;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ListView lvChatRoom;
    private Button btnNewRoomEnter;
    private EditText edtNewRoomName;
    private ArrayAdapter<String> roomAdapter;
    private ArrayList<String> roomList;
    private String Username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lvChatRoom = (ListView) findViewById(R.id.lvChatRoom);
        btnNewRoomEnter = (Button) findViewById(R.id.btnNewRoomEnter);
        edtNewRoomName = (EditText) findViewById(R.id.edtNewRoomName);

        roomAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, roomList);
        DialogRequestUserName();

        btnNewRoomEnter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference mReference = database.getReference(edtNewRoomName.getText().toString());
                mReference.setValue("");
            }
        });
    }

    private void DialogRequestUserName() {
        Context context = this;
        final AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Input Username");
        final EditText editUsername = new EditText(this);
        builder.setView(editUsername);

        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Username = editUsername.getText().toString();
            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });
        builder.show();
    }
}
