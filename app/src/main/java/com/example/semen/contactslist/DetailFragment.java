package com.example.semen.contactslist;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class DetailFragment extends Fragment {
    private final String TAG = this.getClass().getSimpleName();

    TextView tvMessageFromEditText;
    TextView tvContactDetail;
    TextView tvName;
    TextView tvPhoneNumber;


    public DetailFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_detail, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Bundle bundle = this.getArguments();
        String message = bundle.getString("Message", "No data from ContactFragment");
        Log.i(TAG,message);

        tvMessageFromEditText = view.findViewById(R.id.tvMessageFromEditText);
        tvMessageFromEditText.setText(tvMessageFromEditText.getText() + message);

        tvContactDetail = view.findViewById(R.id.tvContactDetail);
        tvName = view.findViewById(R.id.tvName);
        tvPhoneNumber = view.findViewById(R.id.tvPhoneNumber);


    }
}
