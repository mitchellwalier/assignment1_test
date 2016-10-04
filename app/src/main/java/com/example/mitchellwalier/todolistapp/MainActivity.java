package com.example.mitchellwalier.todolistapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import java.io.FileOutputStream;
import java.util.ArrayList;


public class MainActivity extends Activity {

    /*TO DO LIST:
    Save file by using Outputstream
    Retrieve the text file on the start of the app, parse it, and put it into the ListView
     */

    private EditText title;
    private EditText desc;
    private ArrayList<String> taskInfo;
    private ArrayAdapter adapter;
    //private SimpleCursorAdapter myAdapter;
    //private ArrayList<String> titleArray;
    //private ArrayList<String> descArray;

    public void addTask(View view){

        // Get text from fields
        String temp = title.getText().toString();
        title.setText("");
        String temp2 = desc.getText().toString();
        desc.setText("");

        // Put the things into one string and seperate it with a new line
        temp = temp + "\n" + temp2;
        taskInfo.add(temp);


        //titleArray.add(temp);
        //descArray.add(temp2);

        adapter.notifyDataSetChanged();

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        System.out.println("We created our app");

        try {

            taskInfo = savedInstanceState.getStringArrayList("Tasks");
            System.out.println("Lets get the list");

            // Tried to set retrieve the two arrays for SimpleCursor Adapter
            //descArray = savedInstanceState.getStringArrayList("descArray");
            //titleArray = savedInstanceState.getStringArrayList("titleArray");
        }
        catch (Exception e) {

            if (taskInfo == null) {
                taskInfo = new ArrayList<>();
                System.out.println("We created a brand new array from 0");
            }

            // Tried to set up the two arrays for the SimpleCursor Adapter
            /*
            if(titleArray == null){
                titleArray = new ArrayList<>();
            }
            if(descArray == null){
                descArray = new ArrayList<>();
            } */
        }

        // For debugging
        System.out.println(taskInfo + "1");

        title = (EditText) findViewById(R.id.taskTitle);
        desc = (EditText) findViewById(R.id.taskDescription);

        // Set adapter to list view
        ListView taskList = (ListView) findViewById(R.id.taskList);
        adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, taskInfo);
        taskList.setAdapter(adapter);

        // Attemped adapter to put the subdescription
        //myAdapter = new SimpleCursorAdapter(this, android.R.layout.simple_list_item_1,null,titleArray, descArray,0);


        // Set on click listener to delete the Task
        taskList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {

                new AlertDialog.Builder(MainActivity.this)
                        .setIcon(android.R.drawable.ic_dialog_alert).setMessage("Have you completed this task?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                taskInfo.remove(position);
                                adapter.notifyDataSetChanged();

                            }
                        })
                        .setNegativeButton("No", null)
                        .show();

                return true;
            }
        });

    }


    @Override
    public void onSaveInstanceState(Bundle savedValues) {
        super.onSaveInstanceState(savedValues);

        // Save the values in the array
        savedValues.putStringArrayList("Tasks", taskInfo);

        // Debugging Code
        System.out.println("We saved the thing to the bundle");
        System.out.println(taskInfo + "2");
    }


    @Override
    public void onRestoreInstanceState(Bundle savedValues) {
        super.onRestoreInstanceState(savedValues);

        // Retrieve the values
        taskInfo = savedValues.getStringArrayList("Tasks");
        adapter.notifyDataSetChanged();

        // Debugging code
        System.out.println("We took the thing out of the bundle");
        System.out.println(taskInfo + "3");

    }




}
