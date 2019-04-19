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
import android.widget.Toast;

import java.util.ArrayList;

public class NewEventActivity extends Fragment {

    Button mButton;

    EditText title;
    EditText date;
    EditText startTime;
    EditText endTime;
    EditText location;
    EditText link;

    public View onCreateView(@NonNull LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
       return layoutInflater.inflate(R.layout.activity_new_event,viewGroup,false);
    }

    @Override
    public void onStart(){
        super.onStart();
        mButton = (Button)getView().findViewById(R.id.createEvent);

        mButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view) {

                Event event = new Event();

                title = (EditText)getView().findViewById(R.id.editText);
                date = (EditText)getView().findViewById(R.id.editText2);
                startTime = (EditText)getView().findViewById(R.id.editText3);
                endTime = (EditText)getView().findViewById(R.id.editText4);
                location = (EditText)getView().findViewById(R.id.editText6);
                link = (EditText)getView().findViewById(R.id.editText7);

                try {
                    event.setTitle(title.getText().toString());
                    event.setRawStart(date.getText().toString() + ", "+ startTime.getText().toString());
                    event.setRawEnd(date.getText().toString() + ", " + startTime.getText().toString());
                    event.setLink(link.getText().toString());
                    event.setLocation(location.getText().toString());

                    ScheduleActivity.populateListView(event);

                    Fragment newAct = new ScheduleActivity();
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, newAct).commit();
                }
                catch(Exception e) {
                    Toast.makeText(getActivity(), "Error, field not filled out.", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}
