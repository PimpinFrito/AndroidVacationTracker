package com.example.d308mobileapplication.Activities;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.d308mobileapplication.Model.Vacation;
import com.example.d308mobileapplication.R;

import java.util.List;

public class VacationListAdapter extends RecyclerView.Adapter<VacationListAdapter.VacationViewHolder> {

    List<Vacation> vacationList;
    public ClickListener clickListener;;

    public VacationListAdapter(List<Vacation> vacationList) {
        this.vacationList = vacationList;
    }

    public void setClickListener(ClickListener clickListener) {
        this.clickListener = clickListener;
    }

    public void setList(List<Vacation> list){
        vacationList = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public VacationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View vacationView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.vacation_list_item, parent, false);

        return new VacationViewHolder(vacationView);
    }

    @Override
    public void onBindViewHolder(@NonNull VacationViewHolder holder, int position) {
        Vacation vacation = vacationList.get(position);
        holder.titleTextView.setText(vacation.getTitle());
        holder.hotelTextView.setText(vacation.getHotel());
        holder.startDateTextView.setText("Start: " + vacation.getStartDate());
        holder.endDateTextView.setText("End: " + vacation.getEndDate());

    }

    @Override
    public int getItemCount() {
        return vacationList.size();
    }

    class VacationViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener{
        TextView titleTextView;
        TextView hotelTextView;
        TextView startDateTextView;
        TextView endDateTextView;
        public VacationViewHolder(@NonNull View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.titleTextView);
            hotelTextView = itemView.findViewById(R.id.hotelTextView);
            startDateTextView = itemView.findViewById(R.id.startDateTextView);
            endDateTextView = itemView.findViewById(R.id.endDateTextView);

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

