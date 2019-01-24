package com.example.semen.contactslist.presentation;


import android.Manifest;
import android.content.Context;
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
import android.widget.Toast;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.example.semen.contactslist.R;
import com.example.semen.contactslist.domain.model.Contact;
import com.example.semen.contactslist.presentation.presenter.DetailFragmentPresenter;
import com.example.semen.contactslist.presentation.view.DetailFragmentView;

import javax.inject.Inject;
import javax.inject.Provider;

import dagger.android.support.AndroidSupportInjection;


/**
 * A simple {@link Fragment} subclass.
 */
public class DetailFragment extends MvpAppCompatFragment implements DetailFragmentView {
    private TextView tvName;
    private TextView tvPhoneNumber;
    private String contactId;
    private static final int REQUEST_CODE_READ_CONTACTS = 1;
    private static final String _ID = "_id";
    private static final String EMPTY = "Empty";

    @InjectPresenter
    DetailFragmentPresenter detailFragmentPresenter;

    @Inject
    Provider<DetailFragmentPresenter> presenterProvider;

    @ProvidePresenter
    DetailFragmentPresenter providePresenter() {
        return presenterProvider.get();
    }

    public static DetailFragment newInstance(String id) {
        Bundle args = new Bundle();

        DetailFragment fragment = new DetailFragment();
        args.putString(_ID, id);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        AndroidSupportInjection.inject(this);
        super.onAttach(context);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
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

    //запрос в ContentProvider в отдельном потоке
    private void queryContentProvider() {
        detailFragmentPresenter.loadContact(contactId);
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

    @Override
    public void showThrowableMessage(String message) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_LONG).show();
    }
}
