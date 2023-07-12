package com.Arriety.repositorys;

import com.Arriety.entity.PlayerEntity;

import com.girlkun.utils.Logger;
import com.google.gson.Gson;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import java.util.List;

public class RedisRepository {

    private JedisPool pool;

    private static String PLAYER_KEY = "players";

    public RedisRepository(JedisPool jedis) {
        this.pool = jedis;
    }
    Gson gson = new Gson();

    public void savePlayerToCache(List<PlayerEntity> entities) {
        try (Jedis jedis = pool.getResource()) {
            for (PlayerEntity entity : entities) {
                String gsonJson = gson.toJson(entity);
                jedis.hset(PLAYER_KEY, String.valueOf(entity.accountId), gsonJson);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            Logger.error("Đã Lưu Vào Bộ Nhớ: " + ex.getLocalizedMessage());
        }
    }

    public PlayerEntity findPlayerWithAccountId(int accountId) {
        try (Jedis jedis = pool.getResource()) {
            String json = jedis.hget(PLAYER_KEY, String.valueOf(accountId));
            if (json == null || json.isEmpty()) {
                return null;
            }

            Gson gson = new Gson();
            return gson.fromJson(json, PlayerEntity.class);
        } catch (Exception ex) {
            ex.printStackTrace();
            Logger.error("Find Account Người Chơi Cũ: " + ex.getLocalizedMessage());
            return null;
        }
    }
}
