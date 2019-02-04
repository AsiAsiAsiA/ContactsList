package com.example.semen.contactslist.repo;

import com.example.semen.contactslist.domain.Contact;

import java.util.List;

import io.reactivex.Single;

public interface Repository {
    Single<List<Contact>> getContacts();

    Single<Contact> findContactById(String id);
}
