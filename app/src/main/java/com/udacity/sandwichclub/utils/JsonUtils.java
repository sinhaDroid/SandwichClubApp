package com.udacity.sandwichclub.utils;

import android.text.TextUtils;
import android.util.Log;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    // Tag for the log messages
    public static final String TAG = JsonUtils.class.getSimpleName();

    // all keys from json
    private static final String NAME = "name";
    private static final String MAIN_NAME = "mainName";
    private static final String ALSO = "alsoKnownAs";
    private static final String PLACE_ORIGIN = "placeOfOrigin";
    private static final String DESCRIPTION = "description";
    private static final String IMAGE = "image";
    private static final String INGREDIENTS = "ingredients";

    public static Sandwich parseSandwichJson(String json) {

        // If the JSON string is empty or null, then return early.
        if (TextUtils.isEmpty(json)) {
            return null;
        }
        // Sandwich object to hold each sandwich details.
        Sandwich sandwich = new Sandwich();

        try {
            // Create a JSONObject from the JSON string
            JSONObject jsonObject = new JSONObject(json);

            // Extract object from name object
            JSONObject name = jsonObject.getJSONObject(NAME);

            // Extract value from key name
            String mainName = name.getString(MAIN_NAME);

            // Extract the JSONArray from alsoKnowsAs object
            JSONArray alsoKnownArray = name.getJSONArray(ALSO);

            // Convert the JSONArray to ArrayList
            List<String> alsoKnownAsList = new ArrayList<>();

            for (int i = 0; i < alsoKnownArray.length(); i++) {
                alsoKnownAsList.add(alsoKnownArray.getString(i));
            }

            // Extract value from key placeOfOrigin
            String placeOfOrigin = jsonObject.getString(PLACE_ORIGIN);

            // Extract value from key description
            String description = jsonObject.getString(DESCRIPTION);

            // Extract value from key image
            String image = jsonObject.getString(IMAGE);

            // Extract the JSONArray from ingredients object
            JSONArray ingredientsArray = jsonObject.getJSONArray(INGREDIENTS);

            // Convert the JSONArray to ArrayList
            List<String> ingredientsList = new ArrayList<>();

            for (int i = 0; i < ingredientsArray.length(); i++) {
                ingredientsList.add(ingredientsArray.getString(i));
            }

            // Create the sandwich object with the data from the json
            sandwich.setMainName(mainName);
            sandwich.setAlsoKnownAs(alsoKnownAsList);
            sandwich.setPlaceOfOrigin(placeOfOrigin);
            sandwich.setDescription(description);
            sandwich.setImage(image);
            sandwich.setIngredients(ingredientsList);

        } catch (JSONException e) {
            e.printStackTrace();

            // Log the error if comes
            Log.e(TAG, e.getLocalizedMessage());
        }

        return sandwich;
    }
}
