package com.example.tattoshop;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public class ReservedTimeAdapter extends RecyclerView.Adapter<ReservedTimeAdapter.ViewHolder>{
    Collection<Object> appointment;
    Context mcontext;
    List<String> idopontok;
    public ReservedTimeAdapter(Context context, Collection<Object> appointment){
        this.appointment = appointment;
        this.mcontext = context;
        idopontok = new ArrayList<>();
        for (Object o: appointment) {
            idopontok.add(o.toString());
            Log.d(ReservedTimeAdapter.class.getName(), o.toString());
        }
    }
    @NonNull
    @Override
    public ReservedTimeAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(mcontext).inflate(R.layout.recyclerview, parent, false));
    }
    @Override
    public void onBindViewHolder(@NonNull ReservedTimeAdapter.ViewHolder holder, int position) {
        holder.getTv().setText(idopontok.get(position));
    }

    @Override
    public int getItemCount() {
        return appointment.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        TextView tv;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv = itemView.findViewById(R.id.RVtextView);
        }
        public TextView getTv(){
            return tv;
        }
    }
}
