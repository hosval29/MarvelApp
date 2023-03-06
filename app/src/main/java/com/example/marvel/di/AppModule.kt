package com.example.marvel.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.example.marvel.BuildConfig
import com.example.marvel.R
import com.example.marvel.core.utils.Utils
import com.example.marvel.data.local.database.MarvelAppDatabase
import com.example.marvel.data.paging.ComicsRemoteMediator
import com.example.marvel.data.remote.api.ComicsApi
import com.example.marvel.data.repository.ComicsRepositoryImpl
import com.example.marvel.domain.repository.ComicsRepository
import com.example.marvel.domain.use_case.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton
import kotlin.random.Random

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideOkHttpClient(application: Application): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(getHeaderInterceptor(application.applicationContext))
            .addInterceptor(
                HttpLoggingInterceptor().apply {
                    level = HttpLoggingInterceptor.Level.BODY
                }
            )
            .build()
    }

    private fun getHeaderInterceptor(context: Context): Interceptor = Interceptor { chain ->
        var request = chain.request()

        val ts = Random.nextInt(0, 3000)
        val hash =
            Utils.convertMD5("$ts${BuildConfig.MARVEL_API_PRIVATE_KEY}${BuildConfig.MARVEL_API_PUBLIC_KEY}")

        val url = request.url
            .newBuilder()
            .addQueryParameter(
                name = context.getString(R.string.api_ts),
                value = ts.toString()
            )
            .addQueryParameter(
                name = context.getString(R.string.api_key),
                value = BuildConfig.MARVEL_API_PUBLIC_KEY
            )
            .addQueryParameter(
                name = context.getString(R.string.api_hash),
                value = hash
            )
            .build()

        request = request
            .newBuilder()
            .url(url)
            .build()

        chain.proceed(request)
    }

    @Provides
    @Singleton
    fun provideRetrofit(client: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create())
            .client(client)
            .build()
    }

    @Singleton
    @Provides
    fun provideSeriesApi(retrofit: Retrofit): ComicsApi {
        return retrofit.create(ComicsApi::class.java)
    }

    @Provides
    @Singleton
    fun provideMarvelAppDatabase(
        app: Application
    ): MarvelAppDatabase {
        return Room.databaseBuilder(
            app,
            MarvelAppDatabase::class.java,
            app.applicationContext.getString(R.string.database_name)
        ).build()
    }

    @Provides
    @Singleton
    fun provideComicsRemoteMediator(
        database: MarvelAppDatabase,
        api: ComicsApi
    ): ComicsRemoteMediator {
        return ComicsRemoteMediator(
            database = database,
            api = api
        )
    }

    @Provides
    @Singleton
    fun provideSeriesRepository(
        database: MarvelAppDatabase,
        comicsRemoteMediator: ComicsRemoteMediator,
        comicsApi: ComicsApi
    ): ComicsRepository {
        return ComicsRepositoryImpl(
            comicsDao = database.comicsDao,
            comicsFavoritesDao = database.comicsFavoritesDao,
            comicsRemoteMediator = comicsRemoteMediator,
            comicsApi = comicsApi
        )
    }

    @Provides
    @Singleton
    fun provideSeriesUseCase(
        repository: ComicsRepository
    ): ComicsUseCase {
        return ComicsUseCase(
            getAllComics = GetAllComics(repository),
            getComicById = GetComicById(repository),
            getAllComicsFavorites = GetAllComicsFavorites(repository),
            updateComicFavoriteById = UpdateComicFavoriteById(repository),
            saveComicFavorite = SaveComicFavorite(repository)
        )
    }
}