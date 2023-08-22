package com.example.database;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class AddActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    Spinner spinner;
    Button add_button, cancel_button;
    EditText person_name_id, person_age_id, person_address_id, person_city_id, person_zip_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        spinner = (Spinner) findViewById(R.id.gender);
        person_name_id = findViewById(R.id.add_name_value);
        person_age_id = findViewById(R.id.add_age_value);
        person_address_id = findViewById(R.id.add_address_value);
        person_city_id = findViewById(R.id.add_city_value);
        person_zip_id = findViewById(R.id.add_zip_value);



        add_button = (Button)findViewById(R.id.add_btn_add);
        cancel_button = (Button)findViewById(R.id.add_btn_back);

        add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PersonalDetails newDetails = new PersonalDetails();
                newDetails.name = String.valueOf(person_name_id.getText());
                newDetails.gender = String.valueOf(spinner.getSelectedItem());
                newDetails.age = Integer.parseInt(person_age_id.getText().toString());
                newDetails.address = String.valueOf(person_address_id.getText());
                newDetails.city = String.valueOf(person_city_id.getText());
                newDetails.zip = Integer.parseInt(person_zip_id.getText().toString());
                DatabaseHelper dbHelper = new DatabaseHelper(AddActivity.this);
                if(dbHelper.addPerson(newDetails)) {
                    Toast.makeText(AddActivity.this, "Insertion passed!", Toast.LENGTH_SHORT).show();
                    setResult(RESULT_OK);
                }
                else {
                    Toast.makeText(AddActivity.this, "Insertion failed!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        cancel_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        initializeSpinner();

    }
    void initializeSpinner() {
        spinner.setOnItemSelectedListener(this);

        List<String> categories = new ArrayList<String>();
        categories.add("man");
        categories.add("woman");

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories);

        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(dataAdapter);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
    }
}