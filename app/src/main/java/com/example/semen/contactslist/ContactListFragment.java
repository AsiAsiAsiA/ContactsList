package com.example.semen.contactslist;


import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.example.semen.contactslist.adapter.ContactsAdapter;
import com.example.semen.contactslist.model.Contact;
import com.example.semen.contactslist.presenter.ContactsListFragmentPresenter;
import com.example.semen.contactslist.view.ContactListFragmentView;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Provider;

import dagger.android.support.AndroidSupportInjection;


/**
 * A simple {@link Fragment} subclass.
 */
public class ContactListFragment extends MvpAppCompatFragment implements ContactsAdapter.ItemClickListener, ContactListFragmentView {
    private TextView tvContactListFragmentTitle;
    private ContactsAdapter contactsAdapter;
    private List<Contact> contactsList;
    private RecyclerView recyclerView;

    @InjectPresenter
    ContactsListFragmentPresenter contactsListFragmentPresenter;

    @Inject
    Provider<ContactsListFragmentPresenter> presenterProvider;

    @ProvidePresenter
    ContactsListFragmentPresenter providePresenter() {
        return presenterProvider.get();
    }

    private static final int REQUEST_CODE_READ_CONTACTS = 1;

    public static ContactListFragment newInstance() {
        return new ContactListFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        AndroidSupportInjection.inject(this);
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_contact_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        contactsList = new ArrayList<>();
        initViews(view);

        //получаем разрешения
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
        tvContactListFragmentTitle = null;
        contactsAdapter = null;
        contactsList = null;
        recyclerView = null;
        super.onDestroyView();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_CODE_READ_CONTACTS) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                queryContentProvider();
            } else {
                contactsListFragmentPresenter.noPermissions();
            }
        }
    }

    private void initViews(@NonNull View view) {
        tvContactListFragmentTitle = view.findViewById(R.id.contactListFragment_title);
        recyclerView = view.findViewById(R.id.my_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        contactsAdapter = new ContactsAdapter(contactsList);
        contactsAdapter.setItemClickListener(this);
        recyclerView.setAdapter(contactsAdapter);
    }

    //Размещение фрагмента во фрейм
    private void loadFragment(Fragment fragment) {
        getFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .addToBackStack(null)
                .commit();
    }

    //запрос в ContentProvider в отдельном потоке
    private void queryContentProvider() {
        contactsListFragmentPresenter.getContactList();
    }

    @Override
    public void loadList(List<Contact> contacts) {
        if (contacts != null) {
            tvContactListFragmentTitle.setText(getString(R.string.contactListFragment_title));
            contactsAdapter.setContacts(contacts);
        } else {
            tvContactListFragmentTitle.setText(getString(R.string.data_is_not_available));
        }
    }

    @Override
    public void startLoading() {
        tvContactListFragmentTitle.setText(R.string.reading_from_database);
    }

    @Override
    public void finishLoading() {
        Toast.makeText(requireContext(), R.string.contact_list_updated, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showPermissionsNotGranted() {
        tvContactListFragmentTitle.setText(getString(R.string.data_is_not_available));
    }

    @Override
    public void onClick(String id) {
        loadFragment(DetailFragment.newInstance(id));
    }
}
