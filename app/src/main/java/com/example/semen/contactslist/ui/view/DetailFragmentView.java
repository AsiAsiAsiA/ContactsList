package com.example.semen.contactslist.ui.view;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy;
import com.arellomobile.mvp.viewstate.strategy.SingleStateStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;
import com.example.semen.contactslist.domain.Contact;

public interface DetailFragmentView extends MvpView {
    @StateStrategyType(AddToEndSingleStrategy.class)
    void loadContactFromContentProvider(Contact contact);

    @StateStrategyType(SingleStateStrategy.class)
    void showPermissionsNotGranted();

    @StateStrategyType(SingleStateStrategy.class)
    void showThrowableMessage(String message);
}
