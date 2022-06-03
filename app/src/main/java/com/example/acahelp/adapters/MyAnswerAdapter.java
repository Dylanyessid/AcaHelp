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
import com.example.acahelp.models.Answer;
import com.example.acahelp.models.Question;
import com.example.acahelp.utilites.Constants;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyAnswerAdapter extends RecyclerView.Adapter<MyAnswerAdapter.ViewHolder> {

    private ArrayList<Answer> myAnswers;
    private Context context;

    public MyAnswerAdapter(ArrayList<Answer> myAnswers, Context context){
        this.myAnswers = myAnswers;
        this.context = context;
    }

    @NonNull
    @Override
    public MyAnswerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.my_answer,null,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyAnswerAdapter.ViewHolder holder, int position) {
        holder.asignData(myAnswers.get(position));
    }

    @Override
    public int getItemCount() {
        return myAnswers.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView tVMyAnswer, tVMyAnswerQuestion;
        Button btnGoToQuestion;
        private String id, desc, title, user;
        private boolean isPrivate;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tVMyAnswer = itemView.findViewById(R.id.tVMyAnswer);
            tVMyAnswerQuestion = itemView.findViewById(R.id.tVMyAnswerQuestion);
            btnGoToQuestion = itemView.findViewById(R.id.btnGoToAnswerQuestion);
            btnGoToQuestion.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, QuestionDetails.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtra("user", user);
                    intent.putExtra("questionId", id);
                    intent.putExtra("title", title);
                    intent.putExtra("desc", desc);
                    intent.putExtra("isPrivate", isPrivate);
                    context.startActivity(intent);
                }
            });
        }

        public void asignData(Answer answers){
            getQuestionInfo(answers.getQuestion());
            id = answers.getQuestion();
            tVMyAnswer.setText(answers.getAnswer());

        }

        public void getQuestionInfo(String question){
            IQuestion iQuestion = Constants.retrofit.create(IQuestion.class);
            Call<Question> call = iQuestion.getQuestion(question);
            call.enqueue(new Callback<Question>() {
                @Override
                public void onResponse(Call<Question> call, Response<Question> response) {
                    tVMyAnswerQuestion.setText(response.body().getTitle());
                    isPrivate = response.body().isPrivate();
                    title = response.body().getTitle();
                    desc = response.body().getDescription();
                   user = response.body().getUser();
                }

                @Override
                public void onFailure(Call<Question> call, Throwable t) {

                }
            });

        }
    }

}
