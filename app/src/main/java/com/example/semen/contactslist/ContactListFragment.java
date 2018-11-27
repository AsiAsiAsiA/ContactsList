package com.example.semen.contactslist;


import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
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
import com.example.semen.contactslist.service.AsyncResponseContactListFragment;
import com.example.semen.contactslist.service.ContactsAsyncTask;

import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class ContactListFragment extends Fragment implements AsyncResponseContactListFragment, ContactsAdapter.ItemClickListener {
    TextView tvContactListFragmentTitle;
    RecyclerView recyclerView;

    private static final int REQUEST_CODE_READ_CONTACTS = 1;
    private static boolean READ_CONTACTS_GRANTED = false;

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

        recyclerView = view.findViewById(R.id.my_recycler_view);
        tvContactListFragmentTitle = view.findViewById(R.id.contactListFragment_title);
        // получаем разрешения
        int hasReadContactPermission = ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.READ_CONTACTS);
        // если устройство до API 23, устанавливаем разрешение
        if (hasReadContactPermission == PackageManager.PERMISSION_GRANTED) {
            READ_CONTACTS_GRANTED = true;
        } else {
            // вызываем диалоговое окно для установки разрешений
            ActivityCompat.requestPermissions(requireActivity(),
                    new String[]{Manifest.permission.READ_CONTACTS},
                    REQUEST_CODE_READ_CONTACTS);
        }
        // если разрешение установлено, загружаем контакты
        if (READ_CONTACTS_GRANTED) {
            ContactsAsyncTask contactsAsyncTask = new ContactsAsyncTask(this);
            contactsAsyncTask.execute(requireContext());
        } else {
            tvContactListFragmentTitle.setText(requireContext().getString(R.string.no_permission));
        }
    }

    //Размещение фрагмента во фрейм
    private void loadFragment(Fragment fragment) {
        getFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .addToBackStack(null)
                .commit();
    }

    //Окончание запроса в AsyncTask
    @Override
    public void loadList(List<Contact> contacts) {
        tvContactListFragmentTitle.setText(requireContext().getString(R.string.contactListFragment_title));
        Log.i("TAG", "Конец AsyncTask");

        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        ContactsAdapter contactsAdapter = new ContactsAdapter(requireContext(), contacts);
        contactsAdapter.setItemClickListener(this);
        recyclerView.setAdapter(contactsAdapter);
    }

    @Override
    public void onClick(View view, String id) {
        loadFragment(DetailFragment.newInstance(id));
    }
}
