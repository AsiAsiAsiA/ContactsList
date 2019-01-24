package com.example.semen.contactslist.presentation.view;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy;
import com.arellomobile.mvp.viewstate.strategy.SingleStateStrategy;
import com.arellomobile.mvp.viewstate.strategy.SkipStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;
import com.example.semen.contactslist.domain.model.Contact;

import java.util.List;

public interface ContactListFragmentView extends MvpView {
    @StateStrategyType(AddToEndSingleStrategy.class)
    void startLoading();

    @StateStrategyType(AddToEndSingleStrategy.class)
    void loadList(List<Contact> contacts);

    @StateStrategyType(SingleStateStrategy.class)
    void showPermissionsNotGranted();

    @StateStrategyType(SkipStrategy.class)
    void finishLoading();

    @StateStrategyType(SingleStateStrategy.class)
    void showThrowableMessage(String message);
}
