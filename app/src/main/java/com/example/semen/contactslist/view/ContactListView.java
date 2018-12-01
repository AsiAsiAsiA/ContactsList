package com.example.semen.contactslist.view;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy;
import com.arellomobile.mvp.viewstate.strategy.SingleStateStrategy;
import com.arellomobile.mvp.viewstate.strategy.SkipStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;
import com.example.semen.contactslist.model.Contact;

import java.util.List;

public interface ContactListView extends MvpView {
    @StateStrategyType(SkipStrategy.class)
    void startLoading();

    @StateStrategyType(OneExecutionStateStrategy.class)
    void loadList(List<Contact> contacts);

    @StateStrategyType(SingleStateStrategy.class)
    void noPermissions();
}
