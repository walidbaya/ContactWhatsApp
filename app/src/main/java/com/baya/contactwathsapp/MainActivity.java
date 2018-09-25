package com.baya.contactwathsapp;

import android.Manifest;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

//    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;

    ContactAdapter contactAdapter;
    List<String> myWhatsappContacts = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //ButterKnife.bind(this);
        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        contactAdapter = new ContactAdapter(myWhatsappContacts);
        recyclerView.setAdapter(contactAdapter);

        getPermission();
    }

    private void showContacts() {
        Cursor cursor = getContentResolver().query(ContactsContract.Data.CONTENT_URI
                ,new String[] {ContactsContract.Data._ID
                        ,ContactsContract.Data.DISPLAY_NAME
                        ,ContactsContract.CommonDataKinds.Phone.NUMBER
                        ,ContactsContract.CommonDataKinds.Phone.TYPE}
                ,ContactsContract.Data.MIMETYPE + "='" + ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE
                        + "' AND " + ContactsContract.RawContacts.ACCOUNT_TYPE + "= ?"
                ,new String[] { "com.whatsapp" }
                , null);


        if (cursor != null){
            while (cursor.moveToNext())
            {
                myWhatsappContacts.add(cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME)) + "\n" + cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)));
            }
        }
        if (myWhatsappContacts.size() > 0){
            contactAdapter.setContactList(myWhatsappContacts);
            recyclerView.setAdapter(contactAdapter);
            for (int i = 0; i < myWhatsappContacts.size(); i++){
                Log.w("contact", myWhatsappContacts.get(i));
            }
        }
    }

    private void getPermission(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && checkSelfPermission(Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.READ_CONTACTS}, 12);
        } else {
            showContacts();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 12) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                showContacts();
            } else {
                Toast.makeText(this, "Until you grant the permission, we canot display the names", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
