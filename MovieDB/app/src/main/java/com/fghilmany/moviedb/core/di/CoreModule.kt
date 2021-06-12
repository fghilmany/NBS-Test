package com.fghilmany.moviedb.core.di

import android.database.sqlite.SQLiteDatabase
import androidx.room.Room
import com.fghilmany.moviedb.BuildConfig
import com.fghilmany.moviedb.core.data.DataRepository
import com.fghilmany.moviedb.core.data.source.local.LocalDataSource
import com.fghilmany.moviedb.core.data.source.local.room.MovieDatabase
import com.fghilmany.moviedb.core.data.source.remote.RemoteDataSource
import com.fghilmany.moviedb.core.data.source.remote.network.ApiService
import com.fghilmany.moviedb.core.domain.repository.IDataRepository
import com.fghilmany.moviedb.core.utils.AppExecutors
import okhttp3.CertificatePinner
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val databaseModule = module {
    factory { get<MovieDatabase>().movieDao() }
    single {
        Room.databaseBuilder(
            androidContext(),
            MovieDatabase::class.java, "movie"
        ).fallbackToDestructiveMigration()
            .build()
    }
}

val networkModule = module {
    single {
        val interceptor = HttpLoggingInterceptor()
        interceptor.apply { interceptor.level = HttpLoggingInterceptor.Level.BODY }
        OkHttpClient.Builder()
            .connectTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .addInterceptor(interceptor)
            .build()


    }

    single {
        val retrofit = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .client(get())
            .baseUrl(BuildConfig.BASE_URL)
            .build()

        retrofit.create(ApiService::class.java)
    }
}

val repositoryModule = module {
    single { RemoteDataSource(get()) }
    single { LocalDataSource(get()) }
    factory { AppExecutors() }
    single<IDataRepository> {
        DataRepository(
            get(),
            get(),
            get()
        )
    }
}
