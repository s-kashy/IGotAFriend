package com.example.mycomputer.igotafriend.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.mycomputer.igotafriend.AccountInfo;
import com.example.mycomputer.igotafriend.Interests;
import com.example.mycomputer.igotafriend.R;
import com.example.mycomputer.igotafriend.SearchResultAdpter;
import com.example.mycomputer.igotafriend.validetor.AccountInfoHolder;
import com.firebase.ui.auth.AuthUI;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class SearchResultActivty extends AppCompatActivity {
    private ListView listView;
    private SearchResultAdpter searchResultAdpter;
    private ArrayList<AccountInfo> accountInfos;
    private ChildEventListener childEventListener;
    AccountInfo accountVister;
    boolean checkForSort =true;
    AccountInfo acountLocal;
    private FirebaseDatabase mFireBaseDataBase;//->reference the to the entrey point to the database
    private DatabaseReference mMessageDataBaseRefernce;//->refe

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);
        final Intent intent = getIntent();
        if (intent.getExtras() != null) {

            accountVister = AccountInfoHolder.accountInfo;
        }
        mFireBaseDataBase = FirebaseDatabase.getInstance();
        mMessageDataBaseRefernce = mFireBaseDataBase.getReference().child("Users");

        listView = (ListView) findViewById(R.id.lvResultSerach);
        accountInfos = new ArrayList<>();
        searchResultAdpter = new SearchResultAdpter(this, 0, accountInfos);
        listView.setAdapter(searchResultAdpter);
        Query query = mMessageDataBaseRefernce.orderByChild("originCity").equalTo(accountVister.getSearchPreferernce().getCity());
        query.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                AccountInfo account = dataSnapshot.getValue(AccountInfo.class);
                if (!account.getUid().equals(accountVister.getUid())) {
                    accountInfos.add(account);
                    searchResultAdpter.notifyDataSetChanged();
                }
            }


            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {


            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                acountLocal = searchResultAdpter.getItem(position);
                Intent intent1 = new Intent(SearchResultActivty.this, ShowLocalProfileActivty.class);
                intent1.putExtra("new", "viewByVister");
                intent1.putExtra("key", acountLocal);

                startActivity(intent1);

            }
        });

    }

    private boolean equalIntrest(List<Interests> interestsOfLocal, List<Interests> interestsOfVister) {
        int count = 0;
        for (int i = 0; i < interestsOfVister.size(); i++) {
            if (interestsOfVister.get(i).equals(interestsOfLocal.get(i))) {
                count++;
                if (count > 2) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.sort_search, menu);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.signout:
                AuthUI.getInstance().signOut(this);
                return true;
            case R.id.sort:
                if (checkForSort) {
                    Collections.sort(accountInfos, new Comparator<AccountInfo>() {
                        @Override
                        public int compare(AccountInfo accountInfo1, AccountInfo accountInfo2) {

                            return Float.compare(accountInfo1.getProfileLocol().getRate(), accountInfo2.getProfileLocol().getRate());
                        }
                    });
                    searchResultAdpter.notifyDataSetChanged();
                    checkForSort =false;
                    return true;
                }else {
                    Collections.sort(accountInfos, Collections.<AccountInfo>reverseOrder(
                    new Comparator<AccountInfo>() {
                        @Override
                        public int compare(AccountInfo accountInfo1, AccountInfo accountInfo2) {
                            return Float.compare(accountInfo1.getProfileLocol().getRate(), accountInfo2.getProfileLocol().getRate());
                        }
                    }));

                   searchResultAdpter.notifyDataSetChanged();
                   checkForSort =true;
                   return true;
                }
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}


