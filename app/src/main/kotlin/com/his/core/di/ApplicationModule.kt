/**
 * Copyright (C) 2018 Fernando Cejas Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.his.core.di

import android.arch.persistence.room.Room
import android.content.Context
import com.his.AndroidApplication
import com.his.BuildConfig
import com.his.features.login.data.ClientCreator
import com.his.features.login.data.repository.LoginDataRepository
import com.his.features.login.data.repository.LoginRepository
import com.his.features.movies.data.repository.MoviesDataRepository
import com.his.features.movies.data.repository.MoviesRepository
import com.his.features.movies.data.repository.local.AppDatabase
import com.his.features.movies.data.repository.local.db.MovieDetailsDao
import com.his.features.movies.data.repository.net.api.MoviesApi
import com.kingpower.data.net.ApiConnection
import com.kingpower.data.net.ApiConnectionImpl
import com.kingpower.data.net.graphql.GraphQLClient
import com.kingpower.data.net.graphql.GraphQLClientImpl
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class ApplicationModule(private val application: AndroidApplication) {

	@Provides
	@Singleton
	fun provideApplicationContext(): Context = application

	@Provides
	@Singleton
	fun provideMoviesApi(retrofit: Retrofit): MoviesApi {
		return retrofit.create(MoviesApi::class.java)
	}

 //	@Provides
//	@Singleton
//	fun provideCloudMoviesDataStore(moviesApi: MoviesApi, moviesDao: MovieDetailsDao, moviesDataMapper: MovieDataMapper) : MoviesCloudDataStore {
//		return MoviesCloudDataStore(moviesApi, moviesDao, moviesDataMapper)
//	}

	@Provides
	@Singleton
	fun provideRetrofit(): Retrofit {
		return Retrofit.Builder()
			.baseUrl("https://raw.githubusercontent.com/android10/Sample-Data/master/Android-CleanArchitecture-Kotlin/")
			.client(createClient())
			.addCallAdapterFactory(RxJava2CallAdapterFactory.create())
			.addConverterFactory(GsonConverterFactory.create())
			.build()
	}

	@Provides
	@Singleton
	fun provideDatabase(context: Context): AppDatabase = Room.databaseBuilder(context, AppDatabase::class.java, "app_database").build()

	@Provides
	@Singleton
	fun provideMovieDetailsDao(db: AppDatabase): MovieDetailsDao = db.movieDetailsDao()

	@Provides
	@Singleton
	fun provideMoviesRepository(dataSource: MoviesDataRepository): MoviesRepository = dataSource

	@Provides
	@Singleton
	fun provideLoginRepository(dataSource: LoginDataRepository): LoginRepository = dataSource

	@Provides
	@Singleton
	fun provideApiConnection(apiConnection: ApiConnectionImpl): ApiConnection {
		return apiConnection
	}

	@Provides
	@Singleton
	fun provideGraphQLClient(clientCreator: ClientCreator): GraphQLClient {
		return GraphQLClientImpl(clientCreator)
	}

	private fun createClient(): OkHttpClient {
		val okHttpClientBuilder: OkHttpClient.Builder = OkHttpClient.Builder()
		if (BuildConfig.DEBUG) {
			val loggingInterceptor = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BASIC)
			okHttpClientBuilder.addInterceptor(loggingInterceptor)
		}
		return okHttpClientBuilder.build()
	}
}
