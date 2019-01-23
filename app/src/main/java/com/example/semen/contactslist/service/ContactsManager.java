package com.example.semen.contactslist.service;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.provider.ContactsContract;
import android.util.Log;

import com.example.semen.contactslist.model.Contact;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Single;

public class ContactsManager {
    private static final String TAG = "ContactsManager";

    @Inject
    Context context;

    //Получение списка контактов из ContentProvider
    public Single<List<Contact>> getContacts() {
        return Single.fromCallable(() -> {
            List<Contact> contactArrayList = new ArrayList<>();

            ContentResolver contentResolver = context.getContentResolver();

            try (Cursor cursor = contentResolver.query(ContactsContract.Contacts.CONTENT_URI,
                    null,
                    null,
                    null,
                    null)) {
                if (cursor != null) {
                    while (cursor.moveToNext()) {
                        String id = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
                        String contactName = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME_PRIMARY));

                        List<String> phoneNumbers = getListPhoneNumbers(contentResolver, id);

                        Contact contact = new Contact(id, contactName, phoneNumbers);
                        contactArrayList.add(contact);
                        Log.i(TAG, contact.toString());
                    }
                }
            }
            return contactArrayList;
        });
    }

    //Получение контакта по ID
    public Single<Contact> findContactById(String id) {
        return Single.fromCallable(() -> {
            Contact contact = null;
            ContentResolver contentResolver = context.getContentResolver();
            try (Cursor cursor = contentResolver.query(ContactsContract.Contacts.CONTENT_URI,
                    new String[]{ContactsContract.Contacts._ID, ContactsContract.Contacts.DISPLAY_NAME_PRIMARY},
                    ContactsContract.Contacts._ID + " = ?",
                    new String[]{id},
                    null)) {
                if (cursor != null) {
                    while (cursor.moveToNext()) {
                        String contactName = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME_PRIMARY));

                        List<String> phoneNumbers = getListPhoneNumbers(contentResolver, id);

                        contact = new Contact(id, contactName, phoneNumbers);
                        Log.i(TAG, contact.toString());
                    }
                }
            }
            return contact;
        });
    }

    //Получение всех номеров одного контакта
    private static List<String> getListPhoneNumbers(ContentResolver contentResolver, String id) {
        List<String> listPhoneNumbers = new ArrayList<>();

        try (Cursor phoneCursor = contentResolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                null,
                ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?",
                new String[]{id},
                null)) {
            if (phoneCursor != null) {
                while (phoneCursor.moveToNext()) {
                    String phoneNumber = phoneCursor.getString(
                            phoneCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                    listPhoneNumbers.add(phoneNumber);
                }
            }
        }
        return listPhoneNumbers;
    }
}
