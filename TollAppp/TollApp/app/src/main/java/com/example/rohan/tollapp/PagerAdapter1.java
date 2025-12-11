package com.example.rohan.tollapp;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

/**
 * Created by Rohan on 02-04-2017.
 */

public class PagerAdapter1 extends FragmentStatePagerAdapter
{
    int NumTabs;

    public PagerAdapter1(FragmentManager fm,int No)
    {
        super(fm);
        this.NumTabs=No;
    }

    @Override
    public Fragment getItem(int position)
    {
        switch (position)
        {
            case 0:
                Frag_SourceDestn source=new Frag_SourceDestn();
                return source;

            case 1:
                Frag_Map map=new Frag_Map();
                return  map;

            default:
                return null;


        }

    }

    @Override
    public int getCount()
    {
        return NumTabs;
    }
}
