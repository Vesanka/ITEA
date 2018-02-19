package com.productions.itea.motivatedev;

import android.content.Intent;
import android.nfc.FormatException;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.google.android.gms.tasks.Task;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Calendar;
import java.util.Date;

public class TaskEditingActivity extends AppCompatActivity {//implements CompoundButton.OnCheckedChangeListener {

    static final String EXTRA_TASK_STATE = "Add";
//    String name;
    TextView title;
    EditText description;
    EditText date;
    myTask data;
    Button save;
    String task_state;
    String uid, taskUid;
    CheckBox checkBox;

    DatabaseReference tasksRef;


    RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_editing);

        // Instance of database
        FirebaseDatabase myDb = FirebaseDatabase.getInstance();
        tasksRef = myDb.getReference("curr_tasks");

        task_state = getIntent().getStringExtra(EXTRA_TASK_STATE);
        taskUid = getIntent().getStringExtra("task_uid");
        uid = getIntent().getStringExtra("uid");

        title = (TextView) findViewById(R.id.titleView);
        description = (EditText) findViewById(R.id.notesTextView);
        date = (EditText) findViewById(R.id.dateView);
        checkBox = (CheckBox) findViewById(R.id.important_check);
        loadData();
        setWriteble(true);

        save = (Button) findViewById(R.id.saveBtn);
        save.setOnClickListener(saveButtonClickListener);

    }

    View.OnClickListener saveButtonClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Toast.makeText(TaskEditingActivity.this,"Saved!",Toast.LENGTH_SHORT).show();

            data.task_name = title.getText().toString();
            data.description = description.getText().toString();
            data.date = date.getText().toString();
            data.photoUrl = "";
            data.important = checkBox.isChecked();

            DatabaseReference newTaskRef;
            if(taskUid==null){
            newTaskRef = tasksRef.child(uid).push();

            } else {
                newTaskRef = tasksRef.child(uid).child(taskUid);
            }
            newTaskRef.setValue(data);
            onBackPressed();
        }
    };

    private void loadData() {

        if (task_state.equals("Add")) {
            data = BaseEmul.defaultTask;
        } else if (taskUid != null){





//            DatabaseReference taskRef = tasksRef.child(uid).child(taskUid);
//            ValueEventListener event = new ValueEventListener() {
//                @Override
//                public void onDataChange(DataSnapshot dataSnapshot) {
//                    data = dataSnapshot.getValue(myTask.class);
//                }
//
//                @Override
//                public void onCancelled(DatabaseError databaseError) {
//                }
//            };
//            taskRef.addListenerForSingleValueEvent(event);

            //just for test, remove it
            data = new myTask("if it works", "it's is good", new Date(01,01,2001), null,true);

        }

        title.setText(data.task_name);
        description.setText(data.description);
        date.setText(data.date);
        checkBox.setChecked(data.important);
    }

    void setWriteble(boolean state) {
        title.setFocusable(state);
        title.setLongClickable(state);
        title.setCursorVisible(state);
        description.setFocusable(state);
        description.setLongClickable(state);
        description.setCursorVisible(state);
        date.setFocusable(state);
        date.setLongClickable(state);
        date.setCursorVisible(state);
    }

}
