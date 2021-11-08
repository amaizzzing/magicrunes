package com.magicrunes.magicrunes.domain.interactors.runeInfoInteractor

import com.magicrunes.magicrunes.data.repositories.rune.IRuneRepository
import com.magicrunes.magicrunes.data.repositories.runeDescription.IRuneDescriptionRepository
import com.magicrunes.magicrunes.data.services.image.ImageService
import com.magicrunes.magicrunes.ui.models.lists.InfoRuneModel
import com.magicrunes.magicrunes.ui.states.BaseState

class RuneInfoInteractor(
    private val runeRepository: IRuneRepository,
    private val runeDescriptionRepository: IRuneDescriptionRepository,
    private val imageService: ImageService
): IRuneInfoInteractor {
    override suspend fun getRuneList(): BaseState =
        BaseState.Success(
            runeRepository.getAllRunes().map { rune ->
                runeDescriptionRepository.getRuneById(rune.id)?.let { runeDescription ->
                    InfoRuneModel(rune, runeDescription)
                }
            }
        )

    override suspend fun getRuneById(id: Long): BaseState =
        BaseState.Success(
            runeRepository.getRuneById(id)?.let { rune ->
                runeDescriptionRepository.getRuneById(rune.id)?.let { runeDescription ->
                    InfoRuneModel(rune, runeDescription)
                }
            }
        )
}