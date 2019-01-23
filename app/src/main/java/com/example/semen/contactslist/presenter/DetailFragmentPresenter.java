package com.example.semen.contactslist.presenter;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.example.semen.contactslist.model.Contact;
import com.example.semen.contactslist.repo.Repository;
import com.example.semen.contactslist.view.DetailFragmentView;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

@InjectViewState
public class DetailFragmentPresenter extends MvpPresenter<DetailFragmentView> {
    private Disposable disposable;
    private Repository repository;

    @Inject
    public DetailFragmentPresenter(Repository repository) {
        this.repository = repository;
    }

    public void loadContact(String id) {
        if (disposable != null && !disposable.isDisposed()) {
            disposable.dispose();
        }
        disposable = repository.findContactById(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::loadContactFromContentProvider);
    }

    private void loadContactFromContentProvider(Contact contact) {
        getViewState().loadContactFromContentProvider(contact);
    }
}
