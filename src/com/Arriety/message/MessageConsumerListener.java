/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.Arriety.message;

/**
 *
 * @author Arriety
 */
import org.json.JSONObject;

public interface MessageConsumerListener {
    void onMessage(JSONObject delivery);
}

