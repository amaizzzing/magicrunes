package com.magicrunes.magicrunes.data.services.database.firestore

import com.google.firebase.firestore.FirebaseFirestore
import com.magicrunes.magicrunes.data.entities.cache.FortuneHistoryRunesDbEntity
import com.magicrunes.magicrunes.data.entities.cache.HistoryFortuneDbEntity
import com.magicrunes.magicrunes.data.entities.cache.HistoryRuneDbEntity
import kotlinx.coroutines.tasks.await

const val DB_COLLECTION_NAME = "runes"

class FireDatabase(
    private val db: FirebaseFirestore
): IFireDatabase {
    override suspend fun insertUser(id: String): Result<Unit> {
        var res:Result<Unit> = Result.success(Unit)

        return res
    }

    override suspend fun getLastRune(idUser: String): Result<HistoryRuneDbEntity?> {
        var res:Result<HistoryRuneDbEntity?> = Result.success(null)

        db.collection("users")
            .document(idUser)
            .get()
            .addOnSuccessListener { doc ->
                val historyRuneList = doc.get("historyRune")

                historyRuneList?.let { resultList ->
                    resultList as ArrayList<HashMap<String, String>>

                    resultList
                        .map { dbEntity ->
                            HistoryRuneDbEntity().apply {
                                idRune = dbEntity["idRune"]?.toLongOrNull() ?: 0
                                date = dbEntity["date"]?.toLongOrNull() ?: 0
                                comment = dbEntity["comment"] ?: ""
                                state = dbEntity["state"]?.toIntOrNull() ?: 0
                            }
                        }
                        .maxByOrNull { it.date }?.let {
                            res = Result.success(it)
                        }
                }
            }
            .addOnFailureListener {
                res = Result.failure(it)
            }
            .await()

        return res
    }

    override suspend fun insertHistoryRune(idUser: String, historyRune: HistoryRuneDbEntity): Result<Unit> {
        var res:Result<Unit> = Result.success(Unit)
        db.collection("users")
            .document(idUser)
            .get()
            .addOnSuccessListener { doc ->
                val historyRuneList = doc.get("historyRune")

                val newHistoryRune =
                    hashMapOf(
                        "idRune" to historyRune.idRune.toString(),
                        "date" to historyRune.date.toString(),
                        "comment" to historyRune.comment,
                        "state" to historyRune.state.toString()
                    )

                historyRuneList?.let { resultList ->
                    resultList as ArrayList<HashMap<String, String>>

                    resultList.add(newHistoryRune)

                    db.collection("users")
                        .document(idUser)
                        .update("historyRune", resultList)
                } ?: run {
                    db.collection("users")
                        .document(idUser)
                        .get()
                        .addOnCompleteListener {
                            if (it.isSuccessful && it.result.exists()) {
                                db.collection("users")
                                    .document(idUser)
                                    .update("historyRune", listOf(newHistoryRune))
                            } else {
                                db.collection("users")
                                    .document(idUser)
                                    .set(object {
                                        val id = idUser
                                        val historyRune = listOf(newHistoryRune)
                                    })
                            }
                        }
                }
            }
            .addOnFailureListener {
                res = Result.failure(it)
            }
            .await()

        return res
    }

    override suspend fun getRuneByHistoryDate(idUser: String, historyDate: Long): Result<HistoryRuneDbEntity?> {
        var res:Result<HistoryRuneDbEntity?> = Result.success(null)

        db.collection("users")
            .document(idUser)
            .get()
            .addOnSuccessListener { doc ->
                val historyRuneList = doc.get("historyRune")

                historyRuneList?.let { resultList ->
                    resultList as ArrayList<HashMap<String, String>>

                    resultList.find { it["date"]?.toLongOrNull() == historyDate }?.let { dbEntity ->
                        res = Result.success(
                            HistoryRuneDbEntity().apply {
                                idRune = dbEntity["idRune"]?.toLongOrNull() ?: 0
                                date = dbEntity["date"]?.toLongOrNull() ?: 0
                                comment = dbEntity["comment"] ?: ""
                                state = dbEntity["state"]?.toIntOrNull() ?: 0
                            }
                        )
                    }
                }
            }
            .addOnFailureListener {
                res = Result.failure(it)
            }
            .await()

        return res
    }

    override suspend fun getAllHistoryRune(idUser: String): Result<List<HistoryRuneDbEntity>> {
        var res:Result<List<HistoryRuneDbEntity>> = Result.success(listOf())

        db.collection("users")
            .document(idUser)
            .get()
            .addOnSuccessListener { doc ->
                val historyRuneList = doc.get("historyRune")
                var listResult = listOf<HistoryRuneDbEntity>()

                historyRuneList?.let { resultList ->
                    resultList as ArrayList<HashMap<String, String>>

                    listResult = resultList.map { dbEntity ->
                        HistoryRuneDbEntity().apply {
                            idRune = dbEntity["idRune"]?.toLongOrNull() ?: 0
                            date = dbEntity["date"]?.toLongOrNull() ?: 0
                            comment = dbEntity["comment"] ?: ""
                            state = dbEntity["state"]?.toIntOrNull() ?: 0
                        }
                    }
                }
                res = Result.success(listResult)
            }
            .addOnFailureListener {
                res = Result.failure(it)
            }
            .await()

        return res
    }

    override suspend fun updateCommentRune(
        idUser: String,
        historyDate: Long,
        comment: String
    ): Result<Unit> {
        var res:Result<Unit> = Result.success(Unit)
        db.collection("users")
            .document(idUser)
            .get()
            .addOnSuccessListener { doc ->
                val historyRuneList = doc.get("historyRune")

                historyRuneList?.let { resultList ->
                    resultList as ArrayList<HashMap<String, String>>

                    resultList.find { it["date"]?.toLong() == historyDate }?.let {
                        it["comment"] = comment
                    }

                    db.collection("users")
                        .document(idUser)
                        .update("historyRune", resultList)
                }
            }
            .addOnFailureListener {
                res = Result.failure(it)
            }
            .await()

        return res
    }

    override suspend fun getFortuneHistoryByDate(idUser: String, historyDate: Long): Result<HistoryFortuneDbEntity?> {
        var res:Result<HistoryFortuneDbEntity?> = Result.success(null)

        db.collection("users")
            .document(idUser)
            .get()
            .addOnSuccessListener { doc ->
                val historyRuneList = doc.get("historyFortune")

                historyRuneList?.let { resultList ->
                    resultList as ArrayList<HashMap<String, String>>

                    resultList.find { it["date"]?.toLong() == historyDate }?.let { dbEntity ->
                        res = Result.success(
                            HistoryFortuneDbEntity().apply {
                                idFortune = dbEntity["idFortune"]?.toLongOrNull() ?: 0
                                date = dbEntity["date"]?.toLongOrNull() ?: 0
                                comment = dbEntity["comment"] ?: ""
                                fortuneDescription = dbEntity["fortuneDescription"] ?: ""
                            }
                        )
                    } ?: Result.success(null)
                }
            }
            .addOnFailureListener {
                res = Result.failure(it)
            }
            .await()

        return res
    }

    override suspend fun updateHistoryFortune(idUser: String, idFortune: Long, date: Long, fortuneRunesList: List<FortuneHistoryRunesDbEntity>): Result<Unit> {
        var res:Result<Unit> = Result.success(Unit)

        db.collection("users")
            .document(idUser)
            .get()
            .addOnSuccessListener { doc ->
                val historyRuneList = doc.get("historyFortune")

                val historyFortuneDbEntity = HistoryFortuneDbEntity().apply {
                    this.idFortune = idFortune
                    this.date = date
                    this.comment = ""
                }
                val newHistoryFortune = hashMapOf(
                    "id" to "0",
                    "idFortune" to historyFortuneDbEntity.idFortune.toString(),
                    "date" to historyFortuneDbEntity.date.toString(),
                    "comment" to historyFortuneDbEntity.comment,
                    "fortuneDescription" to historyFortuneDbEntity.fortuneDescription,
                    "runes" to fortuneRunesList
                )

                historyRuneList?.let { resultList ->
                    resultList as ArrayList<HashMap<String, Any>>

                    resultList.find { (it["date"] as String).toLong() == historyFortuneDbEntity.date }?.let { dbEntity ->
                        dbEntity["idFortune"] = historyFortuneDbEntity.idFortune.toString()
                    } ?: resultList.add(newHistoryFortune)

                    db.collection("users")
                        .document(idUser)
                        .update("historyFortune", resultList)
                } ?: run {
                    db.collection("users")
                        .document(idUser)
                        .update("historyFortune", listOf(newHistoryFortune))
                }
            }
            .addOnFailureListener {
                res = Result.failure(it)
            }
            .await()

        return res
    }

    override suspend fun getLastFortuneInHistory(idUser: String): Result<HistoryFortuneDbEntity?> {
        var res:Result<HistoryFortuneDbEntity?> = Result.success(null)

        db.collection("users")
            .document(idUser)
            .get()
            .addOnSuccessListener { doc ->
                val historyRuneList = doc.get("historyFortune")

                historyRuneList?.let { resultList ->
                    resultList as ArrayList<HashMap<String, String>>

                    resultList
                        .map { dbEntity ->
                            HistoryFortuneDbEntity().apply {
                                idFortune = dbEntity["idFortune"]?.toLongOrNull() ?: 0
                                date = dbEntity["date"]?.toLongOrNull() ?: 0
                                comment = dbEntity["comment"] ?: ""
                                fortuneDescription = dbEntity["fortuneDescription"] ?: ""
                            }
                        }
                        .maxByOrNull { it.date }?.let {
                            res = Result.success(it)
                        }
                }
            }
            .addOnFailureListener {
                res = Result.failure(it)
            }
            .await()

        return res
    }

    override suspend fun getLastInHistoryByIdFortune(idUser: String, idFortune: Long): Result<HistoryFortuneDbEntity?> {
        var res:Result<HistoryFortuneDbEntity?> = Result.success(null)

        db.collection("users")
            .document(idUser)
            .get()
            .addOnSuccessListener { doc ->
                val historyRuneList = doc.get("historyFortune")

                historyRuneList?.let { resultList ->
                    resultList as ArrayList<HashMap<String, String>>

                    resultList
                        .filter { it["idFortune"]?.toLongOrNull() == idFortune }
                        .map { dbEntity ->
                            HistoryFortuneDbEntity().apply {
                                this.idFortune = dbEntity["idFortune"]?.toLongOrNull() ?: 0
                                date = dbEntity["date"]?.toLongOrNull() ?: 0
                                comment = dbEntity["comment"] ?: ""
                                fortuneDescription = dbEntity["fortuneDescription"] ?: ""
                            }
                        }
                        .maxByOrNull { it.date }?.let {
                            res = Result.success(it)
                        }
                }
            }
            .addOnFailureListener {
                res = Result.failure(it)
            }
            .await()

        return res
    }

    override suspend fun getAllHistoryFortune(idUser: String): Result<List<HistoryFortuneDbEntity>> {
        var res:Result<List<HistoryFortuneDbEntity>> = Result.success(listOf())

        db.collection("users")
            .document(idUser)
            .get()
            .addOnSuccessListener { doc ->
                val historyRuneList = doc.get("historyFortune")

                historyRuneList?.let { resultList ->
                    resultList as ArrayList<HashMap<String, String>>

                    res = Result.success(
                        resultList.map { dbEntity ->
                            HistoryFortuneDbEntity().apply {
                                idFortune = dbEntity["idFortune"]?.toLongOrNull() ?: 0
                                date = dbEntity["date"]?.toLongOrNull() ?: 0
                                comment = dbEntity["comment"] ?: ""
                                fortuneDescription = dbEntity["fortuneDescription"] ?: ""
                            }
                        }
                    )
                }
            }
            .addOnFailureListener {
                res = Result.failure(it)
            }
            .await()

        return res
    }

    override suspend fun insertFortuneHistoryRune(
        idUser: String,
        fortuneRune: FortuneHistoryRunesDbEntity
    ): Result<Unit> {
        var res:Result<Unit> = Result.success(Unit)

        db.collection("users")
            .document(idUser)
            .get()
            .addOnSuccessListener { doc ->
                val historyRuneList = doc.get("historyFortune")

                historyRuneList?.let { resultList ->
                    resultList as ArrayList<HashMap<String, Any>>

                    resultList.find { (it["date"] as String).toLong() == fortuneRune.idHistory }?.let { dbEntity ->
                        val runes = dbEntity["runes"] as ArrayList<HashMap<String, Long>>
                        runes.add(
                            hashMapOf(
                                "id" to fortuneRune.id,
                                "idHistory" to fortuneRune.idHistory,
                                "idRune" to fortuneRune.idRune,
                                "state" to fortuneRune.state.toLong()
                            )
                        )
                        dbEntity["runes"] = runes
                    }

                    db.collection("users")
                        .document(idUser)
                        .update("historyFortune", resultList)
                }
            }
            .addOnFailureListener {
                res = Result.failure(it)
            }
            .await()

        return res
    }

    override suspend fun getFortuneRunesByHistoryDate(
        idUser: String,
        historyDate: Long
    ): Result<List<FortuneHistoryRunesDbEntity>> {
        var res:Result<List<FortuneHistoryRunesDbEntity>> = Result.success(listOf())

        db.collection("users")
            .document(idUser)
            .get()
            .addOnSuccessListener { doc ->
                val historyRuneList = doc.get("historyFortune")

                historyRuneList?.let { resultList ->
                    resultList as ArrayList<HashMap<String, Any>>

                    resultList.find { (it["date"] as String).toLong() == historyDate }?.let { dbEntity ->
                        val runes = dbEntity["runes"] as ArrayList<HashMap<String, Long>>
                        res = Result.success(
                            runes.map {
                                FortuneHistoryRunesDbEntity().apply {
                                    id = it["id"] ?: 0L
                                    idHistory = it["idHistory"] ?: 0L
                                    idRune = it["idRune"] ?: 0L
                                    state = it["state"]?.toInt() ?: 0
                                }
                            }
                        )
                    }
                }
            }
            .addOnFailureListener {
                res = Result.failure(it)
            }
            .await()

        return res
    }

    override suspend fun updateHistoryRuneByDate(
        idUser: String,
        idRune: Long,
        comment: String,
        state: Int,
        syncState: Int,
        historyDate: Long
    ): Result<Unit> {
        var res:Result<Unit> = Result.success(Unit)

        db.collection("users")
            .document(idUser)
            .get()
            .addOnSuccessListener { doc ->
                val historyRuneList = doc.get("historyRune")

                val newHistoryRune = hashMapOf(
                    "id" to "0",
                    "idRune" to idRune.toString(),
                    "comment" to comment,
                    "state" to state.toString(),
                    "syncState" to syncState.toString(),
                    "date" to historyDate.toString()
                )

                historyRuneList?.let { resultList ->
                    resultList as ArrayList<HashMap<String, String>>

                    resultList.find { (it["date"] as String).toLong() == historyDate }?.let { dbEntity ->
                        dbEntity["id"] = "0"
                        dbEntity["idRune"] = idRune.toString()
                        dbEntity["comment"] = comment
                        dbEntity["state"] = state.toString()
                        dbEntity["syncState"] = syncState.toString()
                        dbEntity["date"] = historyDate.toString()
                    } ?: resultList.add(newHistoryRune)

                    db.collection("users")
                        .document(idUser)
                        .update("historyRune", resultList)
                } ?: run {
                    db.collection("users")
                        .document(idUser)
                        .update("historyRune", listOf(newHistoryRune))
                }
            }
            .addOnFailureListener {
                res = Result.failure(it)
            }
            .await()

        return res
    }
}