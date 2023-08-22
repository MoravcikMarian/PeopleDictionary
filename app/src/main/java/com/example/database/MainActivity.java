package com.example.database;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
//import android.widget.SearchView;
import androidx.appcompat.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity{

    RecyclerView recyclerView;
    DatabaseHelper dbHelper;
    CustomAdapter customAdapter;
    ArrayList<PersonalDetails> peopleList;
    FloatingActionButton add_btn;
    SortingDialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);
        add_btn = findViewById(R.id.button_add);
        add_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddActivity.class);
                startActivityForResult(intent, 0);
            }
        });
        dialog = new SortingDialog(this);
        dbHelper = new DatabaseHelper(this);
        peopleList = new ArrayList<>();

        readDatabaseEntries();

        customAdapter = new CustomAdapter(MainActivity.this, this, peopleList);
        recyclerView.setAdapter(customAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK){
            recreate();
        }
    }

    @Override
    public boolean onCreatePanelMenu(int featureId, @NonNull Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        final MenuItem searchItem = menu.findItem(R.id.search);
        final SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                //filter(newText);
                return false;
            }
        });

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if(id == R.id.deleteAll){
            Toast.makeText(this, "Delete All", Toast.LENGTH_SHORT).show();
            dbHelper.deleteAllPeople();
            recreate();
            return true;
        }
        else if(id == R.id.generate10){
            generateNewEntries(10);
            return true;
        }
        else if(id == R.id.generate50){
            generateNewEntries(50);
            return true;
        }
        else if(id == R.id.generate100){
            generateNewEntries(100);
            return true;
        }
        else if( id == R.id.filter){
            dialog.showDialog();
        }

        return super.onOptionsItemSelected(item);
    }

    private void readDatabaseEntries() {
        Cursor cursor;
        cursor = dbHelper.findInDatabase(null,null, null);
        while(cursor.moveToNext()) {
            PersonalDetails person = new PersonalDetails();
            person.id = cursor.getInt(0);
            person.name = cursor.getString(1);
            person.gender = cursor.getString(2);
            person.age = cursor.getInt(3);
            person.address = cursor.getString(4);
            person.city = cursor.getString(5);
            person.zip = cursor.getInt(6);
            peopleList.add(person);
        }
    }

    private void generateNewEntries(int numberOfGenerates) {
        for(int i = 0; i < numberOfGenerates; ++i) {
            dbHelper.addPerson(GeneratePerson.newPerson());
        }
        recreate();
    }

    private void filter(String text) {
        // creating a new array list to filter our data.
        ArrayList<PersonalDetails> filteredList = new ArrayList<PersonalDetails>();

        // running a for loop to compare elements.
        for (PersonalDetails item : peopleList) {
            // checking if the entered string matched with any item of our recycler view.
            if (item.contains(text.toLowerCase())) {
                // if the item is matched we are
                // adding it to our filtered list.
                filteredList.add(item);
            }
        }
        if (filteredList.isEmpty()) {
            // if no item is added in filtered list we are
            // displaying a toast message as no data found.
            Toast.makeText(this, "No Data Found..", Toast.LENGTH_SHORT).show();
        } else {
            // at last we are passing that filtered
            // list to our adapter class.
            customAdapter.updateAdapter(filteredList);
        }
    }
}