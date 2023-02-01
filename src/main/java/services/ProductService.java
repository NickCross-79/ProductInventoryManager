package main.java.services;

import java.util.HashMap;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import com.google.gson.Gson;
import main.java.controllers.AppController;

public class ProductService {
    public static void updateItems( String id, Boolean isOnSale, Object... fields){
        HashMap<String, Object> updates = new HashMap<>();

        updates.put("name", fields[0]);
        updates.put("colour", fields[1]);
        updates.put("description", fields[2]);
        updates.put("price", fields[3]);
        updates.put("startingDateAvailable", fields[4]);
        updates.put("type", fields[5]);
        updates.put("stock", fields[6]);
        updates.put("manufacturer", fields[7]);

        if(isOnSale){
            updates.put("isOnSale", true);
            updates.put("endingDateAvailable", fields[8].toString());
        }
        else {
            updates.put("isOnSale", false);
            updates.put("endingDateAvailable", null);
        }

        try {
            JSONObject updatesJson = (JSONObject) new JSONParser().parse(new Gson().toJson(updates));
            Server.sendPatchRequest(updatesJson, id);
        } catch (ParseException e1) {
            e1.printStackTrace();
        }

        AppController.products = new LoadProducts().loadProducts();
        AppController.pages = AppController.products.size();
    }
}
