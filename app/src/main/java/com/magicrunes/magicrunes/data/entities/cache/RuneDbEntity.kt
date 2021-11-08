package com.magicrunes.magicrunes.data.entities.cache

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class RuneDbEntity: BaseRoom() {
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0
    var name: String = ""
    var mainDescription: String = ""
    var imageLink: String = ""
    var localImageName: String = ""
}