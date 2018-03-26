package com.example.mycomputer.igotafriend.activities;

import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mycomputer.igotafriend.AccountInfo;
import com.example.mycomputer.igotafriend.AccountInfoFirebaseUpdater;
import com.example.mycomputer.igotafriend.Chat;
import com.example.mycomputer.igotafriend.CheckBoxService;
import com.example.mycomputer.igotafriend.ICheckBoxService;
import com.example.mycomputer.igotafriend.IUpdateFireBase;

import com.example.mycomputer.igotafriend.R;
import com.example.mycomputer.igotafriend.validetor.AccountInfoHolder;
import com.example.mycomputer.igotafriend.validetor.LocalValider.ValiderOfLocal;
import com.firebase.ui.auth.AuthUI;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.function.Consumer;

@RequiresApi(api = Build.VERSION_CODES.N)
public class ShowLocalProfileActivty extends AppCompatActivity implements Consumer<Boolean> {
    public static String TAG = "SearchPersonlActivty";
    private Button btnStartChat;
    private LinearLayout lineLeft;
    private LinearLayout lineRight;
    private TextView txFindPlace;
    private Chat chat;
    private CheckBox checkBox;
    private RatingBar ratingBar;
    private de.hdodenhof.circleimageview.CircleImageView imageView;
    private EditText txLocalOfer, txBasicInfoLocal;
    private TextView txName;

