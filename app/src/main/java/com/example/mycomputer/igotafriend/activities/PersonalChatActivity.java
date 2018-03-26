package com.example.mycomputer.igotafriend.activities;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.example.mycomputer.igotafriend.AccountInfo;
import com.example.mycomputer.igotafriend.AccountInfoFirebaseUpdater;

import com.example.mycomputer.igotafriend.Chat;
import com.example.mycomputer.igotafriend.ChatMessage;
import com.example.mycomputer.igotafriend.ChatPersonalAdpter;

import com.example.mycomputer.igotafriend.DateFormat;
import com.example.mycomputer.igotafriend.AlarmReceiver;
import com.example.mycomputer.igotafriend.R;

import com.example.mycomputer.igotafriend.validetor.AccountInfoHolder;
import com.firebase.ui.auth.AuthUI;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class PersonalChatActivity extends AppCompatActivity {
    private ListView listView;
    private FirebaseDatabase mFireBaseDataBase;//->reference the to the entrey point to the database
    private DatabaseReference mDataReference;//->reference a spcifc data base
    private ArrayList<ChatMessage> chatMessages;
    private ChatPersonalAdpter chatPersonalAdpter;
    private ChildEventListener childEventListener;
    private AccountInfo accountInfoMe;
    private ChatMessage msg;
    private Calendar calendar;

    AccountInfo accountInfoOther;
    private AlarmManager alarmMgr;
    private PendingIntent alarmIntent;
    private EditText etMsg;
    private String visterId;
    private String localId;
    private Button btnSend;
    Date date;
    private Chat chat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_chat);
        mFireBaseDataBase = FirebaseDatabase.getInstance();
        etMsg = (EditText) findViewById(R.id.messageEditText);
        btnSend = (Button) findViewById(R.id.sendButton);
        chatMessages = new ArrayList<>();

        listView = (ListView) findViewById(R.id.messageListView);
        chatPersonalAdpter = new ChatPersonalAdpter(this, 0, chatMessages);
        listView.setAdapter(chatPersonalAdpter);
        Intent intent1 = getIntent();
        if (intent1.getExtras() != null) {
            chat = (Chat) intent1.getSerializableExtra("key");
            accountInfoMe = AccountInfoHolder.accountInfo;


            visterId = chat.getVisterUdi();
            localId = chat.getLocalUdi();
            Chat chatMe;
            if (accountInfoMe.getUid().equals(chat.getVisterUdi())) {
                setAccountInfoOther(chat.getLocalUdi());

                chatMe = accountInfoMe.getChatByLOcal(localId);


            } else {
                setAccountInfoOther(chat.getVisterUdi());
                chatMe = accountInfoMe.getChatByViser(visterId);
            }
            for (ChatMessage msg : chatMe.getMessages()) {
                chatMessages.add(msg);

            }
            chatPersonalAdpter.notifyDataSetChanged();
        }

        mFireBaseDataBase = FirebaseDatabase.getInstance();


        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (etMsg.getText().toString() != null && etMsg.getText().toString().trim().length() > 0) {
                    msg = new ChatMessage(accountInfoMe.getDispalyName().toString(), etMsg.getText().toString());
                    if (accountInfoMe.getUid().equals(localId)) {
                        Chat chatMe = accountInfoMe.getChatByViser(visterId);
                        chatMe.addMsg(msg);
                        Chat chatOther = accountInfoOther.getChatByLOcal(localId);
                        chatOther.addMsg(msg);

                    } else {
                        Chat chatMe = accountInfoMe.getChatByLOcal(localId);
                        chatMe.addMsg(msg);
                        Chat chatOther = accountInfoOther.getChatByViser(visterId);
                        chatOther.addMsg(msg);
                    }

                    AccountInfoFirebaseUpdater accountInfoFirebaseUpdater = new AccountInfoFirebaseUpdater();
                    accountInfoFirebaseUpdater.update(accountInfoOther, null);
                    accountInfoFirebaseUpdater.update(accountInfoMe, null);
                    etMsg.setText("");
                    chatMessages.add(msg);
                    chatPersonalAdpter.notifyDataSetChanged();
                }
            }
        });
        setAlerm();
//        if (alarmMgr != null) {
//            alarmMgr.cancel(alarmIntent);
//        } else {
//            setAlerm();
//        }

    }

    private void setAlerm() {
//
//        date = DateFormat.formatForStringToDate(accountInfoMe.getSearchPreferernce().getStartDate());
//        calendar = Calendar.getInstance();
//        calendar.set(Calendar.MONTH, date.getMonth());
//        calendar.set(Calendar.YEAR, date.getYear());
//        calendar.set(Calendar.DAY_OF_MONTH, date.getDay());
//        calendar.set(Calendar.HOUR_OF_DAY, 18);
//        calendar.set(Calendar.MINUTE, 26);
//        calendar.set(Calendar.SECOND, 0);
//        alarmMgr = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
//        Intent intent = new Intent(getApplicationContext(), AlarmReceiver.class);
//        alarmIntent = PendingIntent.getBroadcast(getApplicationContext(), 0, intent, 0);
//
//
//        alarmIntent = PendingIntent.getBroadcast(getApplicationContext(), 0, intent, 0);
//
//        alarmMgr.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
//                1000 * 60 * 20, alarmIntent);

    }

    private void setAccountInfoOther(String uid) {
        FirebaseDatabase mfirebase = FirebaseDatabase.getInstance();
        DatabaseReference ref = mfirebase.getReference().child("Users").child(uid);
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                accountInfoOther = (AccountInfo) dataSnapshot.getValue(AccountInfo.class);

            }


            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    public boolean onCreateOptionsMenu(Menu menu) {
        if (accountInfoMe.getUid().equals(chat.getVisterUdi())&&!chat.isRate()) {
            MenuInflater inflater = getMenuInflater();
            inflater.inflate(R.menu.rate_menu, menu);
            return true;
        }
        return false;

    }

    public boolean onOptionsItemSelected(MenuItem item) {

            switch (item.getItemId()) {
                case R.id.rate:
                    Intent intent1 = new Intent(this, RateActivity.class);
                    intent1.putExtra("localAccountInfo", accountInfoOther);
                    startActivity(intent1);
                    finish();
                    return true;
                default:
                    return super.onOptionsItemSelected(item);
            }

        }

    }


