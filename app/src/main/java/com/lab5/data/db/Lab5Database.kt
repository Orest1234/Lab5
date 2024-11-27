package com.lab5.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.lab5.data.dao.SubjectDao
import com.lab5.data.dao.SubjectLabsDao
import com.lab5.data.entity.SubjectEntity
import com.lab5.data.entity.SubjectLabEntity

/**
 * Lab4Database - the main database class
 * - extends on RoomDatabase()
 * - marked with @Database annotation for generating communication interfaces
 * - in annotation are added all your entities (tables)
 * - includes abstract properties of all DAO interfaces for each entity (table)
 */
@Database(entities = [SubjectEntity::class, SubjectLabEntity::class], version = 1)
abstract class Lab5Database : RoomDatabase() {
    //DAO properties for each entity (table)
    // must be abstract (because Room will generate instances by itself)
    abstract val subjectsDao: SubjectDao
    abstract val subjectLabsDao: SubjectLabsDao
}