package com.example.acahelp.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.acahelp.PostAdapter;
import com.example.acahelp.R;
import com.example.acahelp.models.Answer;
import com.example.acahelp.models.Question;

import java.util.ArrayList;

public class AnswerAdapter extends RecyclerView.Adapter<AnswerAdapter.ViewHolder> {

    ArrayList<Answer> listData;
    public AnswerAdapter(ArrayList<Answer> listData) {
        this.listData = listData;
    }


    @NonNull
    @Override
    public AnswerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.answer,null,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AnswerAdapter.ViewHolder holder, int position) {
        holder.asignData(listData.get(position));
    }

    @Override
    public int getItemCount() {
        return listData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView username;
        TextView answer;
        Button btnLike, btnDislike;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            username = itemView.findViewById(R.id.tVUsername);
            answer = itemView.findViewById(R.id.tVAnswer);
            btnLike = itemView.findViewById(R.id.btnLike);
            btnDislike = itemView.findViewById(R.id.btnDislike);

        }

        public void asignData(Answer answer) {
            username.setText(answer.getUser());
            this.answer.setText(answer.getAnswer());
        }
    }
}
