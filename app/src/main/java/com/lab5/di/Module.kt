package com.lab5.di

import android.content.Context
import androidx.room.Room
import com.lab5.data.db.Lab5Database
import com.lab5.ui.screens.subjectDetails.SubjectDetailsViewModel
import com.lab5.ui.screens.subjectsList.SubjectsListViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

/**
 * appModule = module{...} - is Koin module for creating instances of all components in App
 * - invokes in App class
 * - in the module{...} scope you can create different instances by functions single{}, factory{}, viewModel{}
 * - in the module{...} scope to get some other instance which was created in scope you can call get()
 */
val appModule = module {
    /**
        single{ ...class() } - create single (singleton) instance of class
        In this example is created single instance of Lab5Database database
        - get<Context>() - function gets the context which is in Koin module by default
     */
    single<Lab5Database> {
        Room.databaseBuilder(
            get<Context>(),
            Lab5Database::class.java, "lab5Database"
        ).build()
    }

    /**
        viewModel{ ...viewModelClass() } - create ViewModel instance
        - get<Lab5Database>() - function gets the instance of database which is created above

     */

    viewModel { SubjectsListViewModel(get()) }
    viewModel { SubjectDetailsViewModel(get()) }
}