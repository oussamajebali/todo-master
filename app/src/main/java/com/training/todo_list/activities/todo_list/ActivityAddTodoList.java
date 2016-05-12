package com.training.todo_list.activities.todo_list;

import android.app.DatePickerDialog;
import android.app.DialogFragment;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.github.danielnilsson9.colorpickerview.dialog.ColorPickerDialogFragment;
import com.training.todo_list.R;
import com.training.todo_list.model.managers.TodoManager;
import com.training.todo_list.model.managers.TodoTypeManager;
import com.training.todo_list.model.models.Todo;
import com.training.todo_list.model.models.TodoType;

import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

public class ActivityAddTodoList extends AppCompatActivity implements ColorPickerDialogFragment.ColorPickerDialogListener {


    TodoManager mTodoManager;
    TodoTypeManager mTodoTypeManager;
    Todo mTodo;
    TodoType mTodoType;
    EditText mDescription, mType;
    DatePicker mDate;
    Button mAdd, mColor;
    int mIcolor;
    private static final int DIALOG_ID = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_todo_list);

        mDescription = (EditText) findViewById(R.id.itemAddDescription);
        mType = (EditText) findViewById(R.id.itemAddType);
        mColor = (Button) findViewById(R.id.itemAddColor);
        mDate = (DatePicker) findViewById(R.id.itemAddDate);
        mAdd = (Button) findViewById(R.id.itemAddButton);


        mColor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickColorPickerDialog();
            }
        });


        mAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (TextUtils.isEmpty(mType.getText().toString())
                        || TextUtils.isEmpty(mDescription.getText().toString())
                        ) {
                    Toast.makeText(ActivityAddTodoList.this, "Please fill all the blanks", Toast.LENGTH_LONG)
                            .show();
                } else {

                    int day = mDate.getDayOfMonth();
                    int month = mDate.getMonth();
                    int year = mDate.getYear();
                    final Calendar calendar = Calendar.getInstance();
                    calendar.set(year, month, day);

                    mTodoManager = new TodoManager();
                    mTodoTypeManager = new TodoTypeManager();

                    mTodoType = new TodoType(
                            mType.getText().toString(),
                            colorToHexString(mIcolor),
                            UUID.randomUUID()
                    );

                    mTodo = new Todo(
                            mDescription.getText().toString(),
                            calendar.getTime(),
                            mTodoType.id(),
                            false,
                            UUID.randomUUID()
                    );

                    mTodoTypeManager.save(mTodoType);
                    mTodoManager.save(mTodo);

                    Intent pIntent = new Intent(ActivityAddTodoList.this, ActivityTodoList.class);
                    startActivity(pIntent);
                    finish();
                }
            }
        });


    }


    public void onClickColorPickerDialog() {

        ColorPickerDialogFragment f = ColorPickerDialogFragment
                .newInstance(DIALOG_ID, null, null, Color.BLACK, true);

        f.setStyle(DialogFragment.STYLE_NORMAL, R.style.LightPickerDialogTheme);
        f.show(getFragmentManager(), "d");

    }

    private static String colorToHexString(int color) {
        return String.format("#%06X", 0xFFFFFFFF & color);
    }

    @Override
    public void onColorSelected(int dialogId, int color) {
        mIcolor = color;
        mColor.setBackgroundColor(color);

    }

    @Override
    public void onDialogDismissed(int dialogId) {

    }


    @Override
    public void onBackPressed() {

        Intent pIntent = new Intent(ActivityAddTodoList.this, ActivityTodoList.class);
        startActivity(pIntent);
        finish();

    }
}
