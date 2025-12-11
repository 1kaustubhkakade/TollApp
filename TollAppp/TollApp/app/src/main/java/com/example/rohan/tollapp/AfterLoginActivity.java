package com.example.rohan.tollapp;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class AfterLoginActivity extends AppCompatActivity
{

    public String mb;
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
                Intent intent=new Intent(AfterLoginActivity.this,Database.class);
                startActivity(intent);
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
                                Intent i=new Intent(AfterLoginActivity.this,LoginActivity.class);
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
        setContentView(R.layout.activity_after_login);
            //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
            // setSupportActionBar(toolbar);



            TabLayout tabLayout = (TabLayout) findViewById(R.id.Tablay);
        Bundle bundle = getIntent().getExtras();
       // mb=bundle.getString("mb");
        Toast.makeText(this, ""+mb, Toast.LENGTH_SHORT).show();
            tabLayout.addTab(tabLayout.newTab().setText("Tolls in maharashtra"));
        tabLayout.addTab(tabLayout.newTab().setText("Selection of tolls"));
            tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

            final ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
            final PagerAdapter1 adapter = new PagerAdapter1
                    (getSupportFragmentManager(), tabLayout.getTabCount());
            viewPager.setAdapter(adapter);
            viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
            tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
                @Override
                public void onTabSelected(TabLayout.Tab tab)
                {
                    viewPager.setCurrentItem(tab.getPosition());

                }

                @Override
                public void onTabUnselected(TabLayout.Tab tab)
                {

                }

                @Override
                public void onTabReselected(TabLayout.Tab tab)
                {

                }
            });
        }
    private Boolean exit = false;
    @Override
    public void onBackPressed() {
        if (exit) {
            finish(); // finish activity
        } else {
            RelativeLayout r=(RelativeLayout)findViewById(R.id.main_content);
            Snackbar snackbar=Snackbar.make(r,"Press back again to exit",Snackbar.LENGTH_INDEFINITE);
            snackbar.show();
            exit = true;
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    exit = false;
                }
            }, 3 * 1000);

        }

    }

}
