package com.example.semen.contactslist.ui;


import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.example.semen.contactslist.R;
import com.example.semen.contactslist.domain.Contact;
import com.example.semen.contactslist.ui.adapter.ContactListItemDecorator;
import com.example.semen.contactslist.ui.adapter.ContactsAdapter;
import com.example.semen.contactslist.ui.presenter.ContactsListFragmentPresenter;
import com.example.semen.contactslist.ui.view.ContactListFragmentView;

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
    private static final int REQUEST_CODE_READ_CONTACTS = 1;
    private static final String SEARCH_KEY = "search_key";
    @InjectPresenter
    ContactsListFragmentPresenter presenter;
    @Inject
    ContactListItemDecorator contactListItemDecorator;
    private SearchView searchView;

    @Inject
    Provider<ContactsListFragmentPresenter> presenterProvider;
    private String searchString;

    @ProvidePresenter
    ContactsListFragmentPresenter providePresenter() {
        return presenterProvider.get();
    }

    public static ContactListFragment newInstance() {
        return new ContactListFragment();
    }

    @Override
    public void onAttach(Context context) {
        AndroidSupportInjection.inject(this);
        super.onAttach(context);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_contact_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setHasOptionsMenu(true);

        if (savedInstanceState != null) {
            searchString = savedInstanceState.getString(SEARCH_KEY);
        }

        contactsList = new ArrayList<>();
        initViews(view);

        //получаем разрешения
        int hasReadContactPermission = ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.READ_CONTACTS);
        if (hasReadContactPermission == PackageManager.PERMISSION_GRANTED) {
            presenter.getContactList(searchString);
        } else {
            // вызываем диалоговое окно для установки разрешений
            requestPermissions(new String[]{Manifest.permission.READ_CONTACTS}, REQUEST_CODE_READ_CONTACTS);
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_search, menu);

        MenuItem searchItem = menu.findItem(R.id.menuSearch);
        searchView = (SearchView) searchItem.getActionView();

        if (searchString != null && !searchString.isEmpty()) {
            searchView.setIconified(false);
            searchView.setQuery(searchString, true);
            searchView.clearFocus();
        }

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            //реагирует на отправление текста
            @Override
            public boolean onQueryTextSubmit(String query) {
                Log.i("onQueryTextSubmit", query);
                presenter.getContactList(query);
                return true;
            }

            //реагирует на изменение текста(на каждую букву)
            @Override
            public boolean onQueryTextChange(String query) {
                Log.i("onQueryTextChange", query);
                presenter.getContactList(query);
                return true;
            }
        });
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
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(SEARCH_KEY, searchView.getQuery().toString());
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_CODE_READ_CONTACTS) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                presenter.getContactList(searchString);
            } else {
                presenter.noPermissions();
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
        recyclerView.addItemDecoration(contactListItemDecorator);
    }

    //Размещение фрагмента во фрейм
    private void loadFragment(Fragment fragment) {
        getFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void loadList(@Nullable List<Contact> contacts) {
        if (contacts != null) {
            tvContactListFragmentTitle.setText(getString(R.string.contactListFragment_title));
            contactsAdapter.updateContacts(contacts);
        } else {
            tvContactListFragmentTitle.setText(getString(R.string.no_permission));
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
        tvContactListFragmentTitle.setText(getString(R.string.no_permission));
    }

    @Override
    public void showThrowableMessage(String message) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onClick(String id) {
        loadFragment(DetailFragment.newInstance(id));
    }
}
