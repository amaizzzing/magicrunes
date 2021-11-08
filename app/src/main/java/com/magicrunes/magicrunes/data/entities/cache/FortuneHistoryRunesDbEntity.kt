package com.magicrunes.magicrunes.data.entities.cache

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    foreignKeys = [
        ForeignKey(
            entity = HistoryFortuneDbEntity::class,
            parentColumns = arrayOf("id"),
            childColumns = arrayOf("idHistory"),
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = RuneDbEntity::class,
            parentColumns = arrayOf("id"),
            childColumns = arrayOf("idRune"),
            onDelete = ForeignKey.CASCADE
        )
    ]
)
class FortuneHistoryRunesDbEntity(): BaseRoom() {
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0
    var idHistory: Long = 0
    var idRune: Long = 0
    var state: Int = 0
}