package com.magicrunes.magicrunes.domain.interactors.addcommentdialoginteractor

import com.magicrunes.magicrunes.ui.models.CommentDialogModel

interface ICommentDialogInteractor {
    suspend fun getDialogComment(historyDate: Long? = null): CommentDialogModel?

    suspend fun saveComment(historyDate: Long, comment: String)
}