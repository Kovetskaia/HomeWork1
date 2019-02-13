package com.example.nastya.homework1;

import android.app.IntentService;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.provider.ContactsContract;
import android.support.v4.content.LocalBroadcastManager;

import java.util.ArrayList;

public class MyService extends IntentService {

    ArrayList<String> contacts = new ArrayList<>();

    public MyService() {
        super("MyService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        //вызов метода для получения списка контактов
        loadContacts();
        intent.setAction("any_action");
        intent.putExtra("1", contacts);
        //отправка широковещательного сообщения
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
    }

    private void loadContacts() {
        ContentResolver contentResolver = getContentResolver();
        Cursor cursor = contentResolver.query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null);
        if (cursor != null) {
            while (cursor.moveToNext()) {
                String contact = cursor.getString(
                        cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME_PRIMARY));
                contacts.add(contact);
            }
            cursor.close();
        }


    }
}
