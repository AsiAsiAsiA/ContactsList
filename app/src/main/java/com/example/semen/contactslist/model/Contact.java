package com.example.semen.contactslist.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Contact {
    private final String id;
    private final String name;
    private final List<String> phoneNumbers;

    public Contact(String id, String name, List<String> phoneNumbers) {
        this.id = id;
        this.name = name;
        this.phoneNumbers = phoneNumbers;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<String> getPhoneNumbers() {
        return phoneNumbers;
    }

    @Override
    public String toString() {
        return "Contact{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", phoneNumbers=" + phoneNumbers +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Contact contact = (Contact) o;
        return Objects.equals(id, contact.id) &&
                Objects.equals(name, contact.name) &&
                Objects.equals(phoneNumbers, contact.phoneNumbers);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, phoneNumbers);
    }
}
