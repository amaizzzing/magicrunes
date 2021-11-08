package com.magicrunes.magicrunes.data.entities.cache

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    foreignKeys = [
        ForeignKey(
            entity = CombinationDbEntity::class,
            parentColumns = arrayOf("id"),
            childColumns = arrayOf("idCombination"),
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
class RunesInCombinationDbEntity: BaseRoom() {
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0
    var idCombination: Long = 0
    var idRune: Long = 0
}