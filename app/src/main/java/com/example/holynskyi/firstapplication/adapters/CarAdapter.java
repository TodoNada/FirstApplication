package com.example.holynskyi.firstapplication.adapters;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import java.util.List;
import com.example.holynskyi.firstapplication.R;
import com.example.holynskyi.firstapplication.models.Car;

/**
 * Created by holynskyi on 09.08.17.
 */

public class CarAdapter extends RecyclerView.Adapter<CarAdapter.CarViewHolder> {

    protected List<Car> carList;       // The list of reminders that will be displayed

    public CarAdapter(List<Car> carList) {
        this.carList = carList;
    }

    public static class CarViewHolder extends RecyclerView.ViewHolder {
        CardView cv;
        TextView tvCarTitle;
        TextView tvCarDescription;
        ImageView ivCarView;
        CarViewHolder(View itemView) {
            super(itemView);
            cv = (CardView)itemView.findViewById(R.id.cv);
            tvCarTitle = (TextView)itemView.findViewById(R.id.textViewCarName);
            tvCarDescription = (TextView)itemView.findViewById(R.id.textViewCarDescription);
            ivCarView = (ImageView)itemView.findViewById(R.id.imageViewCarView);
        }
    }



    @Override
    public void onBindViewHolder(CarViewHolder holder, int position) {
        holder.tvCarTitle.setText(carList.get(position).getTitleOne());
        holder.tvCarDescription.setText((carList.get(position).getTitleTwo()));
        //  holder.ivCarView.setImageResource(persons.get(i).photoId);
    }



    @Override
    public CarViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_car, parent, false);
        CarViewHolder cvh = new CarViewHolder(v);
        return cvh;
    }


    @Override
    public int getItemCount() {
        return carList.size();
    }


    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

}
