package com.example.jetbankapp.dependencyinjection

@Module
@InstallIn(SingletonComponent::class)
object FirebaseModule {

    @Provides
    @Singleton
    fun provideFirebaseAnalytics(application: Application): FirebaseAnalytics =
        FirebaseAnalytics.getInstance(application)
}