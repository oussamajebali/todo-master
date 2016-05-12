package com.training.todo_list.adapters;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.training.todo_list.R;
import com.training.todo_list.model.managers.TodoManager;
import com.training.todo_list.model.managers.TodoTypeManager;
import com.training.todo_list.model.models.Todo;
import com.training.todo_list.model.models.TodoType;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by Oussama on 11/05/2016.
 */
public class TodoListAdapter extends BaseAdapter {
    Context mContext;
    LayoutInflater mInflater;
    List<Todo> mTodos;
    List<TodoType> mTodoTypes;


    public TodoListAdapter(Context pContext, List<Todo> pTodos, List<TodoType> pTodoTypes) {
        mContext = pContext;
        mTodos = pTodos;
        mTodoTypes = pTodoTypes;
        mInflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }


    @Override
    public int getCount() {
        return mTodos.size();
    }

    @Override
    public Object getItem(int position) {
        return mTodos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder item;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.todo_list_item, null);
            item = new ViewHolder();
            item.mDescription = (TextView) convertView.findViewById(R.id.itemDescription);
            item.mDate = (TextView) convertView.findViewById(R.id.itemDate);
            item.mType = (TextView) convertView.findViewById(R.id.itemType);
            item.mIsDone = (ImageView) convertView.findViewById(R.id.itemIsDone);
            item.mRelativeLayout = (RelativeLayout) convertView.findViewById(R.id.itemLayout);
            convertView.setTag(item);
        } else {
            item = (ViewHolder) convertView.getTag();
        }
        Todo pTodo = mTodos.get(position);
        TodoType pTodoType = new TodoTypeManager().todoTypeFor(pTodo.idTodoType());
        item.mDescription.setText(pTodo.description());
        DateFormat dateFormat = android.text.format.DateFormat.getDateFormat(mContext);
        item.mDate.setText(dateFormat.format(pTodo.timeCreation()));
        if (pTodo.isDone()) {
            item.mIsDone.setImageResource(R.drawable.ic_event_available_black_24dp);
        } else {
            item.mIsDone.setImageResource(R.drawable.ic_event_busy_black_24dp);
        }


        try {

            item.mRelativeLayout.setBackgroundColor(Color.parseColor(pTodoType.color()));
            item.mType.setText(pTodoType.name());

        }
        catch (Exception e){
            Log.d("Exception",e.toString());
        }



        return convertView;
    }


    static class ViewHolder {
        TextView mDescription;
        TextView mDate;
        TextView mType;
        ImageView mIsDone;
        RelativeLayout mRelativeLayout;

    }
}
