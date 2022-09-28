package com.sukanto.receptionist;

import static android.content.ContentValues.TAG;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

 public class Patients_Adapter extends RecyclerView.Adapter<Patients_Adapter.ViewHolder>{
    private ItemClick itemClick;
    List<patientData> patientDataList;
    Context context;

    public Patients_Adapter(Context context, List<patientData> patientDataList,ItemClick itemClick) {
        this.patientDataList = patientDataList;
        this.context = context;
        this.itemClick= itemClick;

    }

    @NonNull
    @Override
    public Patients_Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.user_list,parent,false);
       return new ViewHolder(view);
       /* View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_list, parent, false);
        return new ViewHolder(view);*/
    }

    @Override
    public void onBindViewHolder(@NonNull Patients_Adapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {

        holder.Username_T.setText(patientDataList.get(position).getName());
        holder.UserPhone_T.setText(patientDataList.get(position).getMobile());
        holder.UserAge_T.setText("Age "+patientDataList.get(position).getAge());
        holder.Gender_T.setText("("+patientDataList.get(position).getGender()+")");
       /* if(patientDataList.get(position).getAge()==null){
            holder.UserAge_T.setText("Age Not Found");
        }else{
            holder.UserAge_T.setText("Age "+patientDataList.get(position).getAge().toString());
        }

        if(patientDataList.get(position).getGender()==null){
            holder.Gender_T.setText("( Not Found )");
        }else{
            holder.Gender_T.setText("Age "+patientDataList.get(position).getGender());
        }*/
        holder.Date_T.setText(" "+patientDataList.get(position).getCreatedAt());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    itemClick.onItemClick(patientDataList.get(position));
            }
        });

    }

    @Override
    public int getItemCount() {
        return patientDataList.size();
    }
    public interface ItemClick{
        void onItemClick(patientData position);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView Username_T,UserPhone_T,UserAge_T,Date_T,Gender_T;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            Username_T=itemView.findViewById(R.id.profile_name);
            UserPhone_T=itemView.findViewById(R.id.phone);
            UserAge_T=itemView.findViewById(R.id.age);
            Date_T=itemView.findViewById(R.id.date);
            Gender_T=itemView.findViewById(R.id.gender);

        }
    }
}
