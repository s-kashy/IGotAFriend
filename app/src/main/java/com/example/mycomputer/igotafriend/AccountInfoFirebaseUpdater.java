package com.example.mycomputer.igotafriend;

import android.os.Build;
import android.support.annotation.RequiresApi;

import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.function.Consumer;

/**
 * Created by My computer on 1/21/2018.
 */

public class AccountInfoFirebaseUpdater implements IUpdateFireBase<AccountInfo> {
    @Override
    public void update(AccountInfo accountInfo, final Consumer<Boolean> callback) {
        FirebaseDatabase mFireBaseDataBase;//->reference the to the entrey pointto the database
        DatabaseReference mNewLocalDataReference;
        mFireBaseDataBase = FirebaseDatabase.getInstance();
        mNewLocalDataReference = mFireBaseDataBase.getReference().child("Users").child(accountInfo.getUid());
        /// mNewLocalDataReference.removeValue();
        mNewLocalDataReference.setValue(accountInfo, new DatabaseReference.CompletionListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                    if (callback!=null) {
                        callback.accept(databaseError == null);
                    }

            }

        });


    }

}
