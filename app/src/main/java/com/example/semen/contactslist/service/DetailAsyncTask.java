package com.example.semen.contactslist.service;

import android.content.Context;
import android.os.AsyncTask;

import com.example.semen.contactslist.model.Contact;

public class DetailAsyncTask extends AsyncTask<Context, Void, Contact> {
    private String id;

    public DetailAsyncTask(String id) {
        this.id = id;
    }

    @Override
    protected Contact doInBackground(Context... contexts) {
        return ContactsContentResolver.findContactById(id,contexts[0]);
    }
}
