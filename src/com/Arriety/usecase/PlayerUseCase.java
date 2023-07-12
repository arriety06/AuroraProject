package com.Arriety.usecase;

import com.Arriety.entity.PlayerEntity;
import com.Arriety.repositorys.PlayerRepository;
import com.Arriety.repositorys.RedisRepository;

public class PlayerUseCase {

    public RedisRepository redisRepository;
    public PlayerRepository playerRepository;

    public PlayerUseCase(RedisRepository redisRepository, PlayerRepository playerRepository) {
        this.redisRepository = redisRepository;
        this.playerRepository = playerRepository;
    }

    public PlayerEntity fetchPlayerIfNeeded(int accountId) {
        PlayerEntity playerInCache = redisRepository.findPlayerWithAccountId(accountId);
        PlayerEntity playerInDb = playerRepository.findPlayerWithAccountId(accountId);

        if (playerInCache == null) {
//            System.out.println("Use Player in Database 1\n");
            return playerInDb;
        }

        if (playerInDb != null) {
            if (playerInDb.updateTime >= playerInCache.updateTime) {
//                System.out.println("Use Player in Database 2\n");
                return playerInDb;
            } else {
                //Need to save entity to database
                playerRepository.updatePlayer(playerInCache);
//                System.out.println("Use Player In Cache\n");
                return playerInCache;
            }
        }
        return null;
    }

}
