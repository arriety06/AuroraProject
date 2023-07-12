package com.Arriety.repositorys;

import com.Arriety.models.GiftEvent;

public interface IGiftEventRepository {
    GiftEvent findGiftEventByCode(String code);
    boolean updateRemainCode(GiftEvent giftEvent);
}
