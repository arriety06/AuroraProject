/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */


package com.Arriety.redis;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 *
 * @Stole By Arriety <3
 */


public class RedisConnectionManager {

    private static RedisConnectionManager instance = null;

    JedisPool jedisPool;

    private RedisConnectionManager() {
        final JedisPoolConfig poolConfig = new JedisPoolConfig();
        poolConfig.setMaxTotal(128);
        jedisPool = new JedisPool(poolConfig);
    }

    public JedisPool getPool() {
        return jedisPool;
    }

    public static RedisConnectionManager getInstance() {
        if (instance == null) {
            synchronized (RedisConnectionManager.class) {
                if (instance == null) {
                    instance = new RedisConnectionManager();
                }
            }
        }
        return instance;
    }
}