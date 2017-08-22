package Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ahmedsayed.todo_studygroup.R;
import com.example.ahmedsayed.todo_studygroup.TodoDetails;

import java.util.ArrayList;

import Model.myTodo;
import data.DBHandler;
import es.dmoral.toasty.Toasty;

public class TODO_Recycler_Adapter extends RecyclerView.Adapter<TODO_Recycler_Adapter.myViewHolder> {

    private ArrayList<myTodo> todos = new ArrayList<>();
    private int layoutResource;
    private Context mycontext;

    public class myViewHolder extends RecyclerView.ViewHolder {
        TextView todo_text;
        ImageView delete;
        RelativeLayout root;

        public myViewHolder(View itemView) {
            super(itemView);
            todo_text = (TextView) itemView.findViewById(R.id.todo_row_tv);
            delete = (ImageView) itemView.findViewById(R.id.delete);
            root = (RelativeLayout) itemView.findViewById(R.id.rowid);

        }
    }

    public TODO_Recycler_Adapter(Context context, int resource, ArrayList<myTodo> todoArrayList) {
        mycontext = context;
        layoutResource = resource;
        todos = todoArrayList;
    }

    @Override
    public myViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(layoutResource, parent, false);
        return new myViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TODO_Recycler_Adapter.myViewHolder holder, final int position) {

        final myTodo todo = todos.get(position);

        holder.todo_text.setText(todo.getTitle());
        holder.root.setOnClickListener(new View.OnClickListener() {
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
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DBHandler dbh = new DBHandler(mycontext);
                int id = todo.getMy_id();

                dbh.delete_wish(id);
               // Toast.makeText(mycontext, "Deleted !", Toast.LENGTH_SHORT).show();
                Toasty.success(mycontext,"Deleted ! ",Toast.LENGTH_LONG,true).show();
                todos.remove(position);
                notifyDataSetChanged();

            }
        });
    }

    @Override
    public int getItemCount() {
        return todos.size();
    }
}
