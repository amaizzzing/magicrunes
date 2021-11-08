package com.magicrunes.magicrunes.data.entities.cache

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    foreignKeys = [
        ForeignKey(
            entity = RuneDbEntity::class,
            parentColumns = arrayOf("id"),
            childColumns = arrayOf("idRune"),
            onDelete = ForeignKey.CASCADE
        )
    ]
)
class HistoryRuneDbEntity(): BaseRoom() {
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0
    var date: Long = 0
    @ColumnInfo(index = true)
    var idRune: Long = 0
    var comment: String = ""
    var state: Int = 0
}