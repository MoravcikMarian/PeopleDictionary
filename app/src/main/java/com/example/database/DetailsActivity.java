package com.example.database;

import android.content.res.Resources;
import android.database.Cursor;
import android.os.Bundle;
import android.text.Html;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class DetailsActivity extends AppCompatActivity {

    TextView person_name_id, person_age_id, person_address_id, person_city_id, person_zip_id;
    Bundle bundle;
    DatabaseHelper dbHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        bundle = getIntent().getExtras();
        person_name_id = findViewById(R.id.details_name);
        person_age_id = findViewById(R.id.details_age);
        person_address_id = findViewById(R.id.details_address);
        person_city_id = findViewById(R.id.details_city);
        person_zip_id = findViewById(R.id.details_zip);
        dbHelper = new DatabaseHelper(DetailsActivity.this);

        String[] args = {String.valueOf(bundle.get("id"))};
        Cursor cursor = dbHelper.findInDatabase(null,"_id =?", args);
        Resources res = getResources();
        if(cursor.getCount() != 0) {
            cursor.moveToNext();
            person_name_id.setText(cursor.getString(1));
            person_age_id.setText(Html.fromHtml(res.getString(R.string.age, cursor.getString(3))));
            person_address_id.setText(Html.fromHtml(res.getString(R.string.address, cursor.getString(4))));
            person_city_id.setText(Html.fromHtml(res.getString(R.string.city, cursor.getString(5))));
            person_zip_id.setText(Html.fromHtml(res.getString(R.string.zip, cursor.getString(6))));
        }
    }
}