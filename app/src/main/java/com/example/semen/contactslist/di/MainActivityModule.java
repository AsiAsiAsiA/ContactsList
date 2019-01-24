package com.example.semen.contactslist.di;

import com.example.semen.contactslist.di.scope.FragmentScope;
import com.example.semen.contactslist.ui.ContactListFragment;
import com.example.semen.contactslist.ui.DetailFragment;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
interface MainActivityModule {
    @FragmentScope
    @ContributesAndroidInjector
    ContactListFragment contactListFragment();

    @FragmentScope
    @ContributesAndroidInjector
    DetailFragment detailFragment();
}