package com.example.database;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    DatabaseHelper dbHelper;
    ArrayList<String> person_id, persons_name, persons_gender;
    CustomAdapter customAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, DetailsActivity.class);
                startActivity(intent);
            }
        });

        dbHelper = new DatabaseHelper(this);
        person_id = new ArrayList<>();
        persons_name = new ArrayList<>();
        persons_gender = new ArrayList<>();

        if(dbHelper.isEmpty()) {
            initializeDatabase();
        }

        readDatabaseEntries();

        customAdapter = new CustomAdapter(MainActivity.this, person_id, persons_name, persons_gender);
        recyclerView.setAdapter(customAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
    }

    private void readDatabaseEntries() {
        Cursor cursor;
        String[] columns = {"_id", "persons_name", "persons_gender"};
        cursor = dbHelper.findInDatabase(columns,null, null);
        while(cursor.moveToNext()) {
            person_id.add(cursor.getString(0));
            persons_name.add(cursor.getString(1));
            persons_gender.add(cursor.getString(2));
        }
    }

    private void initializeDatabase() {
        for(int i = 0; i < 10; ++i) {
            dbHelper.addPerson(GeneratePerson.newPerson());
        }
    }
}