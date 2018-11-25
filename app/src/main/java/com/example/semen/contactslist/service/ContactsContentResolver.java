package com.example.semen.contactslist.service;

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
    //TODO:Хранение списка контактов static контексте программы - не оправдано с точки зрения юзабилити приложения, т.к. для того что бы перезагрузить список контактов придется перезапускать приложение.
    //TODO:во вторых для чего нам он как статик?
    private static List<Contact> contactArrayList;

    //Получение данных из ContentProvider
    public static List<Contact> getContacts(Context context) {
        contactArrayList = new ArrayList<>();

        ContentResolver contentResolver = context.getContentResolver();

        try(Cursor cursor = contentResolver.query(ContactsContract.Contacts.CONTENT_URI,
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
    }

    //Получение контакта по ID
    public static Contact findContactById(String id) {
        Contact contact = null;
        for (int i = 0; i < contactArrayList.size(); i++) {
            if (contactArrayList.get(i).getId().equals(id)) {
                contact = contactArrayList.get(i);
                break;
            }
        }
        return contact;
    }

    //Получение всех номеров контакта
    private static List<String> getListPhoneNumbers(ContentResolver contentResolver, String id) {
        List<String> listPhoneNumbers = new ArrayList<>();

        try(Cursor phoneCursor = contentResolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                null,
                ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?",
                new String[]{id},
                null)){
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
