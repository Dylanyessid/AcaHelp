package com.example.acahelp.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.acahelp.QuestionDetails;
import com.example.acahelp.R;
import com.example.acahelp.interfaces.userAPI;
import com.example.acahelp.models.Question;
import com.example.acahelp.models.User;
import com.example.acahelp.utilites.Constants;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class QuestionAdapter extends RecyclerView.Adapter<QuestionAdapter.ViewHolder> {

    ArrayList<Question> listData;
    Context context;
    public QuestionAdapter(ArrayList<Question> listData, Context context)
    {
        this.listData = listData;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.post,null,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.asignData(listData.get(position));
    }

    @Override
    public int getItemCount() {
        if(listData==null){
            return 0;
        }
        return listData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView title, user;
        TextView description;
        String id;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.tVTitle);
            description = itemView.findViewById(R.id.tVDesc);
            user = itemView.findViewById(R.id.tVUserQuestion);
            
            Button btnGoToQuestion = itemView.findViewById(R.id.btnGoToQuestion);
            btnGoToQuestion.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, QuestionDetails.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtra("questionId", id);
                    intent.putExtra("title", title.getText().toString());
                    intent.putExtra("desc", description.getText().toString());
                    intent.putExtra("isPrivate", false);
                    context.startActivity(intent);
                }
            });
        }

        public void asignData(Question question) {
            title.setText(question.getTitle());
            description.setText(question.getDescription());
            id= question.getId();
            getUserName(question.getUser(), user);
        }
    }

    private void getUserName(String user, TextView userName){
        userAPI userApi = Constants.retrofit.create(userAPI.class);
        Call<User> call = userApi.getUserName(user);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                userName.setText(response.body().getName() + " "+ response.body().getSurname());
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                userName.setText(null);
            }
        });
    }
}
