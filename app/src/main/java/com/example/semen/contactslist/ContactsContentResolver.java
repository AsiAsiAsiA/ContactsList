package com.example.semen.contactslist;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.provider.ContactsContract;
import android.util.Log;

import com.example.semen.contactslist.model.Contact;

import java.util.ArrayList;
import java.util.List;

public class ContactsContentResolver {
    private static final String TAG = "ContactsContentResolver";
    private static  ArrayList<Contact> contactArrayList;

    //Получение данных из ContentProvider
    public static List<Contact> getContacts(Context context) {
        contactArrayList = new ArrayList<>();

        ContentResolver contentResolver = context.getContentResolver();
        Cursor cursor = contentResolver.query(ContactsContract.Contacts.CONTENT_URI,
                null,
                null,
                null,
                null);

        if (cursor != null) {
            while (cursor.moveToNext()) {
                String id = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
                String contactName = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME_PRIMARY));

                Cursor phoneCursor = contentResolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                        null,
                        ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?",
                        new String[]{id},
                        null);

                ArrayList<String> phoneNumbers = new ArrayList<>();

                while (phoneCursor.moveToNext()) {
                    String phoneNumber = phoneCursor.getString(
                            phoneCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                    phoneNumbers.add(phoneNumber);
                }

                phoneCursor.close();

                Contact contact = new Contact(id, contactName, phoneNumbers);
                contactArrayList.add(contact);
                Log.i(TAG, contact.toString());
            }
        }
        cursor.close();
        return contactArrayList;
    }

    public static Contact findContactById(String id){
        Contact contact = null;
        for (int i = 0; i <contactArrayList.size() ; i++) {
            if (contactArrayList.get(i).getId()==id){
                contact = contactArrayList.get(i);
                break;
            }
        }
        return contact;
    }
}
