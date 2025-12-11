package com.example.rohan.tollapp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;


public class PaymentActivity1 extends AppCompatActivity
{


    Button  abtn,hbtn,ibtn,kbtn,sbtn,hsbtn,nxtbtn;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);


        abtn=(Button)findViewById(R.id.axisid);
        hbtn=(Button)findViewById(R.id.hdfcid);
        ibtn=(Button)findViewById(R.id.iciciid);
        kbtn=(Button)findViewById(R.id.kotakid);
        sbtn=(Button)findViewById(R.id.sbiid);
        hsbtn=(Button)findViewById(R.id.hsbcid);
        nxtbtn=(Button)findViewById(R.id.nxtbtnid);



        abtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)

            {

                String url="https://retail.axisbank.co.in/wps/portal/rBanking/axisebanking/AxisRetailLogin/!ut/p/a1/04_Sj9CPykssy0xPLMnMz0vMAfGjzOKNAzxMjIwNjLwsQp0MDBw9PUOd3HwdDQwMjIEKIoEKDHAARwNC-sP1o_ArMYIqwGNFQW6EQaajoiIAVNL82A!!/dl5/d5/L2dBISEvZ0FBIS9nQSEh";
                Intent i =new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                startActivity(i);

            }
        });


        //hdfc


        hbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url="https://netbanking.hdfcbank.com/netbanking/";
                Intent i =new Intent(Intent.ACTION_VIEW,Uri.parse(url));
                startActivity(i);
            }
        });

        //icici

        ibtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String url="https://infinity.icicibank.com/corp/AuthenticationController?FORMSGROUP_ID__=AuthenticationFG&__START_TRAN_FLAG__=Y&FG_BUTTONS__=LOAD&ACTION.LOAD=Y&AuthenticationFG.LOGIN_FLAG=1&BANK_ID=ICI";
                Intent i =new Intent(Intent.ACTION_VIEW,Uri.parse(url));
                startActivity(i);
            }
        });


        //kotak
        kbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url="https://www.kotak.com/j1001mp/netapp/MainPage.jsp";
                Intent i =new Intent(Intent.ACTION_VIEW,Uri.parse(url));
                startActivity(i);
            }
        });

        //sbi
        sbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url="https://retail.onlinesbi.com/retail/login.htm";
                Intent i =new Intent(Intent.ACTION_VIEW,Uri.parse(url));
                startActivity(i);
            }
        });

        //hsbc
        hsbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url="https://www.hsbc.co.in/1/2/!ut/p/c5/04_SB8K8xLLM9MSSzPy8xBz9CP0os3gDf6NAZ8tQU3c3A0dDV5MAf2MTAwjQL8h2VAQAdKy3eg!!/?__EntryPage=hubpib.mobile";
                Intent i =new Intent(Intent.ACTION_VIEW,Uri.parse(url));
                startActivity(i);
            }
        });


        nxtbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent igenerateQR=new Intent(PaymentActivity1.this,GenerateQrModule.class);
                //startActivity(igenerateQR);
                Toast.makeText(PaymentActivity1.this, "called", Toast.LENGTH_SHORT).show();
            }
        });

    }

}
