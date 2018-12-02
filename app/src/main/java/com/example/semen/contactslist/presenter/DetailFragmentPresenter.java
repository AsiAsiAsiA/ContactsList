package com.example.semen.contactslist.presenter;

import android.os.AsyncTask;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.example.semen.contactslist.app.App;
import com.example.semen.contactslist.model.Contact;
import com.example.semen.contactslist.service.ContactsManager;
import com.example.semen.contactslist.view.DetailFragmentView;

@InjectViewState
public class DetailFragmentPresenter extends MvpPresenter<DetailFragmentView> {
    private String id;
    private DetailAsyncTask detailAsyncTask;


    public void loadContact(String id) {
        this.id = id;
        detailAsyncTask = new DetailAsyncTask();
        detailAsyncTask.execute();
    }

    @Override
    public void onDestroy() {
        if (detailAsyncTask != null){
            detailAsyncTask.cancel(true);
            detailAsyncTask = null;
        }
        super.onDestroy();
    }

    class DetailAsyncTask extends AsyncTask<Void, Void, Contact> {
        @Override
        protected Contact doInBackground(Void... voids) {
            return ContactsManager.findContactById(id,App.getContext());
        }

        @Override
        protected void onPostExecute(Contact contact) {
            getViewState().loadContactFromContentProvider(contact);
        }
    }
}
