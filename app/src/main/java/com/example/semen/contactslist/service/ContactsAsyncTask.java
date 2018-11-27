package com.example.semen.contactslist.service;

import android.content.Context;
import android.os.AsyncTask;

import com.example.semen.contactslist.model.Contact;

import java.lang.ref.WeakReference;
import java.util.List;

public class ContactsAsyncTask extends AsyncTask<Context, Void, List<Contact>> {
    private WeakReference<AsyncResponseContactListFragment> delegate;

    public ContactsAsyncTask(AsyncResponseContactListFragment asyncResponseContactListFragment) {
        delegate = new WeakReference<>(asyncResponseContactListFragment);
    }

    @Override
    protected List<Contact> doInBackground(Context... contexts) {
        return ContactsContentResolver.getContacts(contexts[0]);
    }

    @Override
    protected void onPostExecute(List<Contact> contacts) {
        super.onPostExecute(contacts);
        delegate.get().loadList(contacts);
    }
}
