package com.example.acahelp.adapters;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.acahelp.R;
import com.example.acahelp.interfaces.IAnswer;
import com.example.acahelp.interfaces.userAPI;
import com.example.acahelp.models.Answer;
import com.example.acahelp.models.Count;
import com.example.acahelp.models.Score;
import com.example.acahelp.models.User;
import com.example.acahelp.utilites.Constants;
import com.example.acahelp.utilites.SpacingItemDecorator;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AnswerAdapter extends RecyclerView.Adapter<AnswerAdapter.ViewHolder> {

    ArrayList<Answer> listData;
    private SharedPreferences preferences;
    private Context context;
    private ArrayList<Score> scoredAnswers;
    private boolean isPrivate;

    public AnswerAdapter(ArrayList<Answer> listData,Context context, ArrayList<Score> scores, boolean isPrivate) {
        this.context = context;
        preferences = context.getSharedPreferences( context.getString(R.string.sharedP), Context.MODE_PRIVATE);
        this.listData = listData;
        this.scoredAnswers = scores;
        this.isPrivate = isPrivate;
    }

    public AnswerAdapter(ArrayList<Answer> listData) {
        this.listData = listData;
    }

    @NonNull
    @Override
    public AnswerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        if(isPrivate){
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.premium_message,null,false);
        }else{
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.answer,null,false);

        }

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AnswerAdapter.ViewHolder holder, int position) {
        holder.asignData(listData.get(position), position);
    }

    @Override
    public int getItemCount() {
        return listData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView username, score, timestamp;
        TextView answer;
        Button btnLike, btnDislike, btnRemoveScore;
        int answerTotalScore;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            View rootView = itemView;
            if(!isPrivate){
                username = itemView.findViewById(R.id.tVUsername);
                answer = itemView.findViewById(R.id.tVAnswer);
                btnLike = itemView.findViewById(R.id.btnLike);
                btnDislike = itemView.findViewById(R.id.btnDislike);
                btnRemoveScore = itemView.findViewById(R.id.btnRemoveScore);
                score = itemView.findViewById(R.id.tVScore);

                btnLike.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        btnLike.setEnabled(false);
                        btnDislike.setEnabled(false);
                        String id = (listData.get(getLayoutPosition()).get_id());
                        qualifyAnswer(id, true, score);

                    }
                });

                btnDislike.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        btnLike.setEnabled(false);
                        btnDislike.setEnabled(false);
                        String id = (listData.get(getLayoutPosition()).get_id());
                        qualifyAnswer(id, false, score);


                    }
                });

                btnRemoveScore.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        removeScore((listData.get(getLayoutPosition()).get_id()));
                    }
                });
            }else{
                username = itemView.findViewById(R.id.tVMessageUserName);
                answer = itemView.findViewById(R.id.tVMessage);
            }

        }

        private void removeScore(String id){
            IAnswer iAnswer = Constants.retrofit.create(IAnswer.class);
            Call<Score> call = iAnswer.removeScore(id, preferences.getString(context.getString(R.string.sharedP), "xd"));
            call.enqueue(new Callback<Score>() {
                @Override
                public void onResponse(Call<Score> call, Response<Score> response) {
                    switch (response.code()){
                        case 200:
                            Toast.makeText(answer.getContext(), "Se ha eliminado tu puntuación", Toast.LENGTH_SHORT).show();
                            btnDislike.setVisibility(View.VISIBLE);
                            btnLike.setVisibility(View.VISIBLE);
                            break;
                    }
                }

                @Override
                public void onFailure(Call<Score> call, Throwable t) {

                }
            });
        }



        private void qualifyAnswer(String id, boolean isPositive, TextView tVScore){
            IAnswer iAnswer = Constants.retrofit.create(IAnswer.class);
            Score score = new Score(preferences.getString(context.getString(R.string.sharedP), "xd"), isPositive);
            Call<Answer> call = iAnswer.qualifyAnswer(id, score);
            call.enqueue(new Callback<Answer>() {
                @Override
                public void onResponse(Call<Answer> call, Response<Answer> response) {
                    switch (response.code()){
                        case 200:
                            String message;
                            if(isPositive){
                                message = "Te gusta esta respuesta";
                                btnLike.setVisibility(View.INVISIBLE);
                                btnDislike.setVisibility(View.INVISIBLE);
                                btnRemoveScore.setVisibility(View.VISIBLE);
                            }
                            else if(!isPositive){
                                message = "No te gusta esta respuesta.";
                                btnDislike.setVisibility(View.INVISIBLE);
                                btnLike.setVisibility(View.INVISIBLE);
                                btnRemoveScore.setVisibility(View.VISIBLE);

                            }else{
                                message = "Has removido tu puntuación";
                                btnRemoveScore.setVisibility(View.INVISIBLE);
                            }
                            getAnswerScore(id);
                            Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
                            btnLike.setEnabled(true);
                            btnDislike.setEnabled(true);
                            break;
                    }
                }

                @Override
                public void onFailure(Call<Answer> call, Throwable t) {

                }
            });

        }

        private void setTotalScore(int totalScore){
            answerTotalScore = totalScore;

        }

        private void getAnswerScore(String id){
            IAnswer iAnswer = Constants.retrofit.create(IAnswer.class);
            Call<Count> call = iAnswer.getScore(id);
            call.enqueue(new Callback<Count>() {
                @Override
                public void onResponse(Call<Count> call, Response<Count> response) {
                    switch (response.code()){
                        case 200:
                            assert response.body() != null;
                            int scoreTotal = response.body().getPositives() - response.body().getNegatives();
                            if(!isPrivate){
                                score.setText("Puntaje: " + String.valueOf(scoreTotal));
                                setTotalScore(scoreTotal);
                            }

                    }
                }

                @Override
                public void onFailure(Call<Count> call, Throwable t) {

                }
            });

        }



        public void asignData(Answer answer, int position) {
            getAnswerScore(answer.get_id());
            if(scoredAnswers!=null && position< scoredAnswers.size()){
                for (int i = 0; i <scoredAnswers.size() ; i++) {
                    if(scoredAnswers.get(i).getAnswer().equals(answer.get_id()) && scoredAnswers.get(i).getUser().equals(preferences.getString(context.getString(R.string.sharedP), "xd"))){
                        btnLike.setVisibility(View.INVISIBLE);
                        btnDislike.setVisibility(View.INVISIBLE);

                    }
                }
            }
            userAPI userAPI = Constants.retrofit.create(userAPI.class);
            Call<User> call = userAPI.getUserName(answer.getUser());
            call.enqueue(new Callback<User>() {
                @Override
                public void onResponse(Call<User> call, Response<User> response) {
                    if(response.body()!=null){
                        username.setText(response.body().getName() + " " + response.body().getSurname());
                    }
                }

                @Override
                public void onFailure(Call<User> call, Throwable t) {
                    username.setText(null);
                }
            });
            this.answer.setText(answer.getAnswer());

        }
    }

}
