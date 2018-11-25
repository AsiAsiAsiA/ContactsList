package com.example.semen.contactslist;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.semen.contactslist.adapter.ContactsAdapter;
import com.example.semen.contactslist.model.Contact;
import com.example.semen.contactslist.service.ContactsContentResolver;

import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class ContactListFragment extends Fragment {
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

        //TODO: Чтение контактов на главном потоке программы. Неприемлемо с точки зрения отзывчивого UI.
        //TODO:Так же не спрашивается разрешение на чтение контактов.
        //TODO:Получение данных - лучше вынести в другой поток.
        contactArrayList = ContactsContentResolver.getContacts(requireContext());

        recyclerView = view.findViewById(R.id.my_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        recyclerView.setAdapter(new ContactsAdapter(contactArrayList,
                item -> loadFragment(DetailFragment.newInstance(item.getId()))));
    }

    //Размещение фрагмента во фрейм
    private void loadFragment(Fragment fragment) {
        getFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .addToBackStack(null)
                .commit();
    }
}
