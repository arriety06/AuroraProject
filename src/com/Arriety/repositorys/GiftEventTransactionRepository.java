package com.Arriety.repositorys;

import com.Arriety.models.GiftEvent;
import com.Arriety.models.GiftEventTransaction;
import com.girlkun.database.GirlkunDB;
import com.girlkun.result.GirlkunResultSet;

public class GiftEventTransactionRepository implements IGiftEventTransactionRepository {
    private String TABLE = "gift_event_transaction";

    @Override
    public GiftEventTransaction findGiftEventTransaction(int playerId, int giftCodeId) {
        try {
            GirlkunResultSet rs = GirlkunDB.executeQuery(String.format("select * from %s where gift_code = %d and player = %d  limit 1", TABLE, giftCodeId, playerId));
            if (rs.first()) {
                GiftEventTransaction giftEventTransaction = new GiftEventTransaction();
                giftEventTransaction.setId(rs.getInt("id"));
                giftEventTransaction.setGiftCodeId(giftCodeId);
                giftEventTransaction.setPlayerId(playerId);
                giftEventTransaction.setGiftCode(rs.getString("code"));
                return giftEventTransaction;
            }
            return null;
        } catch (Exception e) {
            GirlkunDB.close();
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public boolean createNewTransaction(int playerId, int giftCodeId, String code) {
        String createSql = String.format("insert into %s (player, gift_code, code) values ()", TABLE);
        try {
            GirlkunDB.executeUpdate(createSql, playerId, giftCodeId, code);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
