package com.magicrunes.magicrunes.domain.interactors.addcommentdialoginteractor

import com.magicrunes.magicrunes.ui.models.CommentDialogModel

interface ICommentDialogInteractor {
    suspend fun getDialogComment(idHistory: Long? = null): CommentDialogModel?

    suspend fun saveComment(idHistory: Long, comment: String)
}