package com.example.rohan.tollapp;

import android.app.ProgressDialog;
import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
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

import java.util.concurrent.Executor;

import dmax.dialog.SpotsDialog;


public class FragmentOne extends Fragment {
    private Button Button_Login;
    ProgressDialog pg;
    private FirebaseAuth mAuth;
    private EditText Email_ID, Password;
    TextView getForgot_Password;
    SpotsDialog spotsDialog;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private TextView  Verify_account, Forgot_Password;
    Button Textview_Create_Account;
    private static final String TAG = "SignupActivity";
    LinearLayout linear;


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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_one, container, false);
        linear = (LinearLayout) view.findViewById(R.id.linearLayout);
        pg = new ProgressDialog(getContext());
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
        getForgot_Password = (TextView) view.findViewById(R.id.forgotPass);
        getForgot_Password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = Email_ID.getText().toString();
                if (email.isEmpty()) {
                    Email_ID.setError("Email is empty");
                }
                FirebaseAuth auth = FirebaseAuth.getInstance();
                String emailAddress = email;

                try {


                    auth.sendPasswordResetEmail(emailAddress)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(getActivity(), "Reset link sent", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                } catch (Exception e) {
                    Toast.makeText(getActivity(), "" + e.getMessage(), Toast.LENGTH_SHORT).show();
                }

            }
        });
        pg.setMessage("Login in process");
        Textview_Create_Account = (Button) view.findViewById(R.id.Textview_Create_Account);
        Textview_Create_Account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getContext(), UserRegistration.class);
                startActivity(i);

            }
        });
        Email_ID = (EditText) view.findViewById(R.id.Email_ID);
        Password = (EditText) view.findViewById(R.id.Password);
        Button_Login = (Button) view.findViewById(R.id.Button_Login);
        Button_Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                spotsDialog = new SpotsDialog(getContext(), R.style.Custom);
                spotsDialog.show();

                String mb = Email_ID.getText().toString();
                String password = Password.getText().toString();
                boolean valid = validate(mb, password);
                if (valid == true) {

                    signIn(mb, password);
                    // signFirebase(mb,password);


                } else {
                    spotsDialog.dismiss();
                }
            }
        });

        // Inflate the layout for this fragment
        return view;
    }

    private boolean validate(String email, String password) {
        int flag = 1;
        if (Email_ID.getText().toString().isEmpty()) {
            Email_ID.setError("Enter Mobile No");
            flag = 0;
        } else if (Email_ID.getText().toString().indexOf('@') == -1) {
            Email_ID.setError("Enter valid Email id");
            flag = 0;
        }
        if (Password.getText().toString().isEmpty()) {
            Password.setError("Please enter password");
        }
        if (flag == 0) {
            return false;
        }
        if (flag == 1) {
            return true;
        }

        return true;
    }


    private void signIn(final String mobile, final String password) {
      /* FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference();
        DatabaseReference childDatabaseReference = databaseReference.child(mobile);
        childDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String recUsername = dataSnapshot.child("Mobile no").getValue().toString();
                String recPassword = dataSnapshot.child("Password").getValue().toString();

               if (mobile.equals(recUsername) && password.equals(recPassword))
                {

                    Toast.makeText(getContext(), "User signed in", Toast.LENGTH_SHORT).show();
                    spotsDialog.dismiss();
                   Intent i = new Intent(getContext(), AfterLoginActivity.class);
                    i.putExtra("mb",mobile);
                    startActivity(i);

                }
                else
                {

                    Toast.makeText(getContext(), "Invalid credentials", Toast.LENGTH_SHORT).show();
                    spotsDialog.dismiss();
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(getContext(), "Error Connecting DB"+databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });*/

        mAuth.signInWithEmailAndPassword(mobile, password)
                .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG, "signInWithEmail:onComplete:" + task.isSuccessful());
                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (task.isSuccessful())
                        {
                            FirebaseUser user=FirebaseAuth.getInstance().getCurrentUser();
                            if(user.isEmailVerified()) {
                                Toast.makeText(getContext(), "User Signed in",
                                        Toast.LENGTH_SHORT).show();
                                Intent i = new Intent(getContext(), AfterLoginActivity.class);
                                spotsDialog.dismiss();

                                startActivity(i);
                            }
                            else

                            {
                                Toast.makeText(getActivity(), "User not verified", Toast.LENGTH_SHORT).show();
                                spotsDialog.dismiss();
                            }
                        }
                        else
                        {
                            Toast.makeText(getActivity(), "Invalid credentials", Toast.LENGTH_SHORT).show();
                            spotsDialog.dismiss();
                        }

                        // ...
                    }
                });
        //}


    }
}
