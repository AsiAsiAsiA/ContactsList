package com.example.semen.contactslist.di;

import com.example.semen.contactslist.ContactListFragment;
import com.example.semen.contactslist.DetailFragment;
import com.example.semen.contactslist.di.scope.FragmentScope;

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