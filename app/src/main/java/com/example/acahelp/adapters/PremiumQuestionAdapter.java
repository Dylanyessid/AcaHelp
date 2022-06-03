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
import com.example.acahelp.interfaces.IQuestion;
import com.example.acahelp.interfaces.userAPI;
import com.example.acahelp.models.Question;
import com.example.acahelp.models.User;
import com.example.acahelp.utilites.Constants;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class PremiumQuestionAdapter extends RecyclerView.Adapter<PremiumQuestionAdapter.ViewHolder> {

    ArrayList<Question> listData;
    Context context;
    public PremiumQuestionAdapter(ArrayList<Question> listData, Context context)
    {
        this.listData = listData;
        this.context = context;
    }

    @NonNull
    @Override
    public PremiumQuestionAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.premium_question,null,false);
        return new PremiumQuestionAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PremiumQuestionAdapter.ViewHolder holder, int position) {
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
        TextView area;
        String id,desc, user_id;
        Button btnGoToQuestion;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.tVPremiumQuestionTitle);
             area= itemView.findViewById(R.id.tVPremiumQuestionArea);
            user = itemView.findViewById(R.id.tVPremiumQuestionAuthor);
            btnGoToQuestion = itemView.findViewById(R.id.btnGoToPremiumQuestion);
            btnGoToQuestion.setVisibility(View.GONE);
            btnGoToQuestion.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, QuestionDetails.class).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtra("questionId", id);
                    intent.putExtra("user", user_id);
                    intent.putExtra("desc", desc);
                    intent.putExtra("title", title.getText().toString());
                    intent.putExtra("area", area.getText().toString());
                    intent.putExtra("isPrivate", true);
                    //intent.putExtra("user", area.getText().toString());
                    context.startActivity(intent);
                }
            });
        }

        public void asignData(Question question) {
            getAuthor(question.getUser());
            title.setText(question.getTitle());
            area.setText(question.getArea());
            id= question.getId();
            desc = question.getDescription();
            user_id = question.getUser();
            btnGoToQuestion.setVisibility(View.VISIBLE);
        }
        private void getAuthor(String id){
            userAPI userAPI = Constants.retrofit.create(userAPI.class);
            Call<User> call = userAPI.getUserName(id);
            call.enqueue(new Callback<User>() {
                @Override
                public void onResponse(Call<User> call, Response<User> response) {
                    user.setText(response.body().getName() + " " + response.body().getSurname());
                }

                @Override
                public void onFailure(Call<User> call, Throwable t) {

                }
            });
        }
    }


}
