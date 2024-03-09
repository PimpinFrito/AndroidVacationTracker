package com.example.d308mobileapplication.Activities;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.d308mobileapplication.Model.Trip;
import com.example.d308mobileapplication.R;

import java.util.List;

public class TripListAdapter extends RecyclerView.Adapter<TripListAdapter.TripViewHolder> {
    List<Trip> tripList;
    public ClickListener clickListener;;

    public TripListAdapter(List<Trip> tripList) {
        this.tripList = tripList;
    }

    public void setClickListener(ClickListener clickListener) {
        this.clickListener = clickListener;
    }

    public void setList(List<Trip> list){
        tripList = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public TripListAdapter.TripViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View tripView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.trip_list_item, parent, false);

        return new TripListAdapter.TripViewHolder(tripView);
    }

    @Override
    public void onBindViewHolder(@NonNull TripListAdapter.TripViewHolder holder, int position) {
        Trip trip = tripList.get(position);
        holder.titleTextView.setText(trip.getTitle());
        holder.dateTextView.setText(trip.getDate());

    }

    @Override
    public int getItemCount() {
        return tripList.size();
    }

    class TripViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener{
        TextView titleTextView;

        TextView dateTextView;
        public TripViewHolder(@NonNull View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.trip_title_textView);
            dateTextView = itemView.findViewById(R.id.trip_date_textView);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if(clickListener != null){
                clickListener.onClick(v, getAdapterPosition());
            }
        }
    }
}