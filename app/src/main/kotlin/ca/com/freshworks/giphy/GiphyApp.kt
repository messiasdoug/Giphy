package ca.com.freshworks.giphy

import android.app.Application
import ca.com.freshworks.database.module.databaseModule
import ca.com.freshworks.giphy.module.viewModelModule
import ca.com.freshworks.interactor.module.interactorModule
import ca.com.freshworks.network.module.networkModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.logger.AndroidLogger
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.core.logger.EmptyLogger

class GiphyApp : Application() {
    override fun onCreate() {
        super.onCreate()

        setupKoin()
    }

    override fun onTerminate() {
        stopKoin()
        super.onTerminate()
    }

    private fun setupKoin() {
        startKoin {
            modules(listOf(networkModule, interactorModule, viewModelModule, databaseModule))
            androidContext(this@GiphyApp)
            logger(if (BuildConfig.DEBUG) AndroidLogger() else EmptyLogger())
        }
    }
}