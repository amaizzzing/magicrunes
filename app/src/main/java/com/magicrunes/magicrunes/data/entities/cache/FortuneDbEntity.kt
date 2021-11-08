package com.magicrunes.magicrunes.data.entities.cache

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class FortuneDbEntity(): BaseRoom() {
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0
    var nameFortune: String = ""
    var description: String = ""
    var favourite: Boolean = false
    var lastDate: Long = 0
    var localImageName: String = ""
}