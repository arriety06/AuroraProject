/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.Arriety.entity;

import lombok.*;

/**
 *
 * @Stole By Arriety
 */
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString

public class PlayerEntity {
    public long id;
    public int accountId;
    public String name;
    public short head;
    public byte gender;
    public boolean haveTennisSpaceShip;
    public int clanIdServer1;
    public int clanIdServer2;
    public String dataInventory;
    public String dataLocation;
    public String dataPoint;
    public String dataMagicTree;
    public String itemsBody;
    public String itemsBag;
    public String itemsBox;
    public String itemsBoxLuckyRound;
    public String friends;
    public String enemies;
    public String dataIntrinsic;
    public String dataItemTime;
    public String dataTask;
    public String dataMabuEgg;
    public String dataEggLinhThu;
    public String dataCharm;
    public String skills;
    public String skillsShortcut;
    public String pet;
    public String achievement;
    public String dataBlackBall;
    public String dataSideTask;
    public String datacard;
    public long createTime;
    public int violate;
    public int pointPvp;
    public String dataItemTimeSieuCap;
    public long updateTime;
}
