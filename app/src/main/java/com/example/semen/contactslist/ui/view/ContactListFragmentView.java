package com.example.semen.contactslist.ui.view;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy;
import com.arellomobile.mvp.viewstate.strategy.SingleStateStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;
import com.example.semen.contactslist.domain.Contact;

import java.util.List;

public interface ContactListFragmentView extends MvpView {
    @StateStrategyType(AddToEndSingleStrategy.class)
    void startLoading();

    @StateStrategyType(AddToEndSingleStrategy.class)
    void loadList(List<Contact> contacts);

    @StateStrategyType(AddToEndSingleStrategy.class)
    void finishLoading();

    @StateStrategyType(SingleStateStrategy.class)
    void showPermissionsNotGranted();

    @StateStrategyType(SingleStateStrategy.class)
    void showThrowableMessage(String message);
}
