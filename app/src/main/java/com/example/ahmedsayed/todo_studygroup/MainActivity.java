package com.example.ahmedsayed.todo_studygroup;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import java.util.ArrayList;

import Adapter.TODO_Recycler_Adapter;
import Model.myTodo;
import data.DBHandler;
import es.dmoral.toasty.Toasty;

public class MainActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private ImageView add;
    private DBHandler dbh;
    private ArrayList<myTodo> dbTodo;
    // private ListView listView;
    private RecyclerView recyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toasty.Config.getInstance().setSuccessColor(ContextCompat.getColor(this, R.color.colorAccent)).apply();

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        add = (ImageView) toolbar.findViewById(R.id.addTodo);
        setSupportActionBar(toolbar);
        // listView = (ListView) findViewById(R.id.Todo_list);
        recyclerView = (RecyclerView) findViewById(R.id.Todo_list);
        getdata();

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, Add_Todo.class);
                startActivity(i);
            }
        });


    }

    private void getdata() {

        dbTodo = new ArrayList<>();
        dbh = new DBHandler(getApplicationContext());

        ArrayList<myTodo> getTODO = dbh.get_todos();

        for (int i = 0; i < getTODO.size(); i++) {

            String title = getTODO.get(i).getTitle().toString();
            String content = getTODO.get(i).getContent().toString();
            String date = getTODO.get(i).getRecordDate().toString();
            int id = getTODO.get(i).getMy_id();

            myTodo temp_todo = new myTodo();
            temp_todo.setTitle(title);
            temp_todo.setContent(content);
            temp_todo.setRecordDate(date);
            temp_todo.setMy_id(id);

            dbTodo.add(temp_todo);
        }

        dbh.close();

        TODO_Recycler_Adapter adapter = new TODO_Recycler_Adapter(this, R.layout.todo_row, dbTodo);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        Log.v("test", "inAdapter");
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        getdata();
    }
}
