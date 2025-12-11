package com.example.rohan.tollapp;

import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatSpinner;
import android.text.format.Time;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.instamojo.android.Instamojo;
import com.instamojo.android.activities.PaymentDetailsActivity;
import com.instamojo.android.callbacks.OrderRequestCallBack;
import com.instamojo.android.helpers.Constants;
import com.instamojo.android.models.Errors;
import com.instamojo.android.models.Order;
import com.instamojo.android.network.Request;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.Response;

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


public class GatewayActivity extends AppCompatActivity {


    private static final HashMap<String, String> env_options = new HashMap<>();

    static {
        env_options.put("Test", "https://test.instamojo.com/");
        env_options.put("Production", "https://api.instamojo.com/");
    }
    public String mb;
    private ProgressDialog dialog;
    private EditText nameBox, emailBox, phoneBox, amountBox, descriptionBox;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private static final String TAG = "SignupActivity";
    private String currentEnv = null;
    private String accessToken = null;
    String amount;
    String toll_S;
    private TextView t1;
    String source,destination,Journey,Type,Vehicle_No;
    String tolls_Seleted;



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater=getMenuInflater();
        menuInflater.inflate(R.menu.menu,menu);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.db:
                Toast.makeText(this, "Database", Toast.LENGTH_SHORT).show();
                break;
            case R.id.bye:
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
                alertDialogBuilder.setMessage("Are you sure, You wanted to make decision");
                alertDialogBuilder.setPositiveButton("yes",
                        new DialogInterface.OnClickListener()
                        {
                            @Override
                            public void onClick(DialogInterface arg0, int arg1) {

                                FirebaseAuth.getInstance().signOut();
                                Intent i=new Intent(GatewayActivity.this,LoginActivity.class);
                                startActivity(i);
                            }
                        });

