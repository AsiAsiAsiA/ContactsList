package com.example.semen.contactslist.ui.presenter;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.example.semen.contactslist.domain.Contact;
import com.example.semen.contactslist.repo.Repository;
import com.example.semen.contactslist.ui.view.DetailFragmentView;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.Nullable;
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
        disposable = repository.findContactById(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::loadContactFromContentProvider, this::errorHandle);
    }

    private void loadContactFromContentProvider(Contact contact) {
        getViewState().loadContactFromContentProvider(contact);
    }

    private void errorHandle(@Nullable Throwable throwable) {
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
