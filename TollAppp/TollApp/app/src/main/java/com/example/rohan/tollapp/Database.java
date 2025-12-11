package com.example.rohan.tollapp;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

public class Database extends AppCompatActivity {

    TextView op;
    StringBuffer o;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_database);
        op=(TextView) findViewById(R.id.history);
        o=new StringBuffer();
        try {
            SQLiteDatabase db;
            db = openOrCreateDatabase("History_Toll", Context.MODE_PRIVATE, null);
            db.execSQL("create table if not exists srt(Name varchar)");
            Cursor c = db.rawQuery("select * from srt", null);
            Toast.makeText(this, "", Toast.LENGTH_SHORT).show();

            while (c.moveToNext()) {
               /* Toast.makeText(this, "get here", Toast.LENGTH_SHORT).show();
                if (c.isNull(0)) {
                    op.setText("No transaction yet");
                    Toast.makeText(this, "got if", Toast.LENGTH_SHORT).show();
                    break;
                } else*/
                    o.append(c.getString(0));
                    Toast.makeText(this, "got else", Toast.LENGTH_SHORT).show();
                op.setText(o);
            }
        }
        catch (SQLiteException e)
        {
            Toast.makeText(this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
}
