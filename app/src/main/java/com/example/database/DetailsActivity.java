package com.example.database;

import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.os.Bundle;
import android.text.Html;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class DetailsActivity extends AppCompatActivity {

    TextView person_name_id, person_age_id, person_address_id, person_city_id, person_zip_id;
    Bundle bundle;
    DatabaseHelper dbHelper;
    PersonalDetails details;
    Button edit_btn;
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
        edit_btn = findViewById(R.id.button_edit);
        dbHelper = new DatabaseHelper(DetailsActivity.this);

        edit_btn.setOnClickListener(view -> {
            Intent intent = new Intent(DetailsActivity.this, EditActivity.class);
            bundle.putParcelable("Details", details);
            intent.putExtras(bundle);
            startActivityForResult(intent, 0);
        });
        details = new PersonalDetails();
        fillPersonalData();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK) {
            fillPersonalData();
        }
    }

    public boolean onCreatePanelMenu(int featureId, @NonNull Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_details, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if(id == R.id.delete) {
            AlertDialog.Builder builder = new AlertDialog.Builder(DetailsActivity.this);
            builder.setTitle("Warning");
            builder.setMessage("Do you really wish to delete this person??");
            builder.setPositiveButton("OK", (dialog, which) -> {dbHelper.deletePerson(bundle.getString("id"));
                setResult(RESULT_OK);
                finish();});
            builder.setNegativeButton("CANCEL", (dialog, which) -> dialog.cancel());
            builder.show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    void fillPersonalData() {
        String[] args = {String.valueOf(bundle.get("id"))};
        Cursor cursor = dbHelper.findInDatabase(null,"_id =?", args);
        Resources res = getResources();
        if(cursor.getCount() != 0) {
            cursor.moveToNext();
            details.name = cursor.getString(1);
            details.gender = cursor.getString(2);
            details.age = cursor.getInt(3);
            details.address = cursor.getString(4);
            details.city = cursor.getString(5);
            details.zip = cursor.getInt(6);


            person_name_id.setText(cursor.getString(1));
            person_age_id.setText(Html.fromHtml(res.getString(R.string.age, String.valueOf(details.age))));
            person_address_id.setText(Html.fromHtml(res.getString(R.string.address, details.address)));
            person_city_id.setText(Html.fromHtml(res.getString(R.string.city, details.city)));
            person_zip_id.setText(Html.fromHtml(res.getString(R.string.zip, String.valueOf(details.zip))));
        }
    }
}