                alertDialogBuilder.setNegativeButton("No",new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                });

                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
                break;




            case R.id.share:
                Intent i =new Intent(Intent.ACTION_SEND);
                i.setType("text/plain");
                String ShareBody="Hi hello Welcome";
                String ShareSub="zala Share";
                i.putExtra(Intent.EXTRA_SUBJECT,ShareSub);
                i.putExtra(Intent.EXTRA_SUBJECT,ShareBody);
                startActivity(Intent.createChooser(i,"choose one"));
                break;
        }
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gateway);
        tolls_Seleted=new String();
        source=new String();
        destination=new String();
        toll_S=new String();
        Bundle bundle=getIntent().getExtras();
        amount=bundle.getString("tollgates");
        source=bundle.getString("source");
        Toast.makeText(this, ""+amount, Toast.LENGTH_SHORT).show();
        destination=bundle.getString("dest");
        Toast.makeText(this, ""+destination, Toast.LENGTH_SHORT).show();
        Bundle b=getIntent().getExtras();
        mb=b.getString("Mobile");
        Journey=b.getString("Journey");
        Type=b.getString("Vehicle");

        Vehicle_No=b.getString("Vehicle_No");
        Toast.makeText(GatewayActivity.this, "\n"+Journey+" \n"+Vehicle_No+" "+destination+" \n"+"Mobile "+mb, Toast.LENGTH_LONG).show();
        t1=(TextView)findViewById(R.id.t1);
        /*Button setd=(Button)findViewById(R.id.setvalues);
        setd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mobile=phoneBox.getText().toString();
                setValues(mobile);

            }
        });*/

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
        Button button = (Button) findViewById(R.id.pay);
        nameBox = (EditText) findViewById(R.id.name);
        nameBox.setSelection(nameBox.getText().toString().trim().length());
        emailBox = (EditText) findViewById(R.id.email);
        emailBox.setSelection(emailBox.getText().toString().trim().length());
        phoneBox = (EditText) findViewById(R.id.phone);
        phoneBox.setSelection(phoneBox.getText().toString().trim().length());
        amountBox = (EditText) findViewById(R.id.amount);
       // amountBox.setSelection(amountBox.getText().toString().trim().length());
        descriptionBox = (EditText) findViewById(R.id.description);
        Bundle c=getIntent().getExtras();
       // mb=c.getString("mb2");
        phoneBox.setText(mb);

        String mobile=phoneBox.getText().toString();
        setValues(mobile);

        // descriptionBox.setSelection(descriptionBox.getText().toString().trim().length());
        AppCompatSpinner envSpinner = (AppCompatSpinner) findViewById(R.id.env_spinner);

        final ArrayList<String> envs = new ArrayList<>(env_options.keySet());
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, envs);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        envSpinner.setAdapter(adapter);
        envSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                currentEnv = envs.get(position);
                Instamojo.setBaseUrl(env_options.get(currentEnv));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        dialog = new ProgressDialog(this);
        dialog.setIndeterminate(true);
        dialog.setMessage("please wait...");
        dialog.setCancelable(false);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fetchTokenAndTransactionID();
            }
        });

        //let's set the log level to debug
        Instamojo.setLogLevel(Log.DEBUG);
    }


    // this is for the market place
    // you should have created the order from your backend and pass back the order id to app for the payment
    private void fetchOrder(String accessToken, String orderID) {
        // Good time to show dialog
        Request request = new Request(accessToken, orderID, new OrderRequestCallBack() {
            @Override
            public void onFinish(final Order order, final Exception error) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        dialog.dismiss();
                        if (error != null) {
                            if (error instanceof Errors.ConnectionError) {
                                showToast("No internet connection");
                            } else if (error instanceof Errors.ServerError) {
                                showToast("Server Error. Try again");
                            } else if (error instanceof Errors.AuthenticationError) {
                                showToast("Access token is invalid or expired. Please Update the token!!");
                            } else {
                                showToast(error.toString());
                            }
                            return;
                        }

                        startPreCreatedUI(order);
                    }
                });

            }
        });

        request.execute();
    }

    private void createOrder(String accessToken, String transactionID) {
        String name = nameBox.getText().toString();
        final String email = emailBox.getText().toString();
        String phone = phoneBox.getText().toString();
        String amount = amountBox.getText().toString();
        String description = descriptionBox.getText().toString();

        //Create the Order
        Order order = new Order(accessToken, transactionID, name, email, phone, amount, description);

        //set webhook
        order.setWebhook("http://your.server.com/webhook/");

        //Validate the Order
        if (!order.isValid()) {
            //oops order validation failed. Pinpoint the issue(s).

            if (!order.isValidName()) {
                nameBox.setError("Buyer name is invalid");
            }

            if (!order.isValidEmail()) {
                emailBox.setError("Buyer email is invalid");
            }

            if (!order.isValidPhone()) {
                phoneBox.setError("Buyer phone is invalid");
            }

            if (!order.isValidAmount()) {
                amountBox.setError("Amount is invalid or has more than two decimal places");
            }

            if (!order.isValidDescription()) {
                descriptionBox.setError("Description is invalid");
            }

            if (!order.isValidTransactionID()) {
                showToast("Transaction is Invalid");
            }

            if (!order.isValidRedirectURL()) {
                showToast("Redirection URL is invalid");
            }

            if (!order.isValidWebhook()) {
                showToast("Webhook URL is invalid");
            }

            return;
        }

        //Validation is successful. Proceed
        dialog.show();
        Request request = new Request(order, new OrderRequestCallBack() {
            @Override
            public void onFinish(final Order order, final Exception error) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        dialog.dismiss();
                        if (error != null) {
                            if (error instanceof Errors.ConnectionError) {
                                showToast("No internet connection");
                            } else if (error instanceof Errors.ServerError) {
                                showToast("Server Error. Try again");
                            } else if (error instanceof Errors.AuthenticationError) {
                                showToast("Access token is invalid or expired. Please Update the token!!");
                            } else if (error instanceof Errors.ValidationError) {
                                // Cast object to validation to pinpoint the issue
                                Errors.ValidationError validationError = (Errors.ValidationError) error;

                                if (!validationError.isValidTransactionID()) {
                                    showToast("Transaction ID is not Unique");
                                    return;
                                }

                                if (!validationError.isValidRedirectURL()) {
                                    showToast("Redirect url is invalid");
                                    return;
                                }

                                if (!validationError.isValidWebhook()) {
                                    showToast("Webhook url is invalid");
                                    return;
                                }

                                if (!validationError.isValidPhone()) {
                                    phoneBox.setError("Buyer's Phone Number is invalid/empty");
                                    return;
                                }

                                if (!validationError.isValidEmail()) {
                                    emailBox.setError("Buyer's Email is invalid/empty");
                                    return;
                                }

                                if (!validationError.isValidAmount()) {
                                    amountBox.setError("Amount is either less than Rs.9 or has more than two decimal places");
                                    return;
                                }

                                if (!validationError.isValidName()) {
                                    nameBox.setError("Buyer's Name is required");
                                    return;
                                }
                            } else {
                                showToast(error.getMessage());
                            }
                            return;
                        }

                        startPreCreatedUI(order);
                    }
                });
            }
        });

        request.execute();
    }

    private void startPreCreatedUI(Order order) {
        //Using Pre created UI
        Intent intent = new Intent(getBaseContext(), PaymentDetailsActivity.class);
        intent.putExtra(Constants.ORDER, order);
        startActivityForResult(intent, Constants.REQUEST_CODE);
    }

    private void startCustomUI(Order order) {
        //Custom UI Implementation
        Intent intent = new Intent(getBaseContext(), CustomUIActivity.class);
        intent.putExtra(Constants.ORDER, order);
        startActivityForResult(intent, Constants.REQUEST_CODE);
    }

    private void showToast(final String message) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(getBaseContext(), message, Toast.LENGTH_LONG).show();
            }
        });
    }

    /**
     * Fetch Access token and unique transactionID from developers server
     */
    private void fetchTokenAndTransactionID() {
        if (!dialog.isShowing()) {
            dialog.show();
        }

        OkHttpClient client = new OkHttpClient();
        HttpUrl url = getHttpURLBuilder()
                .addPathSegment("create")
                .build();

        RequestBody body = new FormBody.Builder()
                .add("env", currentEnv.toLowerCase())
                .build();

        okhttp3.Request request = new okhttp3.Request.Builder()
                .url(url)
                .post(body)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (dialog != null && dialog.isShowing()) {
                            dialog.dismiss();
                        }

                        showToast("Failed to fetch the Order Tokens");
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String responseString;
                String errorMessage = null;
                String transactionID = null;
                responseString = response.body().string();
                response.body().close();
                try {
                    JSONObject responseObject = new JSONObject(responseString);
                    if (responseObject.has("error")) {
                        errorMessage = responseObject.getString("error");
                    } else {
                        accessToken = responseObject.getString("access_token");
                        transactionID = responseObject.getString("transaction_id");
                    }
                } catch (JSONException e) {
                    errorMessage = "Failed to fetch Order tokens";
                }

                final String finalErrorMessage = errorMessage;
                final String finalTransactionID = transactionID;
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (dialog != null && dialog.isShowing()) {
                            dialog.dismiss();
                        }

                        if (finalErrorMessage != null) {
                            showToast(finalErrorMessage);
                            return;
                        }

                        createOrder(accessToken, finalTransactionID);
                    }
                });

            }
        });

    }

    /**
     * Will check for the transaction status of a particular Transaction
     *
     * @param transactionID Unique identifier of a transaction ID
     */
    private void checkPaymentStatus(final String transactionID, final String orderID) {
        if (accessToken == null || (transactionID == null && orderID == null)) {
            return;
        }

        if (dialog != null && !dialog.isShowing()) {
            dialog.show();
        }

        showToast("checking transaction status");
        OkHttpClient client = new OkHttpClient();
        HttpUrl.Builder builder = getHttpURLBuilder();
        builder.addPathSegment("status");
        if (transactionID != null) {
            builder.addQueryParameter("transaction_id", transactionID);
        } else {
            builder.addQueryParameter("id", orderID);
        }
        builder.addQueryParameter("env", currentEnv.toLowerCase());
        HttpUrl url = builder.build();

        okhttp3.Request request = new okhttp3.Request.Builder()
                .url(url)
                .addHeader("Authorization", "Bearer " + accessToken)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (dialog != null && dialog.isShowing()) {
                            dialog.dismiss();
                        }
                        showToast("Failed to fetch the Transaction status");
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String responseString = response.body().string();
                response.body().close();
                String status = null;
                String paymentID = null;
                String amount = null;
                String errorMessage = null;

                try {
                    JSONObject responseObject = new JSONObject(responseString);
                    JSONObject payment = responseObject.getJSONArray("payments").getJSONObject(0);
                    status = payment.getString("status");
                    paymentID = payment.getString("id");
                    amount = responseObject.getString("amount");

                } catch (JSONException e) {
                    errorMessage = "Failed to fetch the Transaction status";
                }

                final String finalStatus = status;
                final String finalErrorMessage = errorMessage;
                final String finalPaymentID = paymentID;
                final String finalAmount = amount;
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (dialog != null && dialog.isShowing()) {
                            dialog.dismiss();
                        }
                        if (finalStatus == null) {
                            showToast(finalErrorMessage);
                            return;
                        }

                        if (!finalStatus.equalsIgnoreCase("successful")) {
                            showToast("Transaction still pending");
                            return;
                        }

                        showToast("Transaction Successful for id - " + finalPaymentID);
                        Intent i=new Intent(GatewayActivity.this,Generate.class);
                        i.putExtra("select",tolls_Seleted);
                        i.putExtra("mobile",mb);
                        i.putExtra("name",nameBox.getText().toString());
                        String currentDateTimeString = DateFormat.getDateTimeInstance().format(new Date());

// textView is the TextView view that should display it
                        i.putExtra("source",source);
                        i.putExtra("destination",destination);
                        i.putExtra("select",toll_S);

                        i.putExtra("Journey",Journey);
                        i.putExtra("Vehicle_No",Vehicle_No);
                        i.putExtra("Vehicle",Type);
                        i.putExtra("email",emailBox.getText().toString());
                        i.putExtra("date",currentDateTimeString);
                        Toast.makeText(GatewayActivity.this, "\n"+Journey+" \n"+Vehicle_No+" "+destination+" \n"+"Mobile "+mb, Toast.LENGTH_LONG).show();
                        startActivity(i);

                       // refundTheAmount(transactionID, finalAmount);
                    }
                });
            }
        });

    }

    /**
     * Will initiate a refund for a given transaction with given amount
     *
     * @param transactionID Unique identifier for the transaction
     * @param amount        amount to be refunded
     */
    private void refundTheAmount(String transactionID, String amount) {
        if (accessToken == null || transactionID == null || amount == null) {
            return;
        }

        if (dialog != null && !dialog.isShowing()) {
            dialog.show();
        }

        showToast("Initiating a refund for - " + amount);
        OkHttpClient client = new OkHttpClient();
        HttpUrl url = getHttpURLBuilder()
                .addPathSegment("refund")
                .addPathSegment("")
                .build();

        RequestBody body = new FormBody.Builder()
                .add("env", currentEnv.toLowerCase())
                .add("transaction_id", transactionID)
                .add("amount", amount)
                .add("type", "PTH")
                .add("body", "Refund the Amount")
                .build();

        okhttp3.Request request = new okhttp3.Request.Builder()
                .url(url)
                .addHeader("Authorization", "Bearer " + accessToken)
                .post(body)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (dialog != null && dialog.isShowing()) {
                            dialog.dismiss();
                        }
                        showToast("Failed to Initiate a refund");
                    }
                });
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (dialog != null && dialog.isShowing()) {
                            dialog.dismiss();
                        }
                        String message;

                        if (response.isSuccessful()) {
                            message = "Refund intiated successfully";
                        } else {
                            message = "Failed to Initiate a refund";
                        }

                        showToast(message);
                    }
                });
            }
        });
    }

    private HttpUrl.Builder getHttpURLBuilder() {
        return new HttpUrl.Builder()
                .scheme("https")
                .host("sample-sdk-server.instamojo.com");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Constants.REQUEST_CODE && data != null) {
            String orderID = data.getStringExtra(Constants.ORDER_ID);
            String transactionID = data.getStringExtra(Constants.TRANSACTION_ID);
            String paymentID = data.getStringExtra(Constants.PAYMENT_ID);

            // Check transactionID, orderID, and orderID for null before using them to check the Payment status.
            if (transactionID != null || paymentID != null) {
                checkPaymentStatus(transactionID, orderID);
            } else {
                showToast("Oops!! Payment was cancelled");
            }
        }
    }

    public void setValues(String mobile) {
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference();
        DatabaseReference childDatabaseReference = databaseReference.child(mobile);
        childDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot)
            {

                String email= dataSnapshot.child("Email").getValue().toString();
                String name= dataSnapshot.child("Name").getValue().toString();
                emailBox.setText(email);

                nameBox.setText(name);
              //  phoneBox.setText(Mobile_No);
                Bundle bundle=getIntent().getExtras();
                amountBox.setText(amount);
                toll_S=bundle.getString("select");
                t1.setText(toll_S);




            }

            @Override
            public void onCancelled(DatabaseError databaseError)
            {

            }
        });

    }
}
