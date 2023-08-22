package com.example.database;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class EditActivity extends AppCompatActivity {

    EditText person_name_id, person_age_id, person_address_id, person_city_id, person_zip_id;
    Bundle bundle;
    Button save, back;
    PersonalDetails details;

    DatabaseHelper dbHelper;
    boolean changed = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        dbHelper = new DatabaseHelper(this);

        save = findViewById(R.id.button_save);
        back = findViewById(R.id.button_cancel);
        person_name_id = findViewById(R.id.details_name);
        person_age_id = findViewById(R.id.details_age);
        person_address_id = findViewById(R.id.details_address);
        person_city_id = findViewById(R.id.details_city);
        person_zip_id = findViewById(R.id.details_zip);

        bundle = getIntent().getExtras();
        details = bundle.getParcelable("Details");

        person_name_id.setText(details.name, TextView.BufferType.EDITABLE);
        person_age_id.setText(String.valueOf(details.age), TextView.BufferType.EDITABLE);
        person_address_id.setText(details.address, TextView.BufferType.EDITABLE);
        person_city_id.setText(details.city, TextView.BufferType.EDITABLE);
        person_zip_id.setText(String.valueOf(details.zip), TextView.BufferType.EDITABLE);

        back.setOnClickListener(view -> {
            if(isChanged()) {
                AlertDialog.Builder builder = new AlertDialog.Builder(EditActivity.this);
                builder.setTitle("Warning");
                builder.setMessage("You have some unsaved changes. Do you wish to leave anyway?");
                builder.setPositiveButton("OK", (dialog, which) -> finish());
                builder.setNegativeButton("CANCEL", (dialog, which) -> dialog.cancel());
                builder.show();
            }
            else {
                setResult(RESULT_OK);
                finish();
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PersonalDetails newDetails = new PersonalDetails();
                newDetails.name = String.valueOf(person_name_id.getText());
                newDetails.age = Integer.parseInt(person_age_id.getText().toString());
                newDetails.address = String.valueOf(person_address_id.getText());
                newDetails.city = String.valueOf(person_city_id.getText());
                newDetails.zip = Integer.parseInt(person_zip_id.getText().toString());
                if(dbHelper.editPerson(bundle.getString("id"), newDetails)) {
                    Toast.makeText(EditActivity.this, "Edit saved!", Toast.LENGTH_SHORT).show();
                    details = newDetails;
                    changed = true;
                }
                else {
                    Toast.makeText(EditActivity.this, "Edit failed!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    boolean isChanged() {
        if (!String.valueOf(person_name_id.getText()).equals(details.name)) return true;
        if (Integer.parseInt(person_age_id.getText().toString()) != details.age) return true;
        if (!String.valueOf(person_address_id.getText()).equals(details.address)) return true;
        if (!String.valueOf(person_city_id.getText()).equals(details.city)) return true;
        if (Integer.parseInt(person_zip_id.getText().toString()) != details.zip) return true;
        return false;
    }
}