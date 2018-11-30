package com.example.semen.contactslist.view;

import com.arellomobile.mvp.MvpView;
import com.example.semen.contactslist.model.Contact;

import java.util.List;

public interface ContactListView extends MvpView {
    void loadList(List<Contact> contacts);
}
