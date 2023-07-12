/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.Arriety.furry;

import com.girlkun.database.GirlkunDB;
import com.girlkun.models.item.Item.ItemOption;
import com.girlkun.result.GirlkunResultSet;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

/**
 *
 * @author by Arriety
 */
public class FurryManager {

    private static FurryManager instance;

    public static FurryManager gI() {
        if (instance == null) {
            instance = new FurryManager();
        }
        return instance;
    }

    public long lastTimeUpdate;

    public String[] tabName = {"1", "2", "3", "4", ""};

    public List<ItemFurry> listItem = new ArrayList<>();

    public void save() {
        try (Connection con = GirlkunDB.getConnection();) {
            Statement s = con.createStatement();
            s.execute("TRUNCATE shop_furry");
            for (ItemFurry it : this.listItem) {
                if (it != null) {
                    s.execute(String.format("INSERT INTO `shop_furry`(`id`, `player_id`, `tab`, `item_id`, `gold`, `gem`, `quantity`, `itemOption`, `isUpTop`, `isBuy`) VALUES ('%s','%s','%s','%s','%s','%s','%s','%s','%s','%s')",
                            it.id, it.player_sell, it.tab, it.itemId, it.goldSell, it.gemSell, it.quantity, JSONValue.toJSONString(it.options).equals("null") ? "[]" : JSONValue.toJSONString(it.options), it.isUpTop, it.isBuy ? 1 : 0));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}