package com.lloasd33cafe24.exhibitionapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class SectorListVIewAdapter extends BaseAdapter {

    LayoutInflater inflater = null;
    private ArrayList<SectorListItem> sectorListItems = null;
    private int nListCnt = 0;

    public SectorListVIewAdapter(ArrayList<SectorListItem> sectorListItems) {
        this.sectorListItems = sectorListItems;
        nListCnt = sectorListItems.size();
    }

    @Override
    public int getCount() {
        return nListCnt;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        if (convertView == null)
        {
            final Context context = parent.getContext();
            if (inflater == null)
            {
                inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            }
            convertView = inflater.inflate(R.layout.sectorlist_item, parent, false);
        }
            TextView textSectorName = (TextView) convertView.findViewById(R.id.showsectorname);
            TextView textNumOfEx = (TextView) convertView.findViewById(R.id.shownumberofex);
            textSectorName.setText(sectorListItems.get(position).getSectorname());
            textNumOfEx.setText(sectorListItems.get(position).getNumberofex());
            return convertView;

        }
    }



