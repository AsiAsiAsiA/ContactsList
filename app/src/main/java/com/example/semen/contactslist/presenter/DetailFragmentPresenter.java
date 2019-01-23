package com.example.semen.contactslist.presenter;

import android.os.AsyncTask;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.example.semen.contactslist.model.Contact;
import com.example.semen.contactslist.service.ContactsManager;
import com.example.semen.contactslist.view.DetailFragmentView;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

@InjectViewState
public class DetailFragmentPresenter extends MvpPresenter<DetailFragmentView> {
    private Disposable disposable;

    public void loadContact(String id) {
        if (disposable != null && !disposable.isDisposed()) {
            disposable.dispose();
        }
        disposable = ContactsManager.findContactById(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::loadContactFromContentProvider);
    }

    public void loadContactFromContentProvider(Contact contact){
        getViewState().loadContactFromContentProvider(contact);
    }
}
