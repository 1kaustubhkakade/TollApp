package com.example.rohan.tollapp_admin;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class TollRegistration extends AppCompatActivity {
    EditText Email,Password,Confirm_Password,Location,UserId;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private static final String TAG = "UserRegistration";
    Button Button_Sign_Up ;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    public  boolean flag;
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
        setContentView(R.layout.activity_toll_registration);
        Email=(EditText)findViewById(R.id.Email_ID);
        Password=(EditText)findViewById(R.id.Password);
        Confirm_Password=(EditText)findViewById(R.id.Confirm_Password);
        Location=(EditText)findViewById(R.id.toll_location);
        UserId=(EditText)findViewById(R.id.tollname);
        Button_Sign_Up =(Button)findViewById(R.id.Button_Sign_Up);
        mAuth = FirebaseAuth.getInstance();
        firebaseDatabase=FirebaseDatabase.getInstance();
        databaseReference=firebaseDatabase.getReference();
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
        Button_Sign_Up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                validate(Email, Password, Confirm_Password,UserId,Location);
                if(flag)
                {
                    String email =Email.getText().toString();
                    String password =Password.getText().toString();
                    String userid =UserId.getText().toString();
                    String location =Location.getText().toString();

                    signUp(email,password,userid,location);

                    //FireBase
                }

            }
        });



    }
    public  void validate(EditText email,EditText password,EditText confirm_Password,EditText userId,EditText location)
    {
        flag=true;
        if (email.getText().toString().isEmpty())
        {
            Email.setError("Please Enter email");
            flag = false;
        }
        if (email.getText().toString().indexOf('@') == -1)
        {
            Email.setError("Please Enter Valid Email id");
            flag=false;
        }
        if (email.getText().toString().indexOf(".")== -1)
        {
            Email.setError("Please Enter valid email id");
            flag=false;
        }
        if (userId.getText().toString().isEmpty())
        {
            UserId.setError("Please Enter Userid");
            flag = false;
        }
        if(password.getText().toString().isEmpty())
        {
            Password.setError("Please Enter Password");
            flag=false;
        }
        if(Password.getText().toString().length()<8)
        {
            Password.setError("Please Enter Strong Password");
            flag=false;
        }
        if(confirm_Password.getText().toString().isEmpty())
        {
            Password.setError("Please Enter Confirm Password");
            flag=false;
        }
        if(!password.getText().toString().equals(confirm_Password.getText().toString()))
        {
            Confirm_Password.setError("Password Mismatch");
            flag=false;
        }
        if(location.getText().toString().isEmpty())
        {
            Location.setError("Please Enter valid location");
            flag=false;
        }
    }
    private void signUp(final String email, final String password, final String userid, final String location)
    {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG, "createUserWithEmail:onComplete:" + task.isSuccessful());

                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (task.isSuccessful()) {
                            Toast.makeText(TollRegistration.this, "Successful .",
                                    Toast.LENGTH_SHORT).show();
                            DatabaseReference childDBR = databaseReference.child(location);
                            childDBR.child("UserID").setValue(userid);
                            childDBR.child("Password").setValue(password);
                            childDBR.child("Email").setValue(email);
                            childDBR.child("Mobile no").setValue(location);
                            FirebaseUser user=FirebaseAuth.getInstance().getCurrentUser();
                            user.sendEmailVerification();

                        }

                        // ...
                    }
                });
    }


}
