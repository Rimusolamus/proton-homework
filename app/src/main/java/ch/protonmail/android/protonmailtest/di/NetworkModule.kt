package ch.protonmail.android.protonmailtest.di

import ch.protonmail.android.protonmailtest.BuildConfig.API_URL
import ch.protonmail.android.protonmailtest.data.networking.Api
import ch.protonmail.android.protonmailtest.data.networking.ApiHelper
import ch.protonmail.android.protonmailtest.data.networking.ApiHelperImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideRetrofitClient(
        // Potential dependencies of this type
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(API_URL)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit) = retrofit.create(Api::class.java)

    @Provides
    @Singleton
    fun provideApiHelper(apiHelper: ApiHelperImpl): ApiHelper = apiHelper
}

