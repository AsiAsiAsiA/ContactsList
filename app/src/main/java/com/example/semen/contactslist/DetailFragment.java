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
import com.example.semen.contactslist.service.DetailAsyncTask;

import java.util.concurrent.ExecutionException;


/**
 * A simple {@link Fragment} subclass.
 */
public class DetailFragment extends Fragment {
    TextView tvName;
    TextView tvPhoneNumber;
    Contact contact = null;

    public static DetailFragment newInstance(String id) {
        Bundle args = new Bundle();

        DetailFragment fragment = new DetailFragment();
        args.putString("_id", id);
        fragment.setArguments(args);

        return fragment;
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

        //TODO: Не спрашиваются разрешение на чтение.
//        Contact contact = ContactsContentResolver.findContactById(contactId,requireContext());

        DetailAsyncTask detailAsyncTask = new DetailAsyncTask(contactId);
        detailAsyncTask.execute(requireContext());

        try {
            contact = detailAsyncTask.get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }


        tvName = view.findViewById(R.id.tvName);
        tvPhoneNumber = view.findViewById(R.id.tvPhoneNumber);

        tvName.setText(String.format("%s %s", getString(R.string.id), contact.getName()));
        tvPhoneNumber.setText(String.format("%s %s", getString(R.string.phone_number), contact.getPhoneNumbers().toString()));
    }
}
