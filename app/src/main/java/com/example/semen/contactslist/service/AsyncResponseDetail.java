package com.example.semen.contactslist.service;

import com.example.semen.contactslist.model.Contact;

public interface AsyncResponseDetail {
    void loadContactFromContentProvider(Contact contact);
}
