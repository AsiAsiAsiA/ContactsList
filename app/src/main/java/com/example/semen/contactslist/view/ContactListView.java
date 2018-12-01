package com.example.semen.contactslist.view;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;
import com.example.semen.contactslist.model.Contact;

import java.util.List;

public interface ContactListView extends MvpView {
    @StateStrategyType(AddToEndSingleStrategy.class)
    void loadList(List<Contact> contacts);
}
