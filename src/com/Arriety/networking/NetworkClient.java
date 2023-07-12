/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.Arriety.networking;

import org.json.JSONObject;

//import java.net.http.HttpClient;
//import java.net.http.HttpRequest;
//import java.net.http.HttpResponse;

/**
 *
 * @Stole By Arriety
 */
enum ConfirmType {
    BAOTRI
}

public class NetworkClient {

    private static NetworkClient instance = null;

    private NetworkClient() {
    }

    // Static method
    // Static method to create instance of Singleton class
    public static synchronized NetworkClient getInstance() {
        if (instance == null) {
            instance = new NetworkClient();
        }

        return instance;
    }

    private String baseUrl;

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public void confirm(JSONObject payload) {
//        try {
//            String stringUrl = String.format("%s%s", baseUrl, "/notification/nrojavaconfirm");
//            HttpClient client = HttpClient.newHttpClient();
//            HttpRequest request = HttpRequest.newBuilder()
//                    .uri(URI.create(stringUrl))
//                    .headers("Content-Type", "application/json")
//                    .POST(HttpRequest.BodyPublishers.ofString(payload.toString()))
//                    .build();
//            HttpResponse<String> response = client.send(request,
//                    HttpResponse.BodyHandlers.ofString());
//            System.out.println(response.body());
//
//        } catch (IOException ex) {
//            Logger.getLogger(NetworkClient.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (InterruptedException ex) {
//            Logger.getLogger(NetworkClient.class.getName()).log(Level.SEVERE, null, ex);
//        }
    }
}
