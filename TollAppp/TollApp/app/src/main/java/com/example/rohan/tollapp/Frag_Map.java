package com.example.rohan.tollapp;


import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.annotation.StringDef;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class Frag_Map extends Fragment implements  AdapterView.OnItemSelectedListener{

    Spinner spinner;
    String[] type = {"Car", "Truck", "LCV", "Bus", "Axle"};
    ArrayAdapter arrayAdapter;
    Button edit_details;
    Button Change;
    EditText vehicle_no;
    String Type, Vehicle_No;
    boolean flag = true;
    Button Next;
    RadioButton Trip1, Trip2, Trip3;
    RadioGroup R2;
    TextView tx1,tx2;
    int choice2, num;
    String journey;
    public String mb;
    Button Details;
    EditText Mb;
    LinearLayout sub_Layout1, sub_Layout2,sub_Layout3,sub_Layout4;
    TextView Detail_Vehicle_No, Detail_Vehicle_Type;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)throws com.google.firebase.database.DatabaseException {

        final View view = inflater.inflate(R.layout.frag_map, container, false);

        spinner = (Spinner) view.findViewById(R.id.spinner);
        Change = (Button) view.findViewById(R.id.change);
        edit_details = (Button) view.findViewById(R.id.Change_Default);
        Trip1 = (RadioButton) view.findViewById(R.id.single);
        Trip2 = (RadioButton) view.findViewById(R.id.retrn);
        Trip3 = (RadioButton) view.findViewById(R.id.monthpass);
        Mb = (EditText) view.findViewById(R.id.mobile);
        tx1=(TextView)view.findViewById(R.id.Detail_Vehicle_Type);
        tx2=(TextView)view.findViewById(R.id.Detail_Vehicle_No);
        Details = (Button) view.findViewById(R.id.view_Details);
        sub_Layout1 = (LinearLayout) view.findViewById(R.id.sub_layout1);
        sub_Layout2 = (LinearLayout) view.findViewById(R.id.sub_layout2);
        sub_Layout3 = (LinearLayout) view.findViewById(R.id.sub_layout3);
        Detail_Vehicle_No = (TextView) view.findViewById(R.id.Detail_Vehicle_No);
        Detail_Vehicle_Type = (TextView) view.findViewById(R.id.Detail_Vehicle_Type);
        Details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validate(Mb);
                if (flag) {
                    Toast.makeText(getActivity(), "Onclick of button view details", Toast.LENGTH_SHORT).show();
                    mb = Mb.getText().toString();
                    sub_Layout1.setVisibility(View.VISIBLE);
                    edit_details.setVisibility(View.VISIBLE);
                    sub_Layout2.setVisibility(View.VISIBLE);
                    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
                    DatabaseReference databaseReference = firebaseDatabase.getReference();
                    DatabaseReference childDatabaseReference = databaseReference.child(mb);
                    childDatabaseReference.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            Type = dataSnapshot.child("Vehcile type").getValue().toString();
                            Vehicle_No = dataSnapshot.child("Vehcile number").getValue().toString();
                            tx1.setText(Type);
                            tx2.setText(Vehicle_No);
                            Toast.makeText(getActivity(), "Values Set", Toast.LENGTH_SHORT).show();

                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            Toast.makeText(getActivity(), "Error at Frag_Map 89", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }


            private void validate(EditText mb)
            {
                if(mb.getText().toString().isEmpty())
                {
                    Mb.setError("Please enter mobile number");
                    flag=false;
                }
                if(mb.getText().toString().length()!=10)
                {
                    Mb.setError("Please enter only 10 digit mobile number");
                    flag=false;
                }

            }



        });

       edit_details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "EDIT CALLEd", Toast.LENGTH_SHORT).show();

               sub_Layout3.setVisibility(View.VISIBLE);
               Vehicle_No=vehicle_no.getText().toString();
                tx1.setText(Type);
                tx2.setText(Vehicle_No);
            }
        });

        Change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Vehicle_No = vehicle_no.getText().toString();
                tx1.setText(Type);
                tx2.setText(Vehicle_No);
            }
        });


                                           R2 = (RadioGroup) view.findViewById(R.id.RG2);
                                           arrayAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, type);
                                           spinner.setAdapter(arrayAdapter);
                                           spinner.setOnItemSelectedListener((AdapterView.OnItemSelectedListener) this);
                                           //Change = (Button) view.findViewById(R.id.change);
                                           vehicle_no = (EditText) view.findViewById(R.id.vehicle_no);
                                           Next = (Button) view.findViewById(R.id.Next);