    private AccountInfo accountInfoLocal;
    private boolean isALocal = false;
    private static final String typeEdit = "newLocal";
    //private   String typeEdit = getResources().getString(R.string.newLocal);
    private final String typeViewByVister = "viewByVister";
    private final String rateByVister="rate";
    // private   String typeViewByVister=getResources().getString(R.string.vister);
    private static final String typeViewByLocal = "viewByLocal";
    //  private   String typeViewByLocal=getResources().getString(R.string.viewByLocal);
    private FirebaseDatabase mFireBaseDataBase;//->reference the to the entrey pointto the database
    private DatabaseReference mNewLocalDataReference;
    private ICheckBoxService iCheckBoxService = new CheckBoxService();
    private AccountInfo accountVister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_local_profile);

        txName = (TextView) findViewById(R.id.txName);
        lineLeft = (LinearLayout) findViewById(R.id.lineLeft);
        lineRight = (LinearLayout) findViewById(R.id.lineRight);
        ratingBar = (RatingBar) findViewById(R.id.ratingBar);
        txLocalOfer = (EditText) findViewById(R.id.txLocalOfer);
        imageView = (de.hdodenhof.circleimageview.CircleImageView) findViewById(R.id.imageLocal);
        iCheckBoxService.creatCheckBox(getApplicationContext(), lineRight, lineLeft);
        txBasicInfoLocal = (EditText) findViewById(R.id.txBasicInfoLocal);
        btnStartChat = (Button) findViewById(R.id.btnStartChat);
        btnStartChat.setVisibility(View.INVISIBLE);
        accountInfoLocal = new AccountInfo();

        iCheckBoxService.diabableAll(false, lineRight, lineLeft, txLocalOfer, txBasicInfoLocal);
        Intent intent = getIntent();
        if (intent.getExtras() != null) {

            if (typeEdit.equals(intent.getExtras().getString("new"))) {
                isALocal = true;

                accountInfoLocal =AccountInfoHolder.accountInfo;
                txName.setText(accountInfoLocal.getDispalyName().toString());
                setTitle("Welcom " + accountInfoLocal.getDispalyName().toString());
                if (accountInfoLocal.getPhotoUri() != null) {
                    Picasso.with(this).load(accountInfoLocal.getPhotoUri().toString()).into(imageView);
                }
                iCheckBoxService.diabableAll(true, lineRight, lineLeft, txLocalOfer, txBasicInfoLocal);
                btnStartChat.setVisibility(View.VISIBLE);
                btnStartChat.setText("Submit");

            } else if (typeViewByLocal.equals(intent.getExtras().getString("new"))) {
                isALocal = true;

                accountInfoLocal =AccountInfoHolder.accountInfo;
                if (accountInfoLocal.getPhotoUri() != null) {
                    Picasso.with(this).load(accountInfoLocal.getPhotoUri().toString()).into(imageView);
                }
                setTitle("Welcome " + accountInfoLocal.getDispalyName().toString());
                txName.setText(accountInfoLocal.getDispalyName().toString());
                iCheckBoxService.checkboxViewForVister(accountInfoLocal.getProfileLocol().getListOfInterests(), lineRight, lineLeft);

                txBasicInfoLocal.setText(accountInfoLocal.getProfileLocol().getBasicInfo().toString());
                txLocalOfer.setText(accountInfoLocal.getProfileLocol().getWhatDoIOffer().toString());
                btnStartChat.setText("Submit");
            } else if (typeViewByVister.equals(intent.getExtras().getString("new"))) {
                accountInfoLocal = (AccountInfo) intent.getSerializableExtra("key");
                accountVister = AccountInfoHolder.accountInfo;
                Log.d("vister" + 1, accountInfoLocal.getUid());
                Log.d("vister" + 2, accountVister.getUid());

                txName.setText(accountInfoLocal.getDispalyName().toString());
                setTitle("The local info " + accountInfoLocal.getDispalyName().toString());
                txLocalOfer.setText(accountInfoLocal.getProfileLocol().getWhatDoIOffer().toString());
                Picasso.with(this).load(accountInfoLocal.getPhotoUri().toString()).into(imageView);
                txBasicInfoLocal.setText(accountInfoLocal.getProfileLocol().getBasicInfo().toString());
                iCheckBoxService.checkboxViewForVister(accountInfoLocal.getProfileLocol().getListOfInterests(), lineRight, lineLeft);
                iCheckBoxService.diabableAll(false, lineRight, lineLeft, txLocalOfer, txBasicInfoLocal);


                if (!accountVister.isChetExistWithLocal(accountInfoLocal.getUid())) {
                    btnStartChat.setText("Add to chat");
                    btnStartChat.setVisibility(View.VISIBLE);
                    chat = new Chat();
                    chat.setVisterUdi(accountVister.getUid());
                    chat.setLocalUdi(accountInfoLocal.getUid());
                }

            }
            else if (rateByVister.equals(intent.getExtras().getString("key"))){
                AccountInfo accountInfo=(AccountInfo)intent.getSerializableExtra("local");
                ratingBar.setIsIndicator(true);
                setTitle(accountInfo.getDispalyName().toString());
                btnStartChat.setText("Rate");
            }
        }
        btnStartChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isALocal) {
                    accountInfoLocal.setProfileLocol();
                    getallFilleds(accountInfoLocal);
                    accountInfoLocal.getProfileLocol().clearIntrest();
                    iCheckBoxService.getAllInterstsCheck(lineRight, lineLeft, accountInfoLocal.getProfileLocol().getListOfInterests());
                    Validet();
                } else {
                    Intent intent1 = new Intent(ShowLocalProfileActivty.this, ChatActivity.class);
                    mFireBaseDataBase = FirebaseDatabase.getInstance();

                    accountInfoLocal.addChat(chat);
                    accountVister.addChat(chat);
                    AccountInfoHolder.accountInfo=accountVister;
                    AccountInfoFirebaseUpdater accountInfoFirebaseUpdater = new AccountInfoFirebaseUpdater();
                    accountInfoFirebaseUpdater.update(accountInfoLocal, null);
                    accountInfoFirebaseUpdater.update(accountVister, null);

                    startActivity(intent1);
                    finish();

                }
            }
        });

    }

    private void Validet() {
        ValiderOfLocal validerOfLocal = new ValiderOfLocal();
        String res = validerOfLocal.profileValeder(accountInfoLocal.getProfileLocol());
        if (res != null) {
            Toast.makeText(this, res.toString(), Toast.LENGTH_SHORT).show();
        } else {
            upadate();
        }
    }

    private void upadate() {
        IUpdateFireBase<AccountInfo> updater = new AccountInfoFirebaseUpdater();
        updater.update(accountInfoLocal, this);
        //updater.update(new AccountInfo(accountVister.uuid).setProfileLocal(pl).setSearchPreference(sp));
    }

    @Override
    public void accept(Boolean aBoolean) {
        if (aBoolean) {
            Intent intent1 = new Intent(ShowLocalProfileActivty.this, ChatActivity.class);
            intent1.putExtra("key", accountInfoLocal);
            startActivity(intent1);
            finish();
        }
    }

    private void getallFilleds(AccountInfo accountInfo) {
        accountInfo.getProfileLocol().setWhatDoIOffer(txLocalOfer.getText().toString());
        accountInfo.getProfileLocol().setBasicInfo(txBasicInfoLocal.getText().toString());
    }


    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        if (isALocal) inflater.inflate(R.menu.profile_menu, menu);
        else {
            inflater.inflate(R.menu.signout_menu, menu);
        }
        return true;


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (isALocal) {
            switch (item.getItemId()) {
                case R.id.profile:
                    iCheckBoxService.diabableAll(true, lineRight, lineLeft, txLocalOfer, txBasicInfoLocal);
                    btnStartChat.setVisibility(View.VISIBLE);
                    return true;
                case R.id.signout:
                    AuthUI.getInstance().signOut(this);
                    return true;
            }
        } else {
            switch (item.getItemId()) {
                case R.id.signout:
                    AuthUI.getInstance().signOut(this);
                    return true;
                default:

            }
        }
        return super.onOptionsItemSelected(item);
    }


}
