package com.example.semen.contactslist;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import com.arellomobile.mvp.MvpAppCompatActivity;

public class MainActivity extends MvpAppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            loadFragment(ContactListFragment.newInstance());
        }
    }

    //Размещение фрагмента во фрейм
    private void loadFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, new ContactListFragment())
                .commit();
    }
}
