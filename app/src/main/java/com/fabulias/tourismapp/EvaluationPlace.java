package com.fabulias.tourismapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;



public class EvaluationPlace extends AppCompatActivity {
    private EvaluationTask mAuthTask=null;
    private TextView mTitleName;
    private EditText mComment;
    private RatingBar mScore;
    private String mNamePlace;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_evaluation_place);

        RecyclerView rv= (RecyclerView)findViewById(R.id.rv);
        Button evaluation=(Button)findViewById(R.id.Evaluation);
        mComment=(EditText) findViewById(R.id.comentario);
        mScore=(RatingBar)findViewById(R.id.scoreUser);
        int id_place=(int) getIntent().getExtras().getSerializable("id");
        String name_place=(String) getIntent().getExtras().getSerializable("name_place");
         mNamePlace=name_place;
        TextView titleName=(TextView)  findViewById(R.id.namePlace);
        RatingBar scorePlace=(RatingBar) findViewById(R.id.scorePlace);

        titleName.setText(name_place);

        //Cambiar
        scorePlace.setNumStars(2);

        evaluation.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                evaluar();
            }
        });

        

        LinearLayoutManager llm = new LinearLayoutManager(this);
        rv.setLayoutManager(llm);

        initilizeData();
        ComentarioAdapter adapter = new ComentarioAdapter(comment);
        rv.setAdapter(adapter);

    }

    private void evaluar() {

        String comment= mComment.getText().toString();
        int score=mScore.getNumStars();
        String fecha="2016-12-19T00:00:00Z";
        SharedPreferences pref= getSharedPreferences("DataUser", Context.MODE_PRIVATE);
        String userName=pref.getString("mail",null);

        mAuthTask= new EvaluationTask(mNamePlace,score,comment,fecha,userName, true);
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



    private class EvaluationTask extends AsyncTask<String,String,String>{
        private final String mNamePlace;
        private final int mScore;
        private final String mComment;

        EvaluationTask(String namePlace, int score, String comment, String fecha, String userName, boolean status){
            mNamePlace=namePlace;
            mScore=score;
            mComment=comment;
        }

        HttpURLConnection urlConnection;
        HttpURLConnection urlCommentConnect;

        @Override
        protected String doInBackground(String... params){
            StringBuilder result= new StringBuilder();
            try{
                URL url = new URL(params[0]);
                urlConnection= (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.setRequestProperty("Content-Type", "application/json");
                return ".";
            }catch(Exception e){
                e.printStackTrace();
            }
            return"";
        }







    }

}
