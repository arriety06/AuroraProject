package com.Arriety.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class AccountEntity {
    private int userId;
    private boolean isAdmin;
    private long lastTimeLogout;
    private long lastTimeLogin;
    private boolean active;
    private int thoiVang;
    private int vnd;
    private int tongnap;
    private int pointshare;
    private double bdPlayer;
    private boolean isBan;
}
