package com.example.semen.contactslist.ui.presenter;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.example.semen.contactslist.domain.Contact;
import com.example.semen.contactslist.repo.Repository;
import com.example.semen.contactslist.ui.view.ContactListFragmentView;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.Nullable;
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

    public void getContactList(@Nullable String name) {
        disposable = repository.getContacts(name)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(d -> getViewState().startLoading())
                .doAfterTerminate(() -> getViewState().finishLoading())
                .subscribe(this::loadList, this::errorHandle);
    }

    public void noPermissions() {
        getViewState().showPermissionsNotGranted();
    }

    private void loadList(@Nullable List<Contact> contacts) {
        getViewState().loadList(contacts);
    }

    private void errorHandle(Throwable throwable) {
        getViewState().showThrowableMessage(throwable.getMessage());
    }

    @Override
    public void onDestroy() {
        if (disposable != null && !disposable.isDisposed()) {
            disposable.dispose();
        }
        super.onDestroy();
    }
}

