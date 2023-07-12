package com.Arriety.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GiftEventTransaction {
    private int id;
    private int playerId;
    private int giftCodeId;
    private String giftCode;
}
