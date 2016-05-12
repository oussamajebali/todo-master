package com.training.todo_list.activities.todo_list;

import android.app.DialogFragment;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
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

public class ActivityEditTodolist extends AppCompatActivity implements ColorPickerDialogFragment.ColorPickerDialogListener {


    TodoManager mTodoManager;
    TodoTypeManager mTodoTypeManager;
    Todo mTodo;
    TodoType mTodoType;
    EditText mDescription, mType;
    CheckBox mCheckBox;
    DatePicker mDate;
    Button mEdit, mColor;
    int mIcolor;
    private static final int DIALOG_ID = 0;
    ;
    String mSid;
    int mIindex;
    Boolean mDone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_todolist);

        mSid = getIntent().getStringExtra("itemId");
        mIindex = getIntent().getIntExtra("itemIndex", 0);
        mTodoManager = new TodoManager();
        mTodoTypeManager = new TodoTypeManager();


        mTodo = mTodoManager.todoFor(UUID.fromString(mSid));
        mTodoType = mTodoTypeManager.todoTypeFor(mTodo.idTodoType());

        mIcolor = Color.parseColor(mTodoType.color());
        mDescription = (EditText) findViewById(R.id.itemEditDescription);
        mType = (EditText) findViewById(R.id.itemEditType);
        mColor = (Button) findViewById(R.id.itemEditColor);
        mDate = (DatePicker) findViewById(R.id.itemEditDate);
        mEdit = (Button) findViewById(R.id.itemEditButton);
        mCheckBox = (CheckBox) findViewById(R.id.itemIsDone);

        if (mTodo.isDone()) {
            mCheckBox.setChecked(true);
            mDone = true;
        } else mDone = false;

        mColor.setBackgroundColor(Color.parseColor(mTodoType.color()));
        mDescription.setText(mTodo.description());
        mType.setText(mTodoType.name());
        Calendar pCalendar = Calendar.getInstance();
        pCalendar.setTimeInMillis(mTodo.timeCreation().getTime());
        mDate.updateDate(pCalendar.get(Calendar.YEAR), pCalendar.get(Calendar.MONTH), pCalendar.get(Calendar.DAY_OF_MONTH));

        mCheckBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mCheckBox.isChecked()) {
                    mDone = true;
                } else mDone = false;
            }
        });


        mColor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickColorPickerDialog();
            }
        });

        mEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (TextUtils.isEmpty(mType.getText().toString())
                        || TextUtils.isEmpty(mDescription.getText().toString())
                        ) {
                    Toast.makeText(ActivityEditTodolist.this, "Please fill all the blanks", Toast.LENGTH_LONG)
                            .show();
                } else {


                    int day = mDate.getDayOfMonth();
                    int month = mDate.getMonth();
                    int year = mDate.getYear();
                    final Calendar calendar = Calendar.getInstance();
                    calendar.set(year, month, day);

                    mTodoType = new TodoType(
                            mType.getText().toString(),
                            colorToHexString(mIcolor),
                            mTodoType.id()
                    );

                    mTodo = new Todo(
                            mDescription.getText().toString(),
                            calendar.getTime(),
                            mTodoType.id(),
                            mDone,
                            mTodo.id()
                    );

                    mTodoTypeManager.update(mIindex, mTodoType);
                    mTodoManager.update(mIindex, mTodo);

                    Intent pIntent = new Intent(ActivityEditTodolist.this, ActivityTodoList.class);
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

        Intent pIntent = new Intent(ActivityEditTodolist.this, ActivityTodoList.class);
        startActivity(pIntent);
        finish();

    }
}
