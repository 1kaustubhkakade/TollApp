package com.example.rohan.tollapp;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;

public class Generate extends AppCompatActivity {
    public final static int WIDTH=500;

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
                Intent i1 = new Intent(Generate.this,Database.class);
                startActivity(i1);
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
                                Intent i=new Intent(Generate.this,LoginActivity.class);
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
        setContentView(R.layout.activity_main);
        final ImageView img=(ImageView)findViewById(R.id.imgqr);
        Button b=(Button)findViewById(R.id.gen);
        Bundle bundle=getIntent().getExtras();
        String name=bundle.getString("name");
        String mobile=bundle.getString("mobile");
        String select=bundle.getString("select");
        String source=bundle.getString("source");
        String destination=bundle.getString("destination");
        String email=bundle.getString("email");
        String date=bundle.getString("date");
        String vechicle_number=bundle.getString("Vehicle_No");
        String type=bundle.getString("Vehicle");
        String type_J=bundle.getString("Journey");
        final String all="Name:"+name+"\nEmail id:"+email+"\nMobile:"+mobile+"\nDate:"+date+"\nVehicle Type:"+type+"\nVehicle number:"+vechicle_number+"\nType of toll paid:"+
                type_J+"\nTolls paid:"+select+"\nSource:"+source+"\nDestination:"+destination;


        Toast.makeText(this, ""+all, Toast.LENGTH_SHORT).show();

        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Bitmap bitmap = null;

                    bitmap = encodeAsBitmap(all);
                    img.setImageBitmap(bitmap);

                    SQLiteDatabase db = openOrCreateDatabase("History_Toll", Context.MODE_PRIVATE,null);
                    db.execSQL("create table if not exists srt(All varchar)");
                    db.execSQL("insert into srt values('"+all+"')");
                    Toast.makeText(Generate.this, "Transaction Complete"+all , Toast.LENGTH_SHORT).show();
                    Toast.makeText(Generate.this, "Values added:"+all, Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
    Bitmap encodeAsBitmap(String str) throws WriterException
    {
        BitMatrix result;
        try {
            result = new MultiFormatWriter().encode(str,
                    BarcodeFormat.QR_CODE, WIDTH, WIDTH, null);
        } catch (IllegalArgumentException iae) {
            // Unsupported format
            return null;
        }
        int w = result.getWidth();
        int h = result.getHeight();
        int[] pixels = new int[w * h];
        for (int y = 0; y < h; y++) {
            int offset = y * w;
            for (int x = 0; x < w; x++) {
                pixels[offset + x] = result.get(x, y) ? getResources().getColor(R.color.black) : getResources().getColor(R.color.white);
            }
        }
        Bitmap bitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        bitmap.setPixels(pixels, 0, 500, 0, 0, w, h);
        return bitmap;


    }
}
