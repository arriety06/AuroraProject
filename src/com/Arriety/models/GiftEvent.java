package com.Arriety.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class GiftEvent {
    private int id;
    private String giftCode;
    private String giftBody;
    private int maxGiftCode;
    private int remainCode;
    private long expiredDate;
    private boolean isEnabled;
}
