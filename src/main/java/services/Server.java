package main.java.services;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import org.json.simple.JSONObject;

public class Server{

    private static final HttpClient client = HttpClient.newBuilder()
                        .version(HttpClient.Version.HTTP_2)
                        .build();
    private static String root;

    public static void connect(String url){
        root = url;
    }

    public static Object sendGetRequest(String url){
        HttpResponse<?> response = null;
        try {
            HttpRequest request = HttpRequest.newBuilder()
                                .uri(URI.create(root+url))
                                .GET()
                                .header("Content-Type", "application/json")
                                .build();
            response = client.send(request, BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }

        return response.body().toString();
    }

    public static void sendPatchRequest(JSONObject updates, String id){
        try {
        HttpRequest request = HttpRequest.newBuilder()
                                        .uri(URI.create(root+id))
                                        .method("PATCH", HttpRequest.BodyPublishers.ofString(updates.toString()))
                                        .header("Content-Type", "application/json")
                                        .build();
        
        client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        
    }

    public static void sendDeleteRequest(String id){
        try {
            HttpRequest request = HttpRequest.newBuilder()
                                .uri(URI.create(root+id))
                                .DELETE()
                                .build();
            client.send(request, BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}