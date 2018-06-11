package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

import java.util.List;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;

    private Sandwich mSandwich;

    private TextView mAlsoKnownAs, mAlsoKnownAsLabel;
    private TextView mIngredients, mIngredientsLabel;
    private TextView mPlaceOfOrigin, mPlaceOfOriginLabel;
    private TextView mDescription, mDescriptionLabel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ImageView ingredientsIv = findViewById(R.id.image_iv);

        mAlsoKnownAs = findViewById(R.id.also_known_tv);
        mAlsoKnownAsLabel = findViewById(R.id.also_known_label);
        mIngredients = findViewById(R.id.ingredients_tv);
        mIngredientsLabel = findViewById(R.id.ingredients_label);
        mPlaceOfOrigin = findViewById(R.id.origin_tv);
        mPlaceOfOriginLabel = findViewById(R.id.origin_label);
        mDescription = findViewById(R.id.description_tv);
        mDescriptionLabel = findViewById(R.id.description_label);

        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        int position = 0;
        if (intent != null) {
            position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        }
        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            closeOnError();
            return;
        }

        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];
        mSandwich = JsonUtils.parseSandwichJson(json);
        if (mSandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }

        populateUI();
        Picasso.with(this)
                .load(mSandwich.getImage())
                .into(ingredientsIv);

        setTitle(mSandwich.getMainName());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI() {
        // alsoKnownAs
        List<String> alsoKnownAs = mSandwich.getAlsoKnownAs();
        if (alsoKnownAs != null && !alsoKnownAs.isEmpty()) {
            showView(mAlsoKnownAs);
            showView(mAlsoKnownAsLabel);
            mAlsoKnownAs.setText(joinStringsInList(alsoKnownAs));
        }

        // ingredients
        List<String> ingredients = mSandwich.getIngredients();
        if (ingredients != null && !ingredients.isEmpty()) {
            showView(mIngredients);
            showView(mIngredientsLabel);
            mIngredients.setText(joinStringsInList(ingredients));
        }

        // placeOfOrigin
        String placeOfOrigin = mSandwich.getPlaceOfOrigin();
        if (!TextUtils.isEmpty(placeOfOrigin)) {
            showView(mPlaceOfOrigin);
            showView(mPlaceOfOriginLabel);
            mPlaceOfOrigin.setText(placeOfOrigin);
        }

        // description
        String description = mSandwich.getDescription();
        if (!TextUtils.isEmpty(description)) {
            showView(mDescription);
            showView(mDescriptionLabel);
            mDescription.setText(description);
        }
    }

    /**
     * Helper method to show view
     */
    private void showView(View view) {
        view.setVisibility(View.VISIBLE);
    }

    /**
     * Helper Method to join Strings in a List with ","
     */
    private String joinStringsInList(List<String> list) {
        return TextUtils.join(", ", list);
    }
}
