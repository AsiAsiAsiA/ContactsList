package com.example.semen.contactslist.service;

import android.content.Context;
import android.os.AsyncTask;

import com.example.semen.contactslist.model.Contact;

import java.lang.ref.WeakReference;

public class DetailAsyncTask extends AsyncTask<Context, Void, Contact> {
    private final WeakReference<AsyncResponseDetail> delegate;
    private final String id;

    public DetailAsyncTask(String id, AsyncResponseDetail asyncResponseDetail) {
        this.id = id;
        delegate = new WeakReference<>(asyncResponseDetail);
    }

    @Override
    protected Contact doInBackground(Context... contexts) {
        return ContactsManager.findContactById(id,contexts[0]);
    }

    @Override
    protected void onPostExecute(Contact contact) {
        super.onPostExecute(contact);
        AsyncResponseDetail callback = delegate.get();
        if (callback != null){
            callback.loadContactFromContentProvider(contact);
        }
    }
}
