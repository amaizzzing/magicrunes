package com.magicrunes.magicrunes.domain.interactors.addcommentdialoginteractor

import com.magicrunes.magicrunes.data.repositories.historyRune.IHistoryRuneRepository
import com.magicrunes.magicrunes.data.repositories.rune.IRuneRepository
import com.magicrunes.magicrunes.ui.models.CommentDialogModel

class CommentDialogInteractor(
    private val runeRepository: IRuneRepository,
    private val historyRuneRepository: IHistoryRuneRepository
): ICommentDialogInteractor {
    override suspend fun getDialogComment(idHistory: Long?): CommentDialogModel? {
        val historyRune =
            idHistory?.let {
                historyRuneRepository.getRuneByHistoryId(idHistory)
            } ?: historyRuneRepository.getLastRune()

        historyRune?.let {
            runeRepository.getRuneById(it.idRune)?.let { rune ->
                return CommentDialogModel(rune, it)
            }
        }

        return null
    }

    override suspend fun saveComment(idHistory: Long, comment: String) {
        historyRuneRepository.updateComment(idHistory, comment)
    }
}