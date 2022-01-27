package com.magicrunes.magicrunes.domain.interactors.addcommentdialoginteractor

import com.magicrunes.magicrunes.data.repositories.historyRune.HistoryRuneRepositoryFactory
import com.magicrunes.magicrunes.data.repositories.rune.IRuneRepository
import com.magicrunes.magicrunes.ui.models.CommentDialogModel

class CommentDialogInteractor(
    private val runeRepository: IRuneRepository,
    private val historyRuneRepositoryFactory: HistoryRuneRepositoryFactory
): ICommentDialogInteractor {
    override suspend fun getDialogComment(historyDate: Long?): CommentDialogModel? {
        val historyRuneRepository = historyRuneRepositoryFactory.getHistoryRuneRepository()

        val historyRune =
            historyDate?.let {
                historyRuneRepository.getRuneByHistoryDate(historyDate)
            } ?: historyRuneRepository.getLastRune()

        historyRune?.let {
            runeRepository.getRuneById(it.idRune)?.let { rune ->
                return CommentDialogModel(rune, it)
            }
        }

        return null
    }

    override suspend fun saveComment(historyDate: Long, comment: String) {
        historyRuneRepositoryFactory.getHistoryRuneRepository().updateComment(historyDate, comment)
    }
}