/*                                           edit_details.setOnClickListener(new View.OnClickListener() {
                                               @Override
                                               public void onClick(View v) {
                                                   spinner.setVisibility(View.VISIBLE);
                                                   vehicle_no.setVisibility(View.VISIBLE);
                                               }
                                           });*/
                                           R2.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                                               @Override
                                               public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                                                   choice2 = R2.indexOfChild(view.findViewById(checkedId));


                                                   switch (checkedId) {
                                                       case R.id.single:
                                                           num = 1;
//                        Toast.makeText(getContext(), "Single Journey", Toast.LENGTH_SHORT).show();
                                                           journey = "Single";
                                                           break;
                                                       case R.id.retrn:
                                                           num = 2;
//                        Toast.makeText(getContext(), "Return journey", Toast.LENGTH_SHORT).show();
                                                           journey = "Return";
                                                           break;
                                                       case R.id.monthpass:
                                                           num = 3;
//                        Toast.makeText(getContext(), "Monthly pass", Toast.LENGTH_SHORT).show();
                                                           journey = "Monthly Pass";
                                                           break;
                                                   }

                                               }
                                           });


        Next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    Intent i = new Intent(getActivity(), MapsActivity.class);
                    i.putExtra("Journey", journey);
                    i.putExtra("Vehicle", Type);
                    i.putExtra("Vehicle_No", Vehicle_No);
                    i.putExtra("Mobile", mb);

                Toast.makeText(getActivity(), ""+journey, Toast.LENGTH_SHORT).show();
                Toast.makeText(getActivity(), ""+Type, Toast.LENGTH_SHORT).show();
                Toast.makeText(getActivity(), ""+Vehicle_No, Toast.LENGTH_SHORT).show();
                Toast.makeText(getActivity(), ""+mb, Toast.LENGTH_SHORT).show();

                    getActivity().startActivity(i);

            }
        });





       /* R1.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId)
            {
                choice1=R1.indexOfChild(v.findViewById(checkedId));


                switch(checkedId)
                {
                    case R.id.Car:
                        alpha="a";
//                        Toast.makeText(getContext(), "Car is selected", Toast.LENGTH_SHORT).show();
                        vehicle="Car";
                        break;
                    case R.id.Lcv:
                        alpha="b";
//                        Toast.makeText(getContext(), "Lcv is selected", Toast.LENGTH_SHORT).show();
                        vehicle="LCV";
                        break;
                    case R.id.Bus:
                        alpha="c";
//                        Toast.makeText(getContext(), "Truck is selected", Toast.LENGTH_SHORT).show();
                        vehicle="Truck";
                        break;
                    case R.id.Three:
                        alpha="d";
//                        Toast.makeText(getContext(), "Bus is selected", Toast.LENGTH_SHORT).show();
                        vehicle="Three";
                        break;
                }

          }


                                       }
*/
        return view;
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Type = parent.getItemAtPosition(position).toString();
        Toast.makeText(getActivity(), Type, Toast.LENGTH_SHORT).show();
        //Vechile_no.setEnabled(true);
        if (vehicle_no.getText().toString().isEmpty()) {
            vehicle_no.setError("Enter vehicle number");
            flag = false;
        } else {
            flag = true;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        flag = true;

    }




}
