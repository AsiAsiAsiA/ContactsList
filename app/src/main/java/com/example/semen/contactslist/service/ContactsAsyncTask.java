package com.example.semen.contactslist.service;

import android.content.Context;
import android.os.AsyncTask;

import com.example.semen.contactslist.model.Contact;

import java.lang.ref.WeakReference;
import java.util.List;

public class ContactsAsyncTask extends AsyncTask<Context, Void, List<Contact>> {
    private final WeakReference<AsyncResponseContactList> delegate;

    public ContactsAsyncTask(AsyncResponseContactList asyncResponseContactList) {
        delegate = new WeakReference<>(asyncResponseContactList);
    }

    @Override
    protected List<Contact> doInBackground(Context... contexts) {
        return ContactsManager.getContacts(contexts[0]);
    }

    @Override
    protected void onPostExecute(List<Contact> contacts) {
        super.onPostExecute(contacts);
        AsyncResponseContactList callback = delegate.get();
        if (callback != null) {
            callback.loadList(contacts);
        }
    }
}
