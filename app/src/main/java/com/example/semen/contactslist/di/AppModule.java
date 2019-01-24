package com.example.semen.contactslist.di;

import com.example.semen.contactslist.data.repo.Repository;
import com.example.semen.contactslist.data.repo.RepositoryImpl;
import com.example.semen.contactslist.di.scope.ActivityScope;
import com.example.semen.contactslist.presentation.MainActivity;

import javax.inject.Singleton;

import dagger.Binds;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;
import dagger.android.support.AndroidSupportInjectionModule;

@Module(includes = {AndroidSupportInjectionModule.class})
interface AppModule {
    @Singleton
    @Binds
    Repository repository(RepositoryImpl repository);

    @ActivityScope
    @ContributesAndroidInjector(modules = {MainActivityModule.class})
    MainActivity mainActivityInjector();
}
