package com.example.ahmedsayed.todo_studygroup;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import Model.myTodo;
import data.DBHandler;

public class Add_Todo extends AppCompatActivity {

    private EditText title, descriprion;
    Button add_Button;
    DBHandler dbh;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add__todo);

        title = (EditText) findViewById(R.id.title_editText);
        descriprion = (EditText) findViewById(R.id.descripiton);
        add_Button = (Button) findViewById(R.id.add_btn);
        dbh = new DBHandler(this);


        add_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (title.getText().toString().isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Please Enter Title", Toast.LENGTH_SHORT).show();
                } else
                    add_to_DB();
            }
        });


    }

    private void add_to_DB() {
        myTodo todo = new myTodo();
        todo.setTitle(title.getText().toString());
        todo.setContent(descriprion.getText().toString());

        dbh.add_Todo(todo);
        Log.v("add", "wish_added!");

        dbh.close();
        title.setText("");
        descriprion.setText("");
        finish();
    }
}
