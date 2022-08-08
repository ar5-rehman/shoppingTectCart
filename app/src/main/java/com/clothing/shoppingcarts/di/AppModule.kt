package com.clothing.shoppingcarts.di

import android.content.Context
import com.clothing.shoppingcarts.base.navigation.NavManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
/***
 * Hilt SignletonComponent
 * */
@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    /**
     *  Provide SharePreferencesManager to save app's data
     */
    @Singleton
    @Provides
    fun provideSharePreferences(@ApplicationContext context: Context): SharePreferencesManager =
        SharePreferencesManager(context)

    /**
     * Navigation manager singleton object
     * */
    @Singleton
    @Provides
    fun provideNavManager(): NavManager = NavManager()

}