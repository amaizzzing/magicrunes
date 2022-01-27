package com.magicrunes.magicrunes.data.services.database.room.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.magicrunes.magicrunes.data.entities.cache.*
import com.magicrunes.magicrunes.data.services.database.room.dao.*

@Database(
    entities = [
        RuneDbEntity::class,
        RuneDescriptionDbEntity::class,
        HistoryRuneDbEntity::class,
        FortuneDbEntity::class,
        HistoryFortuneDbEntity::class,
        FortuneHistoryRunesDbEntity::class,
        CombinationDbEntity::class,
        RunesInCombinationDbEntity::class,
        UserInfoDbEntity::class
    ],
    version = 1,
    exportSchema = false
)

abstract class MagicRunesDB: RoomDatabase() {
    abstract val runeDao: RuneDao
    abstract val runeDescriptionDao: RuneDescriptionDao
    abstract val historyRuneDao: HistoryRuneDao

    abstract val fortuneDao: FortuneDao
    abstract val historyFortuneDao: HistoryFortuneDao
    abstract val fortuneHistoryRunesDao: FortuneHistoryRunesDao

    abstract val combinationDao: CombinationDao
    abstract val runesInCombinationDao: RunesInCombinationDao

    abstract val userInfoDao: UserInfoDao

    companion object {
        const val DB_NAME = "magicrunes.db"

        const val DB_FIRST_LOAD_PATH = "database/pre_magicrunes.db"
    }
}