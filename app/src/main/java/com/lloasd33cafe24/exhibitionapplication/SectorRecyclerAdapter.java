package com.lloasd33cafe24.exhibitionapplication;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class SectorRecyclerAdapter extends RecyclerView.Adapter<SectorRecyclerAdapter.ItemViewHolder> {
    private ArrayList<SectorListItem> sectorListItems = new ArrayList<>();
    private Activity activity;
    private String adminID;
    private String name;
    private int selectedPosition = -1;

    public SectorRecyclerAdapter(Activity activity, ArrayList<SectorListItem> datalist, String adminID, String name){
        this.activity = activity;
        sectorListItems = datalist;
        this.adminID = adminID;
        this.name = name;
    }
    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.sectorlist_item, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ItemViewHolder holder, int position) {
        holder.onBind(sectorListItems.get(position), position);
    }

    @Override
    public int getItemCount() {
        return sectorListItems.size();
    }





    class ItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView textView1;
        private TextView textView2;
        private ArrayList<SectorListItem> data;

        ItemViewHolder(View itemView) {
            super(itemView);

            textView1 = itemView.findViewById(R.id.textView1);
            textView2 = itemView.findViewById(R.id.textView2);

        }

        void onBind(SectorListItem sectorListItem, int position){

            this.data = sectorListItems;
            textView1.setText("전시 구역 : " + sectorListItem.getSectorname());
            Integer temp = sectorListItem.getNumberofex();
            String temp2 = temp.toString();
            textView2.setText("작품 갯수 : " + temp2);
            itemView.setOnClickListener(this);


        }

        @Override
        public void onClick(View v) {

        }
    }


}
