package com.example.ahmedsayed.todo_studygroup;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import Model.myTodo;
import data.DBHandler;


public class TODO_Adapter extends ArrayAdapter<myTodo> {

    private int layoutResource;
    private ArrayList<myTodo> myTodos = new ArrayList<>();
    private Context mycontext;

    public TODO_Adapter(@NonNull Context context, @LayoutRes int resource, @NonNull ArrayList<myTodo> data) {
        super(context, resource, data);
        mycontext = context;
        layoutResource = resource;
        myTodos = data;
    }

    @Override
    public int getCount() {
        return myTodos.size();
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        final Viewholder viewholder;
        View listview = convertView;
        final myTodo todo = getItem(position);
        if (listview == null) {
            viewholder = new Viewholder();
            listview = LayoutInflater.from(getContext()).inflate(layoutResource, parent, false);

            viewholder.todo_text = (TextView) listview.findViewById(R.id.todo_row_tv);
            viewholder.delete = (ImageView) listview.findViewById(R.id.delete);
            viewholder.root = (RelativeLayout) listview.findViewById(R.id.rowid);

            listview.setTag(viewholder);
        } else {
            viewholder = (Viewholder) listview.getTag();
        }

        viewholder.todo_text.setText(todo.getTitle());
        viewholder.root.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = todo.getTitle().toString();
                String content = todo.getContent().toString();
                String Date = todo.getRecordDate().toString();

                Intent i = new Intent(mycontext, TodoDetails.class);
                i.putExtra("title", title);
                i.putExtra("date", Date);
                i.putExtra("content", content);
                mycontext.startActivity(i);
            }
        });

        viewholder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DBHandler dbh = new DBHandler(mycontext);
                int id = todo.getMy_id();

                dbh.delete_wish(id);
                Toast.makeText(mycontext, "Deleted !", Toast.LENGTH_SHORT).show();
                myTodos.remove(position);
                notifyDataSetChanged();

            }
        });


        return listview;
    }

    private class Viewholder {
        TextView todo_text;
        ImageView delete;
        RelativeLayout root;
    }
}
