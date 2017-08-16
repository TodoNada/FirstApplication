package com.example.holynskyi.firstapplication.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.example.holynskyi.firstapplication.dialogs.OnCarItemSelectedListener;
import com.example.holynskyi.firstapplication.dialogs.OnHouseItemSelectedListener;
import com.example.holynskyi.firstapplication.models.House;

import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import java.util.List;

import com.daimajia.swipe.SwipeLayout;
import com.example.holynskyi.firstapplication.R;

/**
 * Created by holynskyi on 15.08.17.
 */

public class HouseAdapter extends RecyclerView.Adapter<HouseAdapter.HouseViewHolder> {


    protected List<House> houseList;       // The list of houses that will be displayed

    private OnHouseItemSelectedListener onHouseItemSelectedListener;


    public HouseAdapter(List<House> carList) {
        this.houseList = carList;
    }

    public static class HouseViewHolder extends RecyclerView.ViewHolder {
        SwipeLayout swipeLayout;
        ImageView ivDeleteItem;
        CardView cv;
        TextView tvHouseCity;
        TextView tvHouseAdress;
        TextView tvHouseOther;
        ImageView ivHouseView;

        HouseViewHolder(View itemView) {
            super(itemView);
            swipeLayout = (SwipeLayout) itemView.findViewById(R.id.swipeHouse);
            cv = (CardView) itemView.findViewById(R.id.cvHouse);
            tvHouseCity = (TextView) itemView.findViewById(R.id.textViewHouseCity);
            tvHouseAdress = (TextView) itemView.findViewById(R.id.textViewHouseAdress);
            tvHouseOther = (TextView) itemView.findViewById(R.id.textViewHouseOther);
            ivHouseView = (ImageView) itemView.findViewById(R.id.imageViewHouseView);
            ivDeleteItem = (ImageView) itemView.findViewById(R.id.imageViewDeleteItemHouse);
        }
    }


    public void setOnHouseItemSelectedListener(OnHouseItemSelectedListener onHouseItemSelectedListener) {
        this.onHouseItemSelectedListener = onHouseItemSelectedListener;
    }

    @Override
    public HouseAdapter.HouseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_house, parent, false);
        HouseViewHolder cvh = new HouseViewHolder(v);
        return cvh;
    }

    @Override
    public void onBindViewHolder(HouseViewHolder holder, final int position) {
        final long id = houseList.get(position).getId();
        holder.tvHouseCity.setText(houseList.get(position).getCity());
        holder.tvHouseAdress.setText((houseList.get(position).getAdress()));
        holder.tvHouseOther.setText((houseList.get(position).getOther()));
        holder.swipeLayout.setShowMode(SwipeLayout.ShowMode.LayDown);
        holder.swipeLayout.addSwipeListener(new SwipeLayout.SwipeListener() {

            @Override
            public void onStartOpen(SwipeLayout layout) {

            }

            @Override
            public void onOpen(SwipeLayout layout) {

            }

            @Override
            public void onStartClose(SwipeLayout layout) {

            }

            @Override
            public void onClose(SwipeLayout layout) {

            }

            @Override
            public void onUpdate(SwipeLayout layout, int leftOffset, int topOffset) {

            }

            @Override
            public void onHandRelease(SwipeLayout layout, float xvel, float yvel) {

            }
        });

        holder.ivDeleteItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // listener to delete
                onHouseItemSelectedListener.itemHouseSelected(position, id);

            }
        });
    }

    @Override
    public int getItemCount() {
        if (houseList == null) return 0;
        return houseList.size();
    }
}
