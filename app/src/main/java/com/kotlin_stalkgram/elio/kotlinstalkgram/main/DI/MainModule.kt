package com.kotlin_stalkgram.elio.kotlinstalkgram.main.DI

import android.content.Context
import com.kotlin_stalkgram.elio.kotlinstalkgram.domain.DownloadFileFromURL
import com.kotlin_stalkgram.elio.kotlinstalkgram.domain.DownloadPageAndParseHtml
import com.kotlin_stalkgram.elio.kotlinstalkgram.lib.base.EventBus
import com.kotlin_stalkgram.elio.kotlinstalkgram.main.*
import com.kotlin_stalkgram.elio.kotlinstalkgram.main.UI.MainView
import com.kotlin_stalkgram.elio.kotlinstalkgram.main.events.MainEvent
import dagger.Module
import dagger.Provides
import javax.inject.Named
import javax.inject.Singleton

/**
 * Created by elio on 8/5/16.
 */
@Module
class MainModule(internal var view: MainView, internal var context: Context) {

    @Provides
    @Singleton
    fun provideContext(): Context {
        return this.context
    }

    @Provides
    @Singleton
    fun providesMainView(): MainView {
        return view
    }

    @Provides
    @Singleton
    fun providesMainEvent(): MainEvent {
        return MainEvent()
    }

    @Provides
    @Singleton
    fun providesDownloadPageAndParseHtml(): DownloadPageAndParseHtml {
        return DownloadPageAndParseHtml(context)
    }

    @Provides
    @Singleton
    fun providesDownloadFileFromURL(eventBus: EventBus, event: MainEvent): DownloadFileFromURL {
        return DownloadFileFromURL(eventBus, event)
    }

    @Provides
    @Singleton
    fun providesMainPresenter(context: Context, eventBus: EventBus, mainView: MainView, mainInteractor: MainInteractor, parseHtml: DownloadPageAndParseHtml): MainPresenter {
        return MainPresenterImpl(context, eventBus, mainView, mainInteractor, parseHtml)
    }

    @Provides
    @Singleton
    fun providesMainInteractor(context: Context, @Named("MainImageRepositoryImpl") mainImageRepository: MainRepository, @Named("MainVideoRepositoryImpl") mainVideoRepository: MainRepository, eventBus: EventBus, event: MainEvent): MainInteractor {
        return MainInteractorImpl(context, mainImageRepository, mainVideoRepository, eventBus, event)
    }

    @Provides
    @Named("MainImageRepositoryImpl")
    @Singleton
    fun providesMainImageRepositoryImpl(downloadFileFromURL: DownloadFileFromURL, eventBus: EventBus, event: MainEvent): MainRepository {
        return MainImageRepositoryImpl(downloadFileFromURL, eventBus, event)
    }

    @Provides
    @Named("MainVideoRepositoryImpl")
    @Singleton
    fun providesMainVideoRepositoryImpl(downloadFileFromURL: DownloadFileFromURL, eventBus: EventBus, event: MainEvent): MainRepository {
        return MainVideoRepositoryImpl(downloadFileFromURL, eventBus, event)
    }

}
