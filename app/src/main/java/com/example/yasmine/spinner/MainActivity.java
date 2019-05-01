package com.example.yasmine.spinner;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.yasmine.spinner.Interface.IFirebaseLoadDone;
import com.example.yasmine.spinner.Model.School;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements IFirebaseLoadDone {

    Spinner spinner;
    TextView textView;
    boolean isFirstTimeClick=true;

    DatabaseReference schoolRef;
    IFirebaseLoadDone iFirebaseLoadDone;
    List<School> schools;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = findViewById(R.id.text);
        spinner = findViewById(R.id.spinner);
        spinner.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                //Fixed first time click
                if(!isFirstTimeClick)
                {
                    School school = schools.get(i);

                }
                else
                    isFirstTimeClick = false;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });



        //init Db
        schoolRef = FirebaseDatabase.getInstance().getReference("Schools");

        //init interface
        iFirebaseLoadDone = this;

        //Get data
        schoolRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange (@NonNull DataSnapshot dataSnapshot){
                List<School> schools = new ArrayList<School>();
                for (DataSnapshot ds : dataSnapshot.getChildren())
                {
                    schools.add(ds.getValue(School.class));
                }
                iFirebaseLoadDone.onFirebaseLoadSucess(schools);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                iFirebaseLoadDone.onFirebaseLoadFailed(databaseError.getMessage());
            }
        });

    }


    @Override
    public void onFirebaseLoadSucess(List<School> schoolList) {
        schools = schoolList;
        //Get all names
        List<String> name_list = new ArrayList<String>();
        for(School school:schoolList)
            name_list.add(school.getName());
        //create Adapter and set for spinner
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,android.R.layout.simple_expandable_list_item_1,name_list);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }

    @Override
    public void onFirebaseLoadFailed(String Message) {

    }
}
