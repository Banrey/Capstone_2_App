package com.example.capstone2app;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class adapter_recyclerview extends RecyclerView.Adapter<adapter_recyclerview.myViewHolder> {
    private final ForumInterface forumInterface;
    Context context;
    ArrayList<recyclerModel> recyclerModels;

    public adapter_recyclerview(Context context, ArrayList<recyclerModel> recyclerModels, ForumInterface forumInterface ){
        this.context = context;
        this.recyclerModels = recyclerModels;
        this.forumInterface = forumInterface;
    }
    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.recycler_row,viewGroup,false);
        return new myViewHolder(view, forumInterface);
    }

    @Override
    public void onBindViewHolder(@NonNull myViewHolder myViewHolder, int i) {

        myViewHolder.tvContent.setText(recyclerModels.get(myViewHolder.getAdapterPosition()).getPostContent());
        myViewHolder.etTitle.setText(recyclerModels.get(myViewHolder.getAdapterPosition()).getPostTitle());
    }

    @Override
    public int getItemCount() {
        return recyclerModels.size();
    }

    public static class myViewHolder extends RecyclerView.ViewHolder{
        EditText etTitle;
        TextView tvContent;


        public myViewHolder(@NonNull View itemView, ForumInterface forumInterface) {
            super(itemView);

            etTitle = (EditText) itemView.findViewById(R.id.editTextPostTitle);
            tvContent = (TextView) itemView.findViewById(R.id.textViewPostContent);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(forumInterface != null){
                        int pos = getAdapterPosition();

                        if(pos != RecyclerView.NO_POSITION){
                            forumInterface.onItemClick(pos);
                        }
                    }

                }
            });
        }
    }
}
