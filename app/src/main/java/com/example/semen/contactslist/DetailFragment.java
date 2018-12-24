package com.example.semen.contactslist;


import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.example.semen.contactslist.model.Contact;
import com.example.semen.contactslist.presenter.DetailFragmentPresenter;
import com.example.semen.contactslist.view.DetailFragmentView;
import com.example.semen.contactslist.service.ContactsManager;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;


/**
 * A simple {@link Fragment} subclass.
 */
public class DetailFragment extends MvpAppCompatFragment implements DetailFragmentView {
    private TextView tvName;
    private TextView tvPhoneNumber;
    private String contactId;
    private Disposable disposableDetailFragment;
    private static final int REQUEST_CODE_READ_CONTACTS = 1;
    private static final String _ID = "_id";
    private static final String EMPTY = "Empty";

    @InjectPresenter
    DetailFragmentPresenter detailFragmentPresenter;

    public static DetailFragment newInstance(String id) {
        Bundle args = new Bundle();
        DetailFragment fragment = new DetailFragment();
        args.putString(_ID, id);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_detail, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Bundle bundle = this.getArguments();
        contactId = bundle.getString(_ID, EMPTY);

        tvName = view.findViewById(R.id.tvName);
        tvPhoneNumber = view.findViewById(R.id.tvPhoneNumber);

        int hasReadContactPermission = ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.READ_CONTACTS);
        if (hasReadContactPermission == PackageManager.PERMISSION_GRANTED) {
            queryContentProvider();
        } else {
            // вызываем диалоговое окно для установки разрешений
            requestPermissions(new String[]{Manifest.permission.READ_CONTACTS}, REQUEST_CODE_READ_CONTACTS);
        }
    }

    @Override
    public void onDestroyView() {
        tvName = null;
        tvPhoneNumber = null;
        super.onDestroyView();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_CODE_READ_CONTACTS) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                queryContentProvider();
            } else {
                showPermissionsNotGranted();
            }
        }
    }

    @Override
    public void onDestroyView() {
        tvName = null;
        tvPhoneNumber = null;
        contactId = null;
        if (disposableDetailFragment != null) {
            disposableDetailFragment.dispose();
        }
        super.onDestroyView();
    }

    //запрос в ContentProvider в отдельном потоке
    private void queryContentProvider() {
        if (disposableDetailFragment != null && !disposableDetailFragment.isDisposed()) {
            disposableDetailFragment.dispose();
        }
        disposableDetailFragment = ContactsManager.findContactById(contactId, requireContext())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::loadContactFromContentProvider);
    }

    @Override
    public void loadContactFromContentProvider(Contact contact) {
        if (contact != null) {
            tvName.setText(getString(R.string.detailFragment_text,
                    getString(R.string.id),
                    contact.getName()));
            tvPhoneNumber.setText(getString(R.string.detailFragment_text,
                    getString(R.string.phone_number),
                    contact.getPhoneNumbers().toString()));
        } else {
            tvName.setText(getString(R.string.data_is_not_available));
            tvPhoneNumber.setText(getString(R.string.data_is_not_available));
        }
    }

    @Override
    public void showPermissionsNotGranted() {
        tvName.setText(getString(R.string.data_is_not_available));
        tvPhoneNumber.setText(getString(R.string.data_is_not_available));
    }
}
