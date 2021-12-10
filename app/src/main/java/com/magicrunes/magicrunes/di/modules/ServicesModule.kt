package com.magicrunes.magicrunes.di.modules

import android.widget.ImageView
import androidx.room.Room
import com.magicrunes.magicrunes.MagicRunesApp
import com.magicrunes.magicrunes.data.services.database.db.MagicRunesDB
import com.magicrunes.magicrunes.data.services.image.GlideImageViewLoader
import com.magicrunes.magicrunes.data.services.image.IImageLoader
import com.magicrunes.magicrunes.data.services.image.ImageService
import com.magicrunes.magicrunes.data.services.network.GoogleService
import com.magicrunes.magicrunes.data.services.network.IGoogleService
import com.magicrunes.magicrunes.data.services.resource.IResourceService
import com.magicrunes.magicrunes.data.services.resource.ResourceService
import com.magicrunes.magicrunes.ui.fragments.currentfortunestrategies.FortuneFactory
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ServicesModule {
    @Singleton
    @Provides
    fun database(app: MagicRunesApp): MagicRunesDB =
        Room.databaseBuilder(
            app,
            MagicRunesDB::class.java,
            MagicRunesDB.DB_NAME
        )
        .createFromAsset(MagicRunesDB.DB_FIRST_LOAD_PATH)
        .build()

    @Singleton
    @Provides
    fun imageLoader(): IImageLoader<ImageView> =
        GlideImageViewLoader()

    @Singleton
    @Provides
    fun imgService(app: MagicRunesApp): ImageService = ImageService(app)

    @Singleton
    @Provides
    fun fortuneFactory(): FortuneFactory = FortuneFactory()

    @Singleton
    @Provides
    fun resourceService(app: MagicRunesApp): IResourceService = ResourceService(app)

    @Singleton
    @Provides
    fun googleService(app: MagicRunesApp): IGoogleService = GoogleService(app)
}