package com.sukanto.receptionist;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class Schedules_adapter extends RecyclerView.Adapter<Schedules_adapter.ViewHolder> {

    List<Schedule_data> schedule_data;
    Context context;
    String status;

    public Schedules_adapter(List<Schedule_data> schedule_data, Context context) {
        this.schedule_data = schedule_data;
        this.context = context;
    }

    @NonNull
    @Override
    public Schedules_adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.schedule_list,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Schedules_adapter.ViewHolder holder, int position) {

        holder.patient_nameTV.setText(schedule_data.get(position).getPatient_name());
        holder.DrName_TV.setText(schedule_data.get(position).getDoctor_name());
        holder.schedule_timeTV.setText(schedule_data.get(position).getSchedule_time());
        holder.Schedule_dateTV.setText(schedule_data.get(position).getShedule_date());
     status=schedule_data.get(position).getStatus()==null?"Pending":schedule_data.get(position).getStatus();
        holder.statusTV.setText("("+status+")");

    }

    @Override
    public int getItemCount() {
        return schedule_data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView patient_nameTV,DrName_TV,Schedule_dateTV,schedule_timeTV,statusTV;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            patient_nameTV=itemView.findViewById(R.id.patient_name);
            DrName_TV=itemView.findViewById(R.id.DrName);
            Schedule_dateTV=itemView.findViewById(R.id.Schedule_date);
            schedule_timeTV=itemView.findViewById(R.id.time);
            statusTV=itemView.findViewById(R.id.status);
        }
    }
}
