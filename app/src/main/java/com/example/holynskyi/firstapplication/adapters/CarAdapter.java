package com.example.holynskyi.firstapplication.adapters;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import java.util.List;

import com.daimajia.swipe.SwipeLayout;
import com.example.holynskyi.firstapplication.R;
import com.example.holynskyi.firstapplication.dialogs.OnCarItemSelectedListener;
import com.example.holynskyi.firstapplication.models.Car;

/**
 * Created by holynskyi on 09.08.17.
 */

public class CarAdapter extends RecyclerView.Adapter<CarAdapter.CarViewHolder> {

    protected List<Car> carList;       // The list of cars that will be displayed

    private OnCarItemSelectedListener onItemSelectedListener;


    public CarAdapter(List<Car> carList) {
        this.carList = carList;
    }

    public void setOnCarItemSelectedListener(OnCarItemSelectedListener onItemSelectedListener) {
        this.onItemSelectedListener = onItemSelectedListener;
    }

    public static class CarViewHolder extends RecyclerView.ViewHolder {
        SwipeLayout swipeLayout;
        ImageView ivDeleteItem;
        CardView cv;
        TextView tvCarTitle;
        TextView tvCarDescription;
        ImageView ivCarView;

        CarViewHolder(View itemView) {
            super(itemView);
            swipeLayout = (SwipeLayout) itemView.findViewById(R.id.swipe);
            cv = (CardView) itemView.findViewById(R.id.cv);
            tvCarTitle = (TextView) itemView.findViewById(R.id.textViewCarName);
            tvCarDescription = (TextView) itemView.findViewById(R.id.textViewCarDescription);
            ivCarView = (ImageView) itemView.findViewById(R.id.imageViewCarView);
            ivDeleteItem = (ImageView) itemView.findViewById(R.id.imageViewDeleteItem);
        }
    }

    @Override
    public void onBindViewHolder(CarViewHolder holder, final int position) {
        final long id = carList.get(position).getId();
        holder.tvCarTitle.setText(carList.get(position).getTitleOne());
        holder.tvCarDescription.setText((carList.get(position).getTitleTwo()));
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
                Log.d("ADAPTER", "clicked position " + position + " with id " + id);
                // listener to delete
                onItemSelectedListener.itemCarSelected(position, id);

            }
        });
    }

    @Override
    public CarViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_car, parent, false);
        CarViewHolder cvh = new CarViewHolder(v);
        return cvh;
    }


    @Override
    public int getItemCount() {
        if (carList == null) return 0;
        return carList.size();
    }


    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }




}
