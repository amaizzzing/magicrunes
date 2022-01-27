package com.magicrunes.magicrunes.data.entities.cache

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    foreignKeys = [
        ForeignKey(
            entity = FortuneDbEntity::class,
            parentColumns = arrayOf("id"),
            childColumns = arrayOf("idFortune"),
            onDelete = ForeignKey.CASCADE
        )
    ]
)
class HistoryFortuneDbEntity: BaseRoom() {
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0
    var idFortune: Long = 0
    var date: Long = 0
    var comment: String = ""
    var fortuneDescription: String = ""
    var syncState: Int = 0
}