package com.example.semen.contactslist;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


/**
 * A simple {@link Fragment} subclass.
 */
public class ContactListFragment extends Fragment {
    private final String TAG = this.getClass().getSimpleName();
    Button buttonOpenDetailFragment;
    EditText etContactListFragment;


    public ContactListFragment() {
        // Required empty public constructor
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

        buttonOpenDetailFragment = view.findViewById(R.id.buttonOpenDetailFragment);
        etContactListFragment = view.findViewById(R.id.etContactListFragment);

        buttonOpenDetailFragment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i(TAG, "Message from Fragment");
//                Toast.makeText(getContext(), "Find Context", Toast.LENGTH_SHORT).show();

                DetailFragment detailFragment = new DetailFragment();

                String message = etContactListFragment.getText().toString();

                sendDataToDetailFragment(message,detailFragment);

                loadFragment(detailFragment);
            }
        });
    }

    private void loadFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    private void sendDataToDetailFragment(String message, Fragment fragment) {
        Bundle bundle = new Bundle();
        bundle.putString("Message", message);
        fragment.setArguments(bundle);
    }

    //TODO: залить на GitHub
    //TODO: исправить название проекта

}
