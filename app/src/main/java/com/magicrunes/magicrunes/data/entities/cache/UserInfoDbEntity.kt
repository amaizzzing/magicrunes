package com.magicrunes.magicrunes.data.entities.cache

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class UserInfoDbEntity: BaseRoom() {
    @PrimaryKey
    var id: Long = 0
    var idGoogle: String = ""
    var idFirestore: String = ""
    var name: String = ""
    var lastname: String = ""
    var birthDate: Long = 0
    var lastLogin: Long = 0
    var countLoginInMonth: Int = 0
}