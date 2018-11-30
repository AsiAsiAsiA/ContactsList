package com.example.semen.contactslist;


import android.Manifest;
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

import com.example.semen.contactslist.model.Contact;
import com.example.semen.contactslist.service.AsyncResponseDetail;
import com.example.semen.contactslist.service.DetailAsyncTask;


/**
 * A simple {@link Fragment} subclass.
 */
public class DetailFragment extends Fragment implements AsyncResponseDetail {
    private TextView tvName;
    private TextView tvPhoneNumber;
    private String contactId;
    private static final int REQUEST_CODE_READ_CONTACTS = 1;
    private static final String _ID = "_id";
    private static final String EMPTY = "Empty";

    public static DetailFragment newInstance(String id) {
        Bundle args = new Bundle();

        DetailFragment fragment = new DetailFragment();
        args.putString(_ID, id);
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
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_CODE_READ_CONTACTS) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                queryContentProvider();
            } else {
                tvName.setText(getString(R.string.no_permission));
                tvPhoneNumber.setText(getString(R.string.no_permission));
            }
        }
    }

    //запрос в ContentProvider в отдельном потоке
    private void queryContentProvider() {
        DetailAsyncTask detailAsyncTask = new DetailAsyncTask(contactId, this);
        detailAsyncTask.execute(requireContext());
    }

    @Override
    public void loadContactFromContentProvider(Contact contact) {
        if (isResumed()) {
            tvName.setText(getString(R.string.detailFragment_text,
                    getString(R.string.id),
                    contact.getName()));
            tvPhoneNumber.setText(getString(R.string.detailFragment_text,
                    getString(R.string.phone_number),
                    contact.getPhoneNumbers().toString()));
        }
    }
}
