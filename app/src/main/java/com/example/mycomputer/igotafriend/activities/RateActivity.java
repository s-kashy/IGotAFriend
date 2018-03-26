package com.example.mycomputer.igotafriend.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.RatingBar;

import com.example.mycomputer.igotafriend.AccountInfo;
import com.example.mycomputer.igotafriend.AccountInfoFirebaseUpdater;
import com.example.mycomputer.igotafriend.Chat;
import com.example.mycomputer.igotafriend.R;
import com.example.mycomputer.igotafriend.validetor.AccountInfoHolder;

public class RateActivity extends AppCompatActivity {
    RatingBar ratingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rate);
        Intent intent = getIntent();

        final AccountInfo accountInfo = (AccountInfo) intent.getSerializableExtra("localAccountInfo");


        ratingBar = (RatingBar) findViewById(R.id.ratingBar);
        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                accountInfo.getProfileLocol().addRate(rating);
                Chat chat1=accountInfo.getChatByViser(AccountInfoHolder.accountInfo.getUid());
                chat1.setRate(true);
                Chat chat = AccountInfoHolder.accountInfo.getChatByLOcal(accountInfo.getUid());
                chat.setRate(true);
                AccountInfoFirebaseUpdater accountInfoFirebaseUpdater = new AccountInfoFirebaseUpdater();
                accountInfoFirebaseUpdater.update(accountInfo, null);
                Intent intent1=new Intent(RateActivity.this,PersonalChatActivity.class);
                intent1.putExtra("key",chat1);
                startActivity(intent1);
                finish();

            }
        });
    }
}
