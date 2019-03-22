package com.example.tugascatatan;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    DBHelper dbHelper;
    ArrayAdapter<String> mAdapter;
    ListView listTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbHelper=new DBHelper(this);
        listTask=(ListView)findViewById(R.id.listCatatan);

        loadTaskList();
    }

    private void loadTaskList() {
        ArrayList<String> taskList=dbHelper.getTaskList();
        if(mAdapter==null) {
            mAdapter = new ArrayAdapter<String>(this, R.layout.baris, R.id.task_title, taskList);
            listTask.setAdapter(mAdapter);
        }
        else {
            mAdapter.clear();
            mAdapter.addAll(taskList);
            mAdapter.notifyDataSetChanged();


        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu,menu);

        Drawable icon=menu.getItem(0).getIcon();
        icon.mutate();
        icon.setColorFilter(getResources().getColor(android.R.color.white), PorterDuff.Mode.SRC_IN);
        return super.onCreateOptionsMenu(menu);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case R.id.action_add_task:
                final EditText taskEditText=new EditText(this);
                AlertDialog dialog=new AlertDialog.Builder(this)
                        .setTitle("Tambah Catatan")
                        .setMessage("Ketikan Catatanmu ")
                        .setView(taskEditText)
                        .setPositiveButton("Tambah",new DialogInterface.OnClickListener(){
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String task=String.valueOf(taskEditText.getText());
                                dbHelper.insertNewCatatan(task);
                                loadTaskList();

                            }
                        })
                        .setNegativeButton("Batal",null)
                        .create();
                dialog.show();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void deleteCatatan(View view){
        TextView taskTextView= findViewById(R.id.task_title);
        String Catatan=String.valueOf(taskTextView.getText());
        dbHelper.deleteCatatan(Catatan);
        loadTaskList();
    }



}