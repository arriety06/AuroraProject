package com.Arriety.repositorys;

import com.Arriety.models.GiftEvent;
import com.Arriety.models.GiftEventTransaction;

public interface IGiftEventTransactionRepository {
    GiftEventTransaction findGiftEventTransaction(int playerId, int giftCodeId);
    public boolean createNewTransaction(int playerId, int giftCodeId, String code);
}
