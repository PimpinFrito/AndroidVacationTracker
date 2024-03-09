package com.example.d308mobileapplication.Activities;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.d308mobileapplication.Model.Excursion;
import com.example.d308mobileapplication.Model.Vacation;
import com.example.d308mobileapplication.R;

import org.w3c.dom.Text;

import java.util.List;

public class ExcursionListAdapter extends RecyclerView.Adapter<ExcursionListAdapter.ExcursionViewHolder> {
    List<Excursion> excursionList;
    public ClickListener clickListener;;

    public ExcursionListAdapter(List<Excursion> excursionList) {
        this.excursionList = excursionList;
    }

    public void setClickListener(ClickListener clickListener) {
        this.clickListener = clickListener;
    }

    public void setList(List<Excursion> list){
        excursionList = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ExcursionListAdapter.ExcursionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View excursionView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.excursion_list_item, parent, false);

        return new ExcursionListAdapter.ExcursionViewHolder(excursionView);
    }

    @Override
    public void onBindViewHolder(@NonNull ExcursionListAdapter.ExcursionViewHolder holder, int position) {
        Excursion excursion = excursionList.get(position);
        holder.titleTextView.setText(excursion.getTitle());
        holder.dateTextView.setText(excursion.getDate());

    }

    @Override
    public int getItemCount() {
        return excursionList.size();
    }

    class ExcursionViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener{
        TextView titleTextView;

        TextView dateTextView;
        public ExcursionViewHolder(@NonNull View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.excursion_title_textView);
            dateTextView = itemView.findViewById(R.id.excursion_date_textView);

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
