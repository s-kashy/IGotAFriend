package com.example.mycomputer.igotafriend.activities;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.mycomputer.igotafriend.AccountInfo;
import com.example.mycomputer.igotafriend.Intro;
import com.example.mycomputer.igotafriend.R;
import com.example.mycomputer.igotafriend.validetor.AccountInfoHolder;
import com.firebase.ui.auth.AuthUI;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    public static final String ANONYMOUS = "anonymous";
    public static final int DEFAULT_MSG_LENGTH_LIMIT = 1000;
    private ChildEventListener childEventListener;
    private String uid;
    private FirebaseDatabase mFireBaseDataBase;//->reference the to the entrey point to the database
    private DatabaseReference userDataBaseRefernce;//->reference a spcifc data base
    private String mUserUid;
    private FirebaseAuth mFirebaseAuth;
    private FirebaseAuth.AuthStateListener mAuthStateLisener;
    private static final int RC_SIGN_IN = 1;
    private AccountInfo accountInfo;
    private String email;
    private String disaplayName;
    private ProgressBar mProgressBar;
    private Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mProgressBar = (ProgressBar) findViewById(R.id.progressBar);


        mFireBaseDataBase = FirebaseDatabase.getInstance();
        //->get the main accesse point to the database
        mFirebaseAuth = FirebaseAuth.getInstance();
        //->inetiolize the firebase Athu just like for the database

        mProgressBar.setVisibility(ProgressBar.VISIBLE);
//        //->get referece to a specefic node
//        // Initialize references to views
//
        mAuthStateLisener = new FirebaseAuth.AuthStateListener() {
            //when sign in
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {//firebaseAuth gerente that the user is signin
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    email = user.getEmail();
                    disaplayName = user.getDisplayName();
                    onSignInInitialize(user.getUid());

                } else {
                    //if not sign in
                    startActivityForResult(AuthUI.getInstance().
                            createSignInIntentBuilder().//
                            setIsSmartLockEnabled(false).//

                            setProviders(AuthUI.EMAIL_PROVIDER, AuthUI.GOOGLE_PROVIDER)//
                            .setLogo(R.drawable.logo)

                            .build(), RC_SIGN_IN);
                }
            }
        };
    }

    @Override
    protected void onResume() {
        super.onResume();
        mFirebaseAuth.addAuthStateListener(mAuthStateLisener);
    }

    private void onSignInInitialize(String uid) {
        mUserUid = uid;
        //   accountVister.setDispalyName(displayName);
        checkUserName();
    }


    public void checkUserName() {
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference ref = database.getReference("Users").child(mUserUid);
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //  ref.removeEventListener(this);
                if (dataSnapshot.exists()) {
                    AccountInfoHolder.accountInfo = (AccountInfo) dataSnapshot.getValue(AccountInfo.class);

                    Intent intent = new Intent(MainActivity.this, ChatActivity.class);

                    startActivity(intent);
                    finish();
                    return;
                }

                dialog = new Dialog(MainActivity.this);
                View v = MainActivity.this.getLayoutInflater().inflate(R.layout.intro, null);
                TextView iv = (TextView) v.findViewById(R.id.txIntro);
                Button btnSubmit = (Button) v.findViewById(R.id.btnSubmit);
                iv.setText(Intro.getMesg());

                dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
                dialog.setCancelable(false);
                dialog.setContentView(v);


                btnSubmit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(MainActivity.this, ProfileActivity.class);
                        intent.putExtra("key", mUserUid);
                        intent.putExtra("email", email);
                        intent.putExtra("dispaly", disaplayName);

                        startActivity(intent);
                        dialog.dismiss();
                        finish();

                    }
                });

                dialog.show();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                ref.removeEventListener(this);

            }
        });
    }
}


