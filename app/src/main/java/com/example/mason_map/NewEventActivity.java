package com.example.mason_map;

import android.support.v4.app.Fragment;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Button;
import android.widget.Toast;
import android.widget.DatePicker;
import android.app.DatePickerDialog;
import android.text.InputType;
import java.util.Calendar;
import android.widget.TimePicker;
import android.app.TimePickerDialog;
import android.support.design.widget.TextInputLayout;


import java.util.ArrayList;

public class NewEventActivity extends Fragment {

    private static final String TAG = "NewEventActivity";

    Button mButton;
    EditText title;
    EditText date;
    EditText startTime;
    EditText endTime;
    EditText location;
    EditText link;
    DatePickerDialog datepicker;
    TimePickerDialog startTimepicker;
    TimePickerDialog endTimepicker;
    TextInputLayout inputLayoutTitle, inputLayoutStart, inputLayoutEnd, inputLayoutDate, inputLayoutLocation, inputLayoutLink ;



    public View onCreateView(@NonNull LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
       return layoutInflater.inflate(R.layout.activity_new_event,viewGroup,false);
    }

    @Override
    public void onStart(){
        super.onStart();
        inputLayoutTitle = (TextInputLayout)getView().findViewById(R.id.input_layout_title);
        title = (EditText)getView().findViewById(R.id.editText);
        inputLayoutDate = (TextInputLayout)getView().findViewById(R.id.input_layout_date);

        date = (EditText)getView().findViewById(R.id.editText2);
        date.setInputType(InputType.TYPE_NULL);
        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG,"Opening Calendar");
                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);
                // date picker dialog
                datepicker = new DatePickerDialog(getActivity(), R.style.ThemeDialog,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                date.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                            }
                        }, year, month, day);
                datepicker.show();
            }
        });

        inputLayoutStart = (TextInputLayout)getView().findViewById(R.id.input_layout_start);

        startTime = (EditText)getView().findViewById(R.id.editText3);
        startTime.setInputType(InputType.TYPE_NULL);
        startTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar cldr = Calendar.getInstance();
                int hour = cldr.get(Calendar.HOUR_OF_DAY);
                int minutes = cldr.get(Calendar.MINUTE);
                // time picker dialog
                startTimepicker = new TimePickerDialog(getActivity(),R.style.ThemeDialog,
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker tp, int sHour, int sMinute) {

                                int hour = sHour;
                                String time = "AM";
                                if(sHour>12){
                                    time = "PM";
                                    hour -= 12;
                                    if(hour == 0){
                                        hour = 12;
                                    }
                                }
                                startTime.setText(hour + ":" + sMinute + " " + time);
                            }
                        }, hour, minutes, false);
                startTimepicker.show();
            }
        });

        inputLayoutEnd = (TextInputLayout)getView().findViewById(R.id.input_layout_end);

        endTime = (EditText)getView().findViewById(R.id.editText4);
        endTime.setInputType(InputType.TYPE_NULL);
        endTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar cldr = Calendar.getInstance();
                int hour = cldr.get(Calendar.HOUR_OF_DAY);
                int minutes = cldr.get(Calendar.MINUTE);
                // time picker dialog
                endTimepicker = new TimePickerDialog(getActivity(), R.style.ThemeDialog,
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker tp, int sHour, int sMinute) {

                                int hour = sHour;
                                String time = "AM";
                                if(sHour>12){
                                    time = "PM";
                                    hour -= 12;
                                    if(hour == 0){
                                        hour = 12;
                                    }

                                }
                                endTime.setText(hour + ":" + sMinute + " " + time);
                            }
                        }, hour, minutes, false);
                endTimepicker.show();

            }
        });

        inputLayoutLocation = (TextInputLayout)getView().findViewById(R.id.input_layout_location);
        location = (EditText)getView().findViewById(R.id.editText6);

        inputLayoutLink = (TextInputLayout)getView().findViewById(R.id.input_layout_link);
        link = (EditText)getView().findViewById(R.id.editText7);

        mButton = (Button)getView().findViewById(R.id.createEvent);
        mButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view) {
                Log.d(TAG,"Submit button is listening");

                Event event = new Event();

                try {
                    //Sets each element within the GUI for this Event
                    event.setTitle(title.getText().toString());
                    event.setRawStart(date.getText().toString() + ", "+ startTime.getText().toString());
                    event.setRawEnd(date.getText().toString() + ", " + endTime.getText().toString());
                    event.setLink(link.getText().toString());
                    event.setLocation(location.getText().toString());

                    //Populate the schedule with this new event:
                    ScheduleActivity.populateListView(event);

                    //Goto the schedule:
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
