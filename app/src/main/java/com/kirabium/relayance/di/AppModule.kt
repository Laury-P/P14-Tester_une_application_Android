package com.kirabium.relayance.di

import com.kirabium.relayance.data.CustomerRepositoryImpl
import com.kirabium.relayance.data.DummyData
import com.kirabium.relayance.domain.repository.CustomerRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    @Singleton
    fun provideCustomerRepository() : CustomerRepository = CustomerRepositoryImpl(dataSource = DummyData)
}
