package com.example.semen.contactslist.presenter;

import android.content.Context;
import android.os.AsyncTask;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.example.semen.contactslist.model.Contact;
import com.example.semen.contactslist.service.ContactsManager;
import com.example.semen.contactslist.view.DetailView;

@InjectViewState
public class DetailPresenter extends MvpPresenter<DetailView> {
    private String id;

    public void loadContact(String id, Context context) {
        this.id = id;
        new DetailAsyncTask().execute(context);
    }

    public class DetailAsyncTask extends AsyncTask<Context, Void, Contact> {
        @Override
        protected Contact doInBackground(Context... contexts) {
            return ContactsManager.findContactById(id, contexts[0]);
        }

        @Override
        protected void onPostExecute(Contact contact) {
            getViewState().loadContactFromContentProvider(contact);
        }
    }
}
