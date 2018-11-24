package com.example.semen.contactslist;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.semen.contactslist.DetailFragment;
import com.example.semen.contactslist.R;
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

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        //TODO:setRetainInstance(true); не стоит использовать для фрагментов у которых есть View
        //TODO:По возможности стоит избегать retain фрагментов. В крайнем случае делать их без view, иначе утечки памяти
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

        //TODO:Лучше перейти на Java 8 и лямбды
        recyclerView = view.findViewById(R.id.my_recycler_view);

        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));

        recyclerView.setAdapter(new ContactsAdapter(contactArrayList, new ContactsAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Contact item) {
                //TODO:Создание и инициализацию фрагмента принято делать в методе newInstance фрагмента.
                DetailFragment detailFragment = new DetailFragment();
                sendDataToDetailFragment(item.getId(), detailFragment);
                loadFragment(detailFragment);
            }
        }));
    }

    //Размещение фрагмента во фрейм
    //TODO:оформить как цепочку
    private void loadFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    //TODO:Эта логика должна быть внтури newInstance того фрагмента, который создается, как и создание фрагмента.
    //Отправление данных в другой фрагмент
    private void sendDataToDetailFragment(String message, Fragment fragment) {
        Bundle bundle = new Bundle();
        bundle.putString("_id", message);
        fragment.setArguments(bundle);
    }
}
