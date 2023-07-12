/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.Arriety.manager;

import com.Arriety.entity.PlayerEntity;
import com.Arriety.entity.PlayerFactory;
import com.arriety.injector.NroInjector;
import com.girlkun.server.Client;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Scheduler;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

import java.util.concurrent.*;
import java.util.List;
import java.util.stream.Collectors;
import com.girlkun.utils.Logger;

/**
 *
 * @Stole By Arriety
 */
public class AutoSaveManager {

    private static AutoSaveManager instance = null;

    private AutoSaveManager() {
        compositeDisposable = new CompositeDisposable();
    }

    ExecutorService poolAutoSave = Executors.newFixedThreadPool(3);

    public static synchronized AutoSaveManager getInstance() {
        if (instance == null) {
            instance = new AutoSaveManager();
        }
        return instance;
    }

    private CompositeDisposable compositeDisposable;

    public void autoSave() {
        Disposable subscribe = Observable.interval(120, 20, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.from(poolAutoSave))
                .subscribe(i -> {
                    try {
                        this.handleAutoSave();
                    } catch (Exception ex) {
                        Logger.error("Error occurred during auto save: " + ex.getLocalizedMessage() + "\n");
                    }
                }, throwable -> {
                    Logger.error("Not Save Data: " + throwable.getLocalizedMessage() + "\n");
                });
        compositeDisposable.add(subscribe);
    }

    private void handleAutoSave() {
        try {
            List<PlayerEntity> players = Client.gI().getPlayers().stream().map(PlayerFactory::createEntity).collect(Collectors.toList());
            if (players.isEmpty()) {
                Logger.error("No players found for auto save.\n");
                return;
            }
            NroInjector.getInstance().getRedisRepository().savePlayerToCache(players);
            Logger.error("Saved players: " + players.size() * 2 + "\n");
        } catch (Exception ex) {
            Logger.error("Failed to save players: " + ex.getLocalizedMessage() + "\n");
        }
    }

    private void dispose() {
        compositeDisposable.dispose();
        compositeDisposable = null;
    }

}
