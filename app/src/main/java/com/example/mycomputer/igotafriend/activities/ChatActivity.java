package com.example.mycomputer.igotafriend.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.example.mycomputer.igotafriend.AccountInfo;
import com.example.mycomputer.igotafriend.AccountInfoFirebaseUpdater;
import com.example.mycomputer.igotafriend.Chat;
import com.example.mycomputer.igotafriend.MainChatAdpter;

import com.example.mycomputer.igotafriend.R;
import com.example.mycomputer.igotafriend.validetor.AccountInfoHolder;
import com.firebase.ui.auth.AuthUI;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class ChatActivity extends AppCompatActivity {
    private ArrayList<Chat> messages;
    private MainChatAdpter mainChatAdpter;
    private ListView listView;
    private AccountInfo accountInfo;

    private static final String Tag = "from edit chat";
    private String serachChat;
    private static final int RC_EDIT = 1;
    private Button btnVister, btnLocal;


    private FirebaseDatabase mFireBaseDataBase;//->reference the to the entrey point to the database
    private DatabaseReference mMessageDataBaseRefernce;//->reference a spcifc data base
    private DatabaseReference mRefernceToLocalProfile;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        listView = (ListView) findViewById(R.id.lvChat);
        btnLocal = (Button) findViewById(R.id.btnLocal);
        btnVister = (Button) findViewById(R.id.btnVister);

        messages = new ArrayList<>();

        accountInfo = AccountInfoHolder.accountInfo;

        mainChatAdpter = new MainChatAdpter(this, 0, messages, accountInfo.getUid());

        listView.setAdapter(mainChatAdpter);

        for (Chat chat1 : accountInfo.getChats()) {
            messages.add(chat1);
            mainChatAdpter.notifyDataSetChanged();
        }

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Chat chat = mainChatAdpter.getItem(position);
                Intent intent1 = new Intent(ChatActivity.this, PersonalChatActivity.class);
                intent1.putExtra("key", chat);

                startActivity(intent1);


            }
        });


        btnLocal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (accountInfo.getProfileLocol() != null) {
                    Intent intent1 = new Intent(ChatActivity.this, ShowLocalProfileActivty.class);
                    intent1.putExtra("new", "viewByLocal");

                    startActivity(intent1);
                } else {
                    Intent intent1 = new Intent(ChatActivity.this, ShowLocalProfileActivty.class);
                    intent1.putExtra("new", "newLocal");

                    startActivity(intent1);
                }


            }
        });
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                final Chat chat = mainChatAdpter.getItem(position);
                final AlertDialog.Builder alertDialog = new AlertDialog.Builder(ChatActivity.this);
                alertDialog.setMessage("Are You Sure You Want To Delete!!!").setCancelable(false).setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mainChatAdpter.remove(chat);
                        accountInfo.getChats().remove(chat);
                        AccountInfoFirebaseUpdater accountInfoFirebaseUpdater = new AccountInfoFirebaseUpdater();
                        accountInfoFirebaseUpdater.update(accountInfo, null);

                    }
                }).setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                AlertDialog dialog = alertDialog.create();
                dialog.show();


                return false;
            }
        });

        btnVister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(ChatActivity.this, SearchLocalActivity.class);

                startActivity(intent1);
            }
        });
    }


    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.profile_menu, menu);

        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.profile:
                Intent intent = new Intent(ChatActivity.this, ProfileActivity.class);
                intent.putExtra("edit", Tag);
                intent.putExtra("key", accountInfo);
                startActivity(intent);
                finish();
                return true;
            case R.id.signout:
                AuthUI.getInstance().signOut(this);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

}