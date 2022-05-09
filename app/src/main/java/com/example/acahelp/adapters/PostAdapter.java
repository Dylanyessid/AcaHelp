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
import com.example.acahelp.models.Question;

import java.util.ArrayList;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.ViewHolder> {

    ArrayList<Question> listData;
    Context context;
    public PostAdapter(ArrayList<Question> listData, Context context)
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

        TextView title;
        TextView description;
        String id;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.tVTitle);
            description = itemView.findViewById(R.id.tVDesc);

            Button btnGoToQuestion = itemView.findViewById(R.id.btnLike);
            btnGoToQuestion.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, QuestionDetails.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtra("questionId", id);
                    intent.putExtra("title", title.getText().toString());
                    intent.putExtra("desc", description.getText().toString());
                    System.out.println(description.getText().toString());
                    context.startActivity(intent);
                }
            });
        }

        public void asignData(Question question) {
            title.setText(question.getTitle());
            description.setText(question.getDescription());
             id= question.getId();
        }
    }
}
