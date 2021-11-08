package com.magicrunes.magicrunes.data.entities.cache

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class CombinationDbEntity: BaseRoom() {
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0
    var name: String = ""
    var description: String = ""
}