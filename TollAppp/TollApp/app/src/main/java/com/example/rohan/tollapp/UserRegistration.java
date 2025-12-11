package com.example.rohan.tollapp;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import dmax.dialog.SpotsDialog;

import static java.security.AccessController.getContext;

public class UserRegistration extends AppCompatActivity implements AdapterView.OnItemSelectedListener{
   public EditText Email_Id, Password, Confirm_Password, Mobile_Number,firstName,lastName,Vechile_no;
    public Button Button_Sign_Up;
    ProgressDialog pg;
    public  boolean flag;
    Spinner spinner;
    String mobile;
    String s;

    LinearLayout linearLayout;
    String[] type={"Car","Truck","LCV","Bus","Axle"};
    ArrayAdapter arrayAdapter;
    SpotsDialog spotsDialog;
    public  FirebaseAuth mAuth;
    public FirebaseAuth.AuthStateListener mAuthListener;
    private static final String TAG = "UserRegistration";
    private FirebaseDatabase firebaseDatabase;

 private DatabaseReference databaseReference;

    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_registration);

        getSupportActionBar().hide();
        linearLayout=(LinearLayout)findViewById(R.id.linearLayout);
        firebaseDatabase=FirebaseDatabase.getInstance();
        databaseReference=firebaseDatabase.getReference();

        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                } else {
                    // User is signed out
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                }
                // ...
            }
        };

        Email_Id = (EditText) findViewById(R.id.Email_ID);
        firstName = (EditText) findViewById(R.id.firstName);
        lastName = (EditText) findViewById(R.id.lastName);
        Password = (EditText) findViewById(R.id.Password);
        Confirm_Password = (EditText) findViewById(R.id.Confirm_Password);
        Mobile_Number = (EditText) findViewById(R.id.Mobile_Number);
        Vechile_no=(EditText)findViewById(R.id.vehicle_number);
       // validate(Email_Id, Password, Confirm_Password,Mobile_Number);
        //if(Email_Id.getText().toString().isEmpty() || Password.getText().toString().isEmpty() || )
        spinner=(Spinner)findViewById(R.id.spinner);
        arrayAdapter=new ArrayAdapter<String>(UserRegistration.this,android.R.layout.simple_spinner_dropdown_item,type);
        spinner.setAdapter(arrayAdapter);
        spinner.setOnItemSelectedListener(this);
        Button_Sign_Up = (Button) findViewById(R.id.Button_Sign_Up);

        //Button_Sign_Up.setBackgroundColor(Color.GRAY);
    //        final boolean buttonenable=validate1(Email_Id, Password, Confirm_Password,Mobile_Number);

        Button_Sign_Up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                validate(Email_Id, Password, Confirm_Password,Mobile_Number,firstName,lastName);
                spotsDialog = new SpotsDialog(UserRegistration.this, R.style.Custom);
                spotsDialog.show();
                if(flag)
                {


                    String email =Email_Id.getText().toString();
                    String password =Password.getText().toString();
                    String cpass =Confirm_Password.getText().toString();
                    String Mobileno =Mobile_Number.getText().toString();
                    String name =firstName.getText().toString();
                    String lastname =lastName.getText().toString();
                    Toast.makeText(UserRegistration.this, s, Toast.LENGTH_SHORT).show();
                    signUp(email,password,cpass,Mobileno,name,lastname,s);

                    //FireBase
                }
                else
                {
                    spotsDialog.dismiss();
                }


            }
        });

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        s=parent.getItemAtPosition(position).toString();
        Toast.makeText(UserRegistration.this, s, Toast.LENGTH_SHORT).show();
        //Vechile_no.setEnabled(true);
        if (Vechile_no.getText().toString().isEmpty())
        {
            Vechile_no.setError("Enter vehicle number");
            flag=false;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        Snackbar snackbar=Snackbar.make(linearLayout,"Please select Vehicle type",Snackbar.LENGTH_INDEFINITE);
        snackbar.show();
        flag=false;

    }


    public void validate(EditText eid, EditText Pass, EditText conPass, EditText mb,EditText firstName1,EditText lastName1) {
        //Button_Sign_Up.setEnabled(false);
        flag=true;
        if (eid.getText().toString().isEmpty())
        {
            Email_Id.setError("Please Enter email");
            flag = false;
        }
        if (eid.getText().toString().indexOf('@') == -1)
        {
            Email_Id.setError("Please Enter Valid Email id");
            flag=false;
        }
        if (eid.getText().toString().indexOf(".")== -1)
        {
            Email_Id.setError("Please Enter valid email id");
            flag=false;
        }
        if (firstName1.getText().toString().isEmpty())
        {
            firstName.setError("Please Enter Name");
            flag = false;
        }
        if (lastName1.getText().toString().isEmpty())
        {
            lastName.setError("Please Enter last Name");
            flag = false;
        }
        if(Pass.getText().toString().isEmpty())
        {
            Password.setError("Please Enter Password");
            flag=false;
        }
        if(Pass.getText().toString().length()<8)
        {
            Password.setError("Please Enter Strong Password");
            flag=false;
        }
        if(conPass.getText().toString().isEmpty())
        {
            Password.setError("Please Enter Confirm Password");
            flag=false;
        }
        if(!Pass.getText().toString().equals(conPass.getText().toString()))
        {
            Confirm_Password.setError("Password Mismatch");
            flag=false;
        }
        if(mb.getText().toString().length()!=10)
        {
            Mobile_Number.setError("{Please Enter 10 Digit Mobile No.");
            flag=false;
        }

        //Button_Sign_Up.setEnabled(true);
    }

    private void signUp(final String email, final String password, String cpass, final String Mobileno, final String name, final String lastname, final String s)
    {


        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Toast.makeText(UserRegistration.this, "createUserWithEmail:onComplete:" + task.isSuccessful(), Toast.LENGTH_SHORT).show();
                        // progressBar.setVisibility(View.GONE);
                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (task.isSuccessful()) {
                            spotsDialog.dismiss();
                            mobile=Mobile_Number.getText().toString();
                            String fullname=name.concat(lastname);
                            DatabaseReference childDBR = databaseReference.child(mobile);
                            childDBR.child("Name").setValue(fullname);
                            childDBR.child("Password").setValue(password);
                            childDBR.child("Email").setValue(email);
                            childDBR.child("Mobile no").setValue(Mobileno);
                            childDBR.child("Vehcile type").setValue(s);
                            childDBR.child("Vehcile number").setValue(Vechile_no.getText().toString());
                            FirebaseUser user=FirebaseAuth.getInstance().getCurrentUser();
                            user.sendEmailVerification();

                            Toast.makeText(UserRegistration.this, "User registered", Toast.LENGTH_SHORT).show();
                            Intent i=new Intent(UserRegistration.this,LoginActivity.class);
                            startActivity(i);
                        }
                        else
                        {
                            spotsDialog.dismiss();
                            Toast.makeText(UserRegistration.this, ""+task.getException(), Toast.LENGTH_SHORT).show();
                           // Snackbar snackbar= Snackbar.make(linearLayout,"User Registration done Please Register your mail",Snackbar.LENGTH_LONG);
                            //snackbar.show();
                        }

                    }
                });

    }

}