package com.example.rohan.tollapp_admin;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
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

import dmax.dialog.SpotsDialog;

public class MainActivity extends AppCompatActivity {
    private Button Button_Login;
    ProgressDialog pg;
    private FirebaseAuth mAuth;
    private EditText Email_ID, Password;
    TextView getForgot_Password;
    SpotsDialog spotsDialog;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private TextView Textview_Create_Account, Verify_account, Forgot_Password;
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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
        getForgot_Password = (TextView) findViewById(R.id.forgotPass);
       // pg.setMessage("Login in process");
        Textview_Create_Account = (TextView) findViewById(R.id.Textview_Create_Account);
        Textview_Create_Account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, TollRegistration.class);
                startActivity(i);

            }
        });
        mAuth = FirebaseAuth.getInstance();
        Email_ID = (EditText) findViewById(R.id.Email_ID);
        Password = (EditText) findViewById(R.id.Password);
        Button_Login = (Button) findViewById(R.id.Button_Login);
        Button_Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                spotsDialog = new SpotsDialog(MainActivity.this, R.style.Custom);
                spotsDialog.show();

                String email = Email_ID.getText().toString();
                String password = Password.getText().toString();
                boolean valid = validate(email, password);
                if (valid == true) {

                    signIn(email, password);


                }
                Intent i = new Intent(MainActivity.this, QRCodeScanner.class);
                startActivity(i);
            }
        });
    }

    private boolean validate(String email, String password) {
        int flag = 1;
        if (Email_ID.getText().toString().isEmpty()) {
            Email_ID.setError("Enter email id");
            flag = 0;
        }
       /* else if(Email_ID.getText().toString().indexOf('@')==-1)
        {
            Email_ID.setError("Enter valid Email id");
            flag =0;
        }*/
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


    private void signIn(final String email, final String password) {
     /*   FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference();
        DatabaseReference childDatabaseReference = databaseReference.child(email);
        childDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String recUsername = dataSnapshot.child("Mobile no").getValue().toString();
                String recPassword = dataSnapshot.child("Password").getValue().toString();

               if (email.equals(recUsername) && password.equals(recPassword))
                {

                    Toast.makeText(getContext(), "User signed in", Toast.LENGTH_SHORT).show();
                    spotsDialog.dismiss();
                   Intent i = new Intent(getContext(), AfterLoginActivity.class);
                    startActivity(i);

                }
                else
                {

                    Toast.makeText(getContext(), "Invalid credentials", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(getContext(), "Error Connecting DB"+databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
*/
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG, "signInWithEmail:onComplete:" + task.isSuccessful());

                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (task.isSuccessful()) {
                            FirebaseUser user=FirebaseAuth.getInstance().getCurrentUser();
                            if(user.isEmailVerified()) {
                                Log.w(TAG, "signInWithEmail:failed", task.getException());
                                Toast.makeText(MainActivity.this, "User Signed in",
                                        Toast.LENGTH_SHORT).show();
                                spotsDialog.dismiss();
                            }
                            else
                            {
                                Toast.makeText(MainActivity.this, "User not verified", Toast.LENGTH_SHORT).show();
                                spotsDialog.dismiss();
                            }
                        }

                        // ...
                    }
                });
    }

}