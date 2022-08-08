package com.clothing.shoppingcarts.di

import android.content.Context
import com.clothing.shoppingcarts.BuildConfig
import com.clothing.shoppingcarts.base.interceptors.ConnectivityInterceptor
import com.clothing.shoppingcarts.network.repositories.ClothingRepository
import com.clothing.shoppingcarts.network.repositories.ClothingRepositoryImpl
import com.clothing.shoppingcarts.network.services.ClothingService
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.logging.HttpLoggingInterceptor.Logger.Companion.DEFAULT
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit
/**
 * network related module. this module retrofit related configuration done
 * */
@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    private const val KEY_AUTHORIZATION = "auth"

    /**
     * Building retrofit client for networking request
     *  @param httpClient
     *  @return retrofit
     */
    private fun retrofitClient(httpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .client(httpClient)
            .addConverterFactory(MoshiConverterFactory.create(moshi()))
            .build()
    }

    /**
     * Building moshi for parsing json into Kotlin class
     */
    private fun moshi(): Moshi {
        val moshiBuilder = Moshi.Builder().add(KotlinJsonAdapterFactory())
        return moshiBuilder.build()
    }

    /**
     * Building http client to perform a network request
     * @param context, sharePreferencesManager
     * @return OkHttpClient
     */
    private fun httpClient(
        context: Context,
        sharePreferencesManager: SharePreferencesManager
    ): OkHttpClient {
        val httpLoggingInterceptor = HttpLoggingInterceptor(DEFAULT)
        val clientBuilder = OkHttpClient.Builder()
        if (BuildConfig.DEBUG) {
            httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
            clientBuilder.addInterceptor(httpLoggingInterceptor)
        }

        // headers
       /* clientBuilder.addInterceptor { chain ->
            val request = chain.request()
            val builder = request.newBuilder()
            val headers = HashMap<String, String>()
            headers[KEY_AUTHORIZATION] = "${sharePreferencesManager.token}"

            for ((key, value) in headers) {
                builder.addHeader(key, value)
            }
            chain.proceed(builder.build())
        }*/

        clientBuilder.addInterceptor(ConnectivityInterceptor(context))
        clientBuilder.connectTimeout(60, TimeUnit.SECONDS)
        clientBuilder.readTimeout(120, TimeUnit.SECONDS)
        clientBuilder.writeTimeout(120, TimeUnit.SECONDS)
        return clientBuilder.build()
    }

    private fun createNetworkClient(context: Context, sharePreferencesManager: SharePreferencesManager): Retrofit {
        return retrofitClient(httpClient(context, sharePreferencesManager))
    }

    @Provides
    fun provideRetrofit(@ApplicationContext context: Context, sharePreferencesManager: SharePreferencesManager): Retrofit {
        return createNetworkClient(context, sharePreferencesManager)
    }

    @Provides
    fun provideClothingService(retrofit: Retrofit): ClothingService {
        return retrofit.create(ClothingService::class.java)
    }

    @Provides
    fun provideClothingRepository(authService: ClothingService): ClothingRepository {
        return ClothingRepositoryImpl(authService)
    }

}