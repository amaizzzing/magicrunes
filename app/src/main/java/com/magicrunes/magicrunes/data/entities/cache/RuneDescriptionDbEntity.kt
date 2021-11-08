package com.magicrunes.magicrunes.data.entities.cache

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
class RuneDescriptionDbEntity: BaseRoom() {
    @PrimaryKey(autoGenerate = true)
    var idRune: Long = 0
    var avDescription: String = ""
    var revDescription: String = ""
}