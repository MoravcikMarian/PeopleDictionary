package com.example.database;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class SortingDialog extends AlertDialog{

    ArrayList selectedItems;
    //boolean[] checked;
    AlertDialog.Builder builder;
    Context context;
    MainActivity activity;
    int choice;
    protected SortingDialog(Context context) {
        super(context);
        this.context = context;
        activity = (MainActivity)context;
        selectedItems = new ArrayList<>();
        builder = new AlertDialog.Builder(context);
        //checked = new boolean[5];
    }

    public AlertDialog showDialog() {
        builder.setTitle("Sorter")
                .setSingleChoiceItems(R.array.personal_details, -1, new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        choice = which;
                    }
                })
                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {

                        sort(choice);
                    }
                })
                .setNegativeButton(R.string.resetAll, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                       activity.customAdapter.updateAdapter(activity.peopleList);
                    }
                });

        return builder.show();
    }

    @SuppressLint("NewApi")
    private void sort(int which) {
        Comparator<PersonalDetails> compareKey = null;/* = Comparator.comparing((PersonalDetails p)->p.gender);*/

        switch (which) {
            case 0:
                compareKey = Comparator.comparing((PersonalDetails p)->p.name);
                break;
            case 1:
                compareKey = Comparator.comparing((PersonalDetails p)->p.gender);
                break;
            case 2:
                compareKey = Comparator.comparing((PersonalDetails p)->p.age);
                break;
            case 3:
                compareKey = Comparator.comparing((PersonalDetails p)->p.address);
                break;
            case 4:
                compareKey = Comparator.comparing((PersonalDetails p)->p.city);
                break;
            default:
                break;
        }

        List<PersonalDetails> sortedPeople = activity.peopleList.stream()
                .sorted(compareKey)
                .collect(Collectors.toList());

        activity.customAdapter.updateAdapter((ArrayList<PersonalDetails>) sortedPeople);
    }
}
