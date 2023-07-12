package com.arriety.injector;

import com.Arriety.repositorys.AccountRepository;
import com.Arriety.repositorys.PlayerRepository;
import com.Arriety.repositorys.RedisRepository;
import com.Arriety.usecase.PlayerUseCase;
import lombok.Getter;

/*
* @Stole By Arriety
*/
@Getter
public class NroInjector {
    private static NroInjector instance = null;

    private PlayerUseCase playerUseCase;
    private RedisRepository redisRepository;
    private PlayerRepository playerRepository;
    private AccountRepository accountRepository;

    private NroInjector() {

    }

    public static synchronized NroInjector getInstance() {
        if (instance == null) {
            instance = new NroInjector();
        }
        return instance;
    }

     public void injectPlayerUseCase(PlayerUseCase playerUseCase) {
        this.playerUseCase = playerUseCase;
     }

     public  void injectAccountRepository(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
     }

     public void injectRedisRepository(RedisRepository redisRepository) {
        this.redisRepository = redisRepository;
     }

     public void injectPlayerRepository(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
     }
}