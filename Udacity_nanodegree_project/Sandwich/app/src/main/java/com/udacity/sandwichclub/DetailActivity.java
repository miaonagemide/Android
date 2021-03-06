package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;
import com.udacity.sandwichclub.utils.StringUtilis;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;
    private static final String LOG_TAG="DetailActivity";
    private ImageView ingredientsIv;
    private TextView originT;
    private TextView desctiptionT;
    private TextView ingredientT;
    private TextView alsoKnowAsT;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);

        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            closeOnError();
            return;
        }
        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];

        Sandwich sandwich = JsonUtils.parseSandwichJson(json);
        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }

        populateUI(sandwich);


        Picasso.get()
                .load(sandwich.getImage())
                .placeholder(R.drawable.sad_cat)
                .error(R.drawable.mad_cat)
                .into(ingredientsIv);

        setTitle(sandwich.getMainName());

    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI(Sandwich sandwich) {
        ingredientsIv= findViewById(R.id.image_iv);

        desctiptionT = findViewById(R.id.description_tv);
        desctiptionT.setText(sandwich.getDescription());
        originT = findViewById(R.id.origin_tv);
        originT.setText(sandwich.getPlaceOfOrigin());
        ingredientT = findViewById(R.id.ingredients_tv);
        ingredientT.setText(StringUtilis.sanitize(sandwich.getIngredients().toString()));
        alsoKnowAsT = findViewById(R.id.also_known_tv);
        alsoKnowAsT.setText(StringUtilis.sanitize(sandwich.getAlsoKnownAs().toString()));
    }
}
