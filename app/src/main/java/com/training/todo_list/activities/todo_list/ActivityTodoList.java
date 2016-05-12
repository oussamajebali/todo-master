package com.training.todo_list.activities.todo_list;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.training.todo_list.R;
import com.training.todo_list.adapters.TodoListAdapter;
import com.training.todo_list.model.managers.TodoManager;
import com.training.todo_list.model.managers.TodoTypeManager;
import com.training.todo_list.model.models.Todo;

import java.util.Date;
import java.util.UUID;

public class ActivityTodoList extends ListActivity {
    TodoManager mTodoManager;
    TodoTypeManager mTodoTypeManager;
    Todo mTodo;
    ListView mTodoList;
    TodoListAdapter mTodoListAdapter;
    int mIindex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lt_act_todo_list);
        mTodoList = (ListView) findViewById(android.R.id.list);
        mTodoManager = new TodoManager();
        mTodoTypeManager = new TodoTypeManager();

        mTodoListAdapter = new TodoListAdapter(ActivityTodoList.this, mTodoManager.all(), mTodoTypeManager.all());
        mTodoList.setAdapter(mTodoListAdapter);

        mTodoList.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {


                mIindex = mTodoManager.all().indexOf(mTodoListAdapter.getItem(position));
                Intent intent = new Intent(ActivityTodoList.this, ActivityEditTodolist.class);
                intent.putExtra("itemId", mTodoManager.all().get(position).id().toString());
                intent.putExtra("itemIndex", mIindex);
                startActivity(intent);
                finish();
            }
        });

    }


    public void askAddTodo(View pView) {
        Intent pIntent = new Intent(ActivityTodoList.this, ActivityAddTodoList.class);
        startActivity(pIntent);
        finish();
    }


    public void askSurprise(View pView) {
        AlertDialog.Builder tBuilder = new AlertDialog.Builder(this);
        tBuilder.setTitle(R.string.dialog_title_surprise);
        tBuilder.setMessage(R.string.dialog_message_surprise);
        tBuilder.setPositiveButton(R.string.confirm, null);
        tBuilder.show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mTodoListAdapter.notifyDataSetChanged();
    }


}
