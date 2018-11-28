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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.semen.contactslist.adapter.ContactsAdapter;
import com.example.semen.contactslist.model.Contact;
import com.example.semen.contactslist.service.AsyncResponseContactList;
import com.example.semen.contactslist.service.ContactsAsyncTask;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class ContactListFragment extends Fragment implements AsyncResponseContactList, ContactsAdapter.ItemClickListener {
    private TextView tvContactListFragmentTitle;
    private ContactsAdapter contactsAdapter;
    private List<Contact> contactsList;

    private static final int REQUEST_CODE_READ_CONTACTS = 1;

    public static ContactListFragment newInstance() {
        return new ContactListFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
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
            Log.i("ContactListFragment", "RequestPermission");
            requestPermissions(new String[]{Manifest.permission.READ_CONTACTS}, REQUEST_CODE_READ_CONTACTS);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        Log.i("ContactListFragment", "onRequestPermissionsResult");
        if (requestCode == REQUEST_CODE_READ_CONTACTS) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                queryContentProvider();
            } else {
                tvContactListFragmentTitle.setText(getString(R.string.no_permission));
            }
        }
    }

    private void initViews(@NonNull View view) {
        tvContactListFragmentTitle = view.findViewById(R.id.contactListFragment_title);
        RecyclerView recyclerView = view.findViewById(R.id.my_recycler_view);
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
        ContactsAsyncTask contactsAsyncTask = new ContactsAsyncTask(this);
        contactsAsyncTask.execute(requireContext());
    }

    //Окончание запроса в AsyncTask
    @Override
    public void loadList(List<Contact> contacts) {
        tvContactListFragmentTitle.setText(getString(R.string.contactListFragment_title));
        contactsAdapter.setContacts(contacts);
    }

    @Override
    public void onClick(String id) {
        loadFragment(DetailFragment.newInstance(id));
    }
}
