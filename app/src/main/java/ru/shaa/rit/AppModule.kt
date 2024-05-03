package ru.shaa.rit

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.shaa.main.data.api.DogApi
import ru.shaa.main.data.api.createApi
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    private const val DOG_API_BASE_URL = "https://dog.ceo/api/breeds/image/"

    @Provides
    @Singleton
    fun provideDogApi(): DogApi {
        return createApi(DOG_API_BASE_URL)
    }

//    @Provides
//    @Singleton
//    fun provideRepository(): DogRepository {
//        return DogRepositoryImpl(api = provideDogApi())
//    }
}