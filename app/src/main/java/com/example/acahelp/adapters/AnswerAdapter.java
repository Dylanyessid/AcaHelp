package com.example.acahelp.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.acahelp.R;
import com.example.acahelp.interfaces.userAPI;
import com.example.acahelp.models.Answer;
import com.example.acahelp.models.User;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

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
        TextView username, score;
        TextView answer;
        Button btnLike, btnDislike;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            username = itemView.findViewById(R.id.tVUsername);
            answer = itemView.findViewById(R.id.tVAnswer);
            btnLike = itemView.findViewById(R.id.btnLike);
            btnDislike = itemView.findViewById(R.id.btnDislike);
            score = itemView.findViewById(R.id.tVScore);

        }

        public void asignData(Answer answer) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("http://192.168.1.66:4000")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            userAPI userAPI = retrofit.create(userAPI.class);
            Call<User> call = userAPI.getUserName(answer.getUser());
            call.enqueue(new Callback<User>() {
                @Override
                public void onResponse(Call<User> call, Response<User> response) {
                    System.out.println(response.body());
                    if(response.body()!=null){
                        username.setText(response.body().getName() + " " + response.body().getSurname());
                    }

                }

                @Override
                public void onFailure(Call<User> call, Throwable t) {
                    username.setText(null);
                }
            });
            String scoreString = "Puntaje: "+ String.valueOf(answer.getScore());
            this.answer.setText(answer.getAnswer());
            score.setText(scoreString);
        }
    }


}
