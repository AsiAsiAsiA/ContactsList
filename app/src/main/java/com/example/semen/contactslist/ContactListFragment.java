package com.example.semen.contactslist;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.semen.contactslist.adapter.ContactsAdapter;
import com.example.semen.contactslist.model.Contact;

import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class ContactListFragment extends Fragment {
    private final String TAG = this.getClass().getSimpleName();

    RecyclerView recyclerView;
    List<Contact> contactArrayList = null;

    public ContactListFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_contact_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        contactArrayList = ContactsContentResolver.getContacts(getActivity());

        recyclerView = view.findViewById(R.id.my_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        recyclerView.setAdapter(new ContactsAdapter(contactArrayList, new ContactsAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Contact item) {
                DetailFragment detailFragment = new DetailFragment();
                sendDataToDetailFragment(item.getId(), detailFragment);
                loadFragment(detailFragment);
            }
        }));
    }

    private void loadFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    //Отправление данных в другой фрагмент
    private void sendDataToDetailFragment(String message, Fragment fragment) {
        Bundle bundle = new Bundle();
        bundle.putString("_id", message);
        fragment.setArguments(bundle);
    }
}
