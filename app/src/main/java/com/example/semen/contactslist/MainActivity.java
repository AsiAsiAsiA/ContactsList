package com.example.semen.contactslist;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        ContactListFragment contactListFragment = new ContactListFragment();

        loadFragment(new ContactListFragment());

        Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show();
    }

    //TODO: 2 раза используется код loadFragment() в MainActivity и CaontactListFragment
    //TODO: Чтение данных из ContentProvider с помощью ContentResolver в отдельном классе
    //TODO: Получить имена и номера телефонов вывести в консоли (Ivan Ivanov 89992152255)
    //TODO: Реализовать RecyclerViewAdapter
    //TODO: Добавить данные RecyclerViewAdapter
    //TODO: Передать значение из RecyclerView в DetailFragment
    //TODO: Создать класс Contact
    //TODO: Создать класс Contact

    private void loadFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, fragment);
        fragmentTransaction.commit();
    }
}
