package com.example.semen.contactslist.presenter;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.example.semen.contactslist.model.Contact;
import com.example.semen.contactslist.repo.Repository;
import com.example.semen.contactslist.view.ContactListFragmentView;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

@InjectViewState
public class ContactsListFragmentPresenter extends MvpPresenter<ContactListFragmentView> {
    private Disposable disposable;
    private Repository repository;

    @Inject
    public ContactsListFragmentPresenter(Repository repository) {
        this.repository = repository;
    }

    public void getContactList() {
        if (disposable != null && !disposable.isDisposed()) {
            disposable.dispose();
        }
        disposable = repository.getContacts()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(__ -> getViewState().startLoading())
                .doAfterTerminate(() -> getViewState().finishLoading())
                .subscribe(this::loadList);
    }

    public void noPermissions() {
        getViewState().showPermissionsNotGranted();
    }

    private void loadList(List<Contact> contacts) {
        getViewState().loadList(contacts);
    }
}

