package com.example.travelapp.utils;

import android.content.Context;
import com.example.travelapp.R;
import com.example.travelapp.models.Destination;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class DataLoader {

    public static List<Destination> loadDestinations(Context context) {
        List<Destination> list = new ArrayList<>();

        try {
            // Đọc file JSON từ res/raw
            InputStream is = context.getResources().openRawResource(R.raw.destinations);
            byte[] buffer = new byte[is.available()];
            is.read(buffer);
            is.close();

            String json = new String(buffer, "UTF-8");

            // Parse JSON
            JSONArray array = new JSONArray(json);
            for (int i = 0; i < array.length(); i++) {
                JSONObject obj = array.getJSONObject(i);

                String name = obj.getString("name");
                String location = obj.getString("location");
                String imageName = obj.getString("image");
                String description = obj.optString("description", ""); // Mô tả (nếu có)

                int imageResId = context.getResources()
                        .getIdentifier(imageName, "drawable", context.getPackageName());

                list.add(new Destination(name, location, imageResId, description));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }
}
