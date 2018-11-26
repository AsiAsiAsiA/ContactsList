package com.example.semen.contactslist.service;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.example.semen.contactslist.AsyncResponse;
import com.example.semen.contactslist.model.Contact;

import java.util.List;

public class ContactsAsyncTask extends AsyncTask<Context, Void, List<Contact>> {
    public AsyncResponse delegate = null;

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        Log.i("TAG","Старт AsyncTask");
    }

    @Override
    protected List<Contact> doInBackground(Context... contexts) {
        return ContactsContentResolver.getContacts(contexts[0]);
    }

    @Override
    protected void onPostExecute(List<Contact> contacts) {
        super.onPostExecute(contacts);
        delegate.processFinish();
    }
}
