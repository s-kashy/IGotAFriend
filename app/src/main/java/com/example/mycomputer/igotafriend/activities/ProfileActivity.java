package com.example.mycomputer.igotafriend.activities;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mycomputer.igotafriend.AccountInfo;
import com.example.mycomputer.igotafriend.AccountInfoFirebaseUpdater;
import com.example.mycomputer.igotafriend.Gender;
import com.example.mycomputer.igotafriend.IUpdateFireBase;
import com.example.mycomputer.igotafriend.R;
import com.example.mycomputer.igotafriend.validetor.AccountInfoHolder;
import com.example.mycomputer.igotafriend.validetor.AccountInfoValider.ValiderAccountInfo;
import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.util.function.Consumer;


public class ProfileActivity extends AppCompatActivity implements Consumer<Boolean> {
    private TextView txDisplayName ;
    private static final String Tag = "from edit chat";
    private Dialog dialog;
    private Uri uri;
    private Button btnTakePic, btnSubmit;
    private RadioGroup radioGroup;
    private de.hdodenhof.circleimageview.CircleImageView imageView;

    private Gender type;
    private EditText etAge, etFirstName;
    private FirebaseDatabase mFireBaseDataBase;//->reference the to the entrey point to the database
    private DatabaseReference mDataBaseRefernce;//->refe
    private IUpdateFireBase iUpdateFireBase;

    private static final int RESULT_LOAD_IMAGE = 100;
    private static final int REQUEST_IMAGE_CAPTURE = 110;
    private AccountInfo accountInfo;
    private String uid = "";
    private FirebaseStorage mFirebaseStorage;
    private StorageReference mPhotoStrogeReference;
    private boolean zoom = true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        iUpdateFireBase = new AccountInfoFirebaseUpdater();
        btnSubmit = (Button) findViewById(R.id.btnSubmit);
        radioGroup = (RadioGroup) findViewById(R.id.radioGroup);

        txDisplayName = (TextView) findViewById(R.id.txDisplay);



        etAge = (EditText) findViewById(R.id.etAge);
        btnTakePic = (Button) findViewById(R.id.btnTakeAPic);
        imageView = (de.hdodenhof.circleimageview.CircleImageView) findViewById(R.id.imageUser);
        PlaceAutocompleteFragment autocompleteFragment = (PlaceAutocompleteFragment)//
                getFragmentManager().findFragmentById(R.id.place_autocomplete_fragment);
        autocompleteFragment.setHint("City");


        final Intent intent = getIntent();
        if (intent.getExtras() != null) {

            if (Tag.equals(intent.getExtras().getString("edit"))) {

                accountInfo = AccountInfoHolder.accountInfo;
                etAge.setText(accountInfo.getAge().toString());

                txDisplayName.setText(accountInfo.getDispalyName());
                if (accountInfo.getPhotoUri()!=null) {
                    Picasso.with(this).load(accountInfo.getPhotoUri().toString()).into(imageView);
                }
                if (accountInfo.getType() == Gender.MALE) {
                    RadioButton radioButton = (RadioButton) findViewById(R.id.radoiMale);
                    radioButton.setChecked(true);
                } else {
                    RadioButton radioButton = (RadioButton) findViewById(R.id.radoiFemle);
                    radioButton.setChecked(true);
                }

            } else {
                uid = intent.getExtras().getString("key");
                String email = intent.getExtras().getString("email");
                String dispaly=intent.getExtras().getString("dispaly");
                if (uid != null) {
                    accountInfo=new AccountInfo();
                    accountInfo.setUid(uid);
                    accountInfo.setEmail(email);
                    accountInfo.setDispalyName(dispaly.toString());
                    txDisplayName.setText(dispaly.toString());
                    AccountInfoHolder.accountInfo=accountInfo;

                }
            }
        }



        mFireBaseDataBase = FirebaseDatabase.getInstance();
        mDataBaseRefernce = mFireBaseDataBase.getReference().child("Users");
        mFirebaseStorage = FirebaseStorage.getInstance();
        mPhotoStrogeReference = mFirebaseStorage.getReference().child("photo/" + accountInfo.getEmail());


        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.radoiMale) {
                    accountInfo.setType(Gender.MALE);
                } else if (checkedId == R.id.radoiFemle) {
                    accountInfo.setType(Gender.FEMALE);
                } else {
                    accountInfo.setType(Gender.UNDEFINE);
                }
            }
        });
        btnTakePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog = new Dialog(ProfileActivity.this);
                dialog.setContentView(R.layout.dailog_cam);
                dialog.setCancelable(false);

                Button btnStorge = (Button) dialog.findViewById(R.id.btnStorge);
                Button btnCamera = (Button) dialog.findViewById(R.id.btnCamera);
                Button btnCancel = (Button) dialog.findViewById(R.id.btnCancel);
                dialog.show();
                btnStorge.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);

                        intent.setType("image/jpeg");
                        startActivityForResult(intent, RESULT_LOAD_IMAGE);


                    }
                });
                btnCamera.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

                        startActivityForResult(intent, REQUEST_IMAGE_CAPTURE);

                    }

                });
                btnCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });


            }

        });
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v1) {
                Dialog settingsDialog = new Dialog(ProfileActivity.this);

                View v = ProfileActivity.this.getLayoutInflater().inflate(R.layout.imagezoom, null);
                ImageView iv = (ImageView) v.findViewById(R.id.imageZoom);
                Bitmap bm = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
                iv.setImageBitmap(bm);
                settingsDialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
                settingsDialog.setContentView(v);
                settingsDialog.show();


            }
        });

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                valider();
            }
        });
        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {
                accountInfo.setOriginCity(place.getName().toString());

            }

            @Override
            public void onError(Status status) {

            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {
            uri = data.getData();
            Picasso.with(this).load(uri.toString()).into(imageView);

            mPhotoStrogeReference.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Uri uri=taskSnapshot.getDownloadUrl();
                    accountInfo.setPhotoUri(uri.toString());

                }
            });
            dialog.dismiss();


        } else if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap bitmap = (Bitmap) data.getExtras().get("data");
            imageView.setImageBitmap(bitmap);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
            byte[] data1 = baos.toByteArray();
            UploadTask uploadTask = mPhotoStrogeReference.putBytes(data1);
            uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Uri uri = taskSnapshot.getDownloadUrl();
                    accountInfo.setPhotoUri(uri.toString());
                }
            });
        }

        dialog.dismiss();

    }


    private void valider() {
        ValiderAccountInfo iAccountInfoValidter = new ValiderAccountInfo();
        accountInfo.setAge(etAge.getText().toString());


        String res = iAccountInfoValidter.validate(accountInfo);
        if (res != null) {

            Toast.makeText(this, res.toString(), Toast.LENGTH_SHORT).show();
            return;
        } else {
            AccountInfoHolder.accountInfo=accountInfo;
            IUpdateFireBase<AccountInfo> updateFireBase = new AccountInfoFirebaseUpdater();
            updateFireBase.update(accountInfo, ProfileActivity.this);
        }
    }


    @Override
    public void accept(Boolean aBoolean) {
        if (aBoolean) {
            Intent intent1 = new Intent(ProfileActivity.this, ChatActivity.class);

            startActivity(intent1);
            finish();
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
}
