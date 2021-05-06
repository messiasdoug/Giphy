package ca.com.freshworks.database.module

import ca.com.freshworks.database.DatabaseProvider
import ca.com.freshworks.database.GiphyDatabase
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val databaseModule = module {
    factory { DatabaseProvider.dataBase(androidContext()) }
    factory { get<GiphyDatabase>().gifDao() }
}