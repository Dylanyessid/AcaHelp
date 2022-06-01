package com.example.acahelp.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.acahelp.R;

import java.util.ArrayList;

public class AreaAdapter extends RecyclerView.Adapter<AreaAdapter.ViewHolder>{


    ArrayList<String> areas;
    public AreaAdapter(ArrayList<String> areas){this.areas = areas;}
    @NonNull
    @Override
    public AreaAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.area,null,false);
        return new AreaAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.asignData(areas.get(position));
    }


    @Override
    public int getItemCount() {
        return areas.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView areaName;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            areaName = itemView.findViewById(R.id.tVArea);
            Button btnDeleteArea = itemView.findViewById(R.id.btnDelete);
            btnDeleteArea.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    areas.remove(getLayoutPosition());
                    notifyItemRemoved(getLayoutPosition());
                    System.out.println("VEAMOS " + getLayoutPosition());
                }
            });
        }

        public void asignData(String area){
            areaName.setText(area);
        }
    }
}
