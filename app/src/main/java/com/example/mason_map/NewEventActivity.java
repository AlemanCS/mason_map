package com.example.mason_map;

import android.support.v4.app.Fragment;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Button;
import java.util.ArrayList;

public class NewEventActivity extends Fragment {

    Button mButton;
    EditText title;
    EditText date;
    EditText startTime;
    EditText endTime;
    EditText description;
    EditText location;
    Event newEvent;

    public View onCreateView(@NonNull LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
       return layoutInflater.inflate(R.layout.activity_new_event,viewGroup,false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        mButton = (Button)view.findViewById(R.id.createEvent);

        mButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view) {

                title = (EditText)view.findViewById(R.id.editText);
                date = (EditText)view.findViewById(R.id.editText2);
                startTime = (EditText)view.findViewById(R.id.editText3);
                endTime = (EditText)view.findViewById(R.id.editText4);
                description = (EditText)view.findViewById(R.id.editText5);
                location = (EditText)view.findViewById(R.id.editText6);
                ArrayList<String> custom;
                newEvent = new Event(title.getText().toString(), "", description.getText().toString(), custom = new ArrayList<String>(), startTime.getText().toString(), endTime.getText().toString(), location.getText().toString());
                ScheduleActivity.populateListView(newEvent);

            }
        });

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}
