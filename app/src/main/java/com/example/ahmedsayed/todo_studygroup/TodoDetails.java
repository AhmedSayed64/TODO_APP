package com.example.ahmedsayed.todo_studygroup;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class TodoDetails extends AppCompatActivity {

    private TextView title, date, content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo_details);

        title = (TextView) findViewById(R.id.details_titleId);
        date = (TextView) findViewById(R.id.details_DateId);
        content = (TextView) findViewById(R.id.details_contentId);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            title.setText(extras.getString("title"));
            date.setText("Created on : " + extras.getString("date"));
            content.setText("\"" + extras.getString("content") + "\"");

        }

    }
}
