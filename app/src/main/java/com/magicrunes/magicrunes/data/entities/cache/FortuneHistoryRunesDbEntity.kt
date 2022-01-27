package com.magicrunes.magicrunes.data.entities.cache

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity()
class FortuneHistoryRunesDbEntity(): BaseRoom() {
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0
    var idHistory: Long = 0
    var idRune: Long = 0
    var state: Int = 0
    var syncState: Int = 0
}