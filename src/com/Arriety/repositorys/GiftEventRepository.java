package com.Arriety.repositorys;

import com.Arriety.models.GiftEvent;
import com.girlkun.database.GirlkunDB;
import com.girlkun.models.player.Gift;
import com.girlkun.result.GirlkunResultSet;

import java.sql.Connection;

public class GiftEventRepository implements IGiftEventRepository {
    private String TABLE = "gift_event";


    @Override
    public GiftEvent findGiftEventByCode(String code) {
        try {
            GirlkunResultSet girlkunResultSet = GirlkunDB.executeQuery(String.format("select * from %s where code = \'%s\' limit 1", TABLE, code));
            System.out.println(girlkunResultSet);
            if (girlkunResultSet.first()) {
                GiftEvent giftEvent = new GiftEvent();
                giftEvent.setId(girlkunResultSet.getInt("id"));
                giftEvent.setGiftCode(girlkunResultSet.getString("code"));
                giftEvent.setGiftBody(girlkunResultSet.getString("content"));
                java.sql.Timestamp timestamp = girlkunResultSet.getTimestamp("expired_date");
                giftEvent.setExpiredDate(timestamp.getTime());
                giftEvent.setEnabled(girlkunResultSet.getInt("is_enabled") == 1);
                giftEvent.setRemainCode(girlkunResultSet.getInt("remain"));
                return giftEvent;
            }
            return null;
        } catch (Exception e) {
            GirlkunDB.close();
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public boolean updateRemainCode(GiftEvent giftEvent) {
        int updateValue = Math.max(giftEvent.getRemainCode() - 1, 0);
        String sqlUpdate = String.format("update %s set remain = %d where id = %d", TABLE, updateValue, giftEvent.getId());
        try {
            GirlkunDB.executeUpdate(sqlUpdate);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
