package com.example.semen.contactslist.presenter;

import android.content.Context;
import android.os.AsyncTask;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.example.semen.contactslist.model.Contact;
import com.example.semen.contactslist.service.ContactsManager;
import com.example.semen.contactslist.view.ContactListView;

import java.util.List;

@InjectViewState
public class ContactsListPresenter extends MvpPresenter<ContactListView> {
    public void getContactList(Context context) {
        new ContactsAsyncTask().execute(context);
    }

    public void noPermissions(){
        getViewState().noPermissions();
    }

    public class ContactsAsyncTask extends AsyncTask<Context, Void, List<Contact>> {
        @Override
        protected void onPreExecute() {
            getViewState().startLoading();
        }

        @Override
        protected List<Contact> doInBackground(Context... contexts) {
            return ContactsManager.getContacts(contexts[0]);
        }

        @Override
        protected void onPostExecute(List<Contact> contacts) {
            getViewState().loadList(contacts);
        }
    }
}


