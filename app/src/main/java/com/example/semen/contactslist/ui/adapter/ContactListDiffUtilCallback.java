package com.example.semen.contactslist.ui.adapter;

import android.support.v7.util.DiffUtil;

import com.example.semen.contactslist.domain.Contact;

import java.util.List;

public class ContactListDiffUtilCallback extends DiffUtil.Callback {
    private final List<Contact> newList;
    private final List<Contact> oldList;

    public ContactListDiffUtilCallback(List<Contact> newList, List<Contact> oldList) {
        this.newList = newList;
        this.oldList = oldList;
    }

    @Override
    public int getOldListSize() {
        return oldList.size();
    }

    @Override
    public int getNewListSize() {
        return newList.size();
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        return newList.get(newItemPosition).getId().equals(oldList.get(oldItemPosition).getId());
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        final Contact newContact = newList.get(newItemPosition);
        final Contact oldContact = oldList.get(oldItemPosition);
        return newContact.equals(oldContact);
    }
}
