package com.example.semen.contactslist.service;

import com.example.semen.contactslist.model.Contact;

import java.util.List;

public interface AsyncResponseContactList {
    void loadList(List<Contact> contacts);
}
