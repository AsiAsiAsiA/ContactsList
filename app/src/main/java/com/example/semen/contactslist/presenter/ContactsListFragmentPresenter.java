package com.example.semen.contactslist.presenter;

import android.os.AsyncTask;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.example.semen.contactslist.model.Contact;
import com.example.semen.contactslist.service.ContactsManager;
import com.example.semen.contactslist.view.ContactListFragmentView;

import java.util.List;

@InjectViewState
public class ContactsListFragmentPresenter extends MvpPresenter<ContactListFragmentView> {
    private ContactsAsyncTask contactsAsyncTask;

    public void getContactList() {
        contactsAsyncTask = new ContactsAsyncTask();
        contactsAsyncTask.execute();
    }

    @Override
    public void onDestroy() {
        if (contactsAsyncTask != null) {
            contactsAsyncTask.cancel(true);
            contactsAsyncTask = null;
        }
        super.onDestroy();
    }

    public void noPermissions() {
        getViewState().showPermissionsNotGranted();
    }

    class ContactsAsyncTask extends AsyncTask<Void, Void, List<Contact>> {
        @Override
        protected void onPreExecute() {
            if (!isCancelled()) {
                getViewState().startLoading();
            }
        }

        @Override
        protected List<Contact> doInBackground(Void... voids) {
            if (!isCancelled()) {
                return ContactsManager.getContacts();
            }
            return null;
        }

        @Override
        protected void onPostExecute(List<Contact> contacts) {
            getViewState().loadList(contacts);
            getViewState().finishLoading();
        }
    }
}


