package com.fabulias.tourismapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;



public class EvaluationPlace extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_evaluation_place);

        RecyclerView rv= (RecyclerView)findViewById(R.id.rv);

        

        LinearLayoutManager llm = new LinearLayoutManager(this);
        rv.setLayoutManager(llm);

        initilizeData();
        ComentarioAdapter adapter = new ComentarioAdapter(comment);
        rv.setAdapter(adapter);

    }


    public class Comentario {
        private String nameUser;
        private String commentUser;

        Comentario(String nameuser, String commentuser){
            this.nameUser=nameuser;
            this.commentUser=commentuser;

        }
        String getNameUser(){
            return nameUser;
        }
        String getCommentUser(){
            return commentUser;
        }

    }
    private List<Comentario> comment;

    private void initilizeData(){
        comment = new ArrayList<>();
        comment.add(new Comentario("Maximiliano", "Buen lugar es lindo"));
        comment.add(new Comentario("Farid", "Estaba bonito el lugar"));

    }

    public static class ComentarioAdapter extends RecyclerView.Adapter<EvaluationPlace.ComentarioAdapter.ComentarioViewHolder>{
        private List<EvaluationPlace.Comentario> items;

        static class ComentarioViewHolder extends RecyclerView.ViewHolder{
            TextView mNameUser;
            TextView mCommentUser;
            ImageView mPhoto;

            ComentarioViewHolder(View v){
                super(v);
                mNameUser=(TextView) v.findViewById(R.id.nameUser);
                mCommentUser=(TextView) v.findViewById(R.id.comentarioUser);
                mPhoto =(ImageView) v.findViewById(R.id.photoUser);
            }
        }
        ComentarioAdapter(List<EvaluationPlace.Comentario> comment){
            this.items=comment;
        }
        @Override
        public int getItemCount() {
            return items.size();
        }

        @Override
        public EvaluationPlace.ComentarioAdapter.ComentarioViewHolder onCreateViewHolder(ViewGroup viewGroup, int i ){
            View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.fragment_comment, viewGroup, false);
            return new EvaluationPlace.ComentarioAdapter.ComentarioViewHolder(v);
        }

        @Override
        public void onBindViewHolder(EvaluationPlace.ComentarioAdapter.ComentarioViewHolder viewHolder, int i ){
            viewHolder.mNameUser.setText(items.get(i).getNameUser());
            viewHolder.mCommentUser.setText(items.get(i).getCommentUser());
        }

    }



}
