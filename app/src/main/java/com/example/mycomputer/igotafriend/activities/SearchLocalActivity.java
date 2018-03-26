package com.example.mycomputer.igotafriend.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mycomputer.igotafriend.AccountInfo;
import com.example.mycomputer.igotafriend.AccountInfoFirebaseUpdater;
import com.example.mycomputer.igotafriend.CheckBoxService;
import com.example.mycomputer.igotafriend.DateFormat;
import com.example.mycomputer.igotafriend.ICheckBoxService;
import com.example.mycomputer.igotafriend.IUpdateFireBase;
import com.example.mycomputer.igotafriend.Interests;
import com.example.mycomputer.igotafriend.R;
import com.example.mycomputer.igotafriend.validetor.AccountInfoHolder;
import com.example.mycomputer.igotafriend.validetor.VisterValider.VisterValider;
import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.function.Consumer;

public class SearchLocalActivity extends AppCompatActivity implements Consumer<Boolean> {
    public static String TAG = "SearchPersonlActivty";
    public static String Tag = "vister";
    private Button btnSearch, btnCalender;
    private LinearLayout lineLeft;
    private LinearLayout lineRight;
    private TextView txSearchedAddress;
    private final int DATEPICK = 10;
    private ArrayList<Interests> listOfInterests;
    private CheckBox checkBox;
    private ArrayList<Date> dates;
    private ICheckBoxService iCheckBoxService = new CheckBoxService();
    private AccountInfo accountInfo;
    private FirebaseDatabase mFireBaseDataBase;//->reference the to the entrey pointto the database
    private DatabaseReference mNewDateViserArrival;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_local);
//        accountInfo = new AccountInfo();
        mFireBaseDataBase = FirebaseDatabase.getInstance();
        //  txSearchedAddress = (TextView) findViewById(R.id.txSearchedAddress);
        btnCalender = (Button) findViewById(R.id.btnCalender);
        btnSearch = (Button) findViewById(R.id.btnSubmit);

        lineRight = (LinearLayout) findViewById(R.id.lineRight);
        lineLeft = (LinearLayout) findViewById(R.id.lineLeft);
        iCheckBoxService.creatCheckBox(getApplicationContext(), lineRight, lineLeft);

       accountInfo= AccountInfoHolder.accountInfo;
            if (!accountInfo.getSearchPreferernce().getListOfInterests().isEmpty()) {
                iCheckBoxService.checkboxViewForVister(accountInfo.getSearchPreferernce().getListOfInterests(), lineRight, lineLeft);
            }


        PlaceAutocompleteFragment autocompleteFragment = (PlaceAutocompleteFragment)//
                getFragmentManager().findFragmentById(R.id.place_autocomplete_fragment);
        autocompleteFragment.setHint("City");

        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {

                accountInfo.getSearchPreferernce().setCity(place.getName().toString());
                Log.i(TAG, "Place: " + place.getName());
            }

            @Override
            public void onError(Status status) {
                // TODO: Handle the error.
                Log.i(TAG, "An error occurred: " + status);
            }
        });
        btnCalender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SearchLocalActivity.this, CalenderActivty.class);
                startActivityForResult(intent, DATEPICK);
            }
        });
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                accountInfo.getSearchPreferernce().clearInterset();
                iCheckBoxService.getAllInterstsCheck(lineRight,lineLeft,accountInfo//
                        .getSearchPreferernce().getListOfInterests());
                valider();
            }
        });
    }

    private void valider() {
        VisterValider visterValider=new VisterValider();
        //visterValider.validate(accountInfo.getSearchPreferernce());
        String res= visterValider.validate(accountInfo.getSearchPreferernce());
        if (res!=null){
            Toast.makeText(this, res.toString(), Toast.LENGTH_SHORT).show();
        }
        else {
            upadte();
        }

    }

    private void upadte() {
        IUpdateFireBase<AccountInfo> updater = new AccountInfoFirebaseUpdater();
        updater.update(accountInfo,this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == DATEPICK && resultCode == RESULT_OK) {
            String temp = data.getExtras().getString("dates");
            Log.d("DATEPICK", temp.toString());
            Gson gson = new Gson();
            Type type = new TypeToken<List<Date>>() {
            }.getType();
            dates = gson.fromJson(temp, type);
            int i = 0;
            accountInfo.getSearchPreferernce().setStartDate(DateFormat.formatDateToString(dates.get(i)));
            if(dates.size()>1) {
                accountInfo.getSearchPreferernce().setEndDate(DateFormat.formatDateToString(dates.get(i + 1)));
            }

        }
    }

    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.signout_menu, menu);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.signout:
                AuthUI.getInstance().signOut(this);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void accept(Boolean aBoolean) {
        if (aBoolean){
            Intent intent1=new Intent(this,SearchResultActivty.class);
            intent1.putExtra("key",accountInfo);
            startActivity(intent1);

        }

    }
}

