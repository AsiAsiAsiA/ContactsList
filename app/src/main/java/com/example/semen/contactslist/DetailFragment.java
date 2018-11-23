package com.example.semen.contactslist;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.semen.contactslist.model.Contact;


/**
 * A simple {@link Fragment} subclass.
 */
public class DetailFragment extends Fragment {
    TextView tvName;
    TextView tvPhoneNumber;

    public DetailFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
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
        String contactId = bundle.getString("_id", "Empty");

        Contact contact = ContactsContentResolver.findContactById(contactId);

        tvName = view.findViewById(R.id.tvName);
        tvPhoneNumber = view.findViewById(R.id.tvPhoneNumber);
        tvName.setText("Name: " + contact.getName());
        tvPhoneNumber.setText("Phone Number: " + contact.getPhoneNumbers().toString());
    }
}
