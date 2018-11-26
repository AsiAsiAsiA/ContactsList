package com.example.semen.contactslist;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.semen.contactslist.adapter.ContactsAdapter;
import com.example.semen.contactslist.model.Contact;
import com.example.semen.contactslist.service.ContactsAsyncTask;

import java.util.List;
import java.util.concurrent.ExecutionException;


/**
 * A simple {@link Fragment} subclass.
 */
public class ContactListFragment extends Fragment implements AsyncResponse, ContactsAdapter.ItemClickListener {
    TextView tvContactListFragmentTitle;
    RecyclerView recyclerView;
    List<Contact> contactArrayList;

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


        //TODO:Так же не спрашивается разрешение на чтение контактов.
        ContactsAsyncTask contactsAsyncTask = new ContactsAsyncTask();
        contactsAsyncTask.delegate = this;
        contactsAsyncTask.execute(requireContext());
        try {
            contactArrayList = contactsAsyncTask.get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }

        tvContactListFragmentTitle = view.findViewById(R.id.contactListFragment_title);
        Log.i("TAG", tvContactListFragmentTitle.getText().toString());

        recyclerView = view.findViewById(R.id.my_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        ContactsAdapter contactsAdapter = new ContactsAdapter(requireContext(), contactArrayList);
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

    //Окончание запроса в AsyncTask
    @Override
    public void processFinish() {
        tvContactListFragmentTitle.setText(requireContext().getString(R.string.contactListFragment_title));
        Log.i("TAG", "Конец AsyncTask");
    }

    @Override
    public void onClick(View view, String id) {
        loadFragment(DetailFragment.newInstance(id));
    }
}
