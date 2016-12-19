package com.fabulias.tourismapp;


import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class EvaluationFragment extends Fragment{

    public EvaluationFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        List<Comentario> items = new ArrayList<Comentario>();
        items.add(new Comentario("Maximiliano","El lugar era hermoso"));
        items.add(new Comentario("Farid","La atención era mala"));

        RecyclerView recycler = (RecyclerView) container.findViewById(R.id.rv);
        recycler.setHasFixedSize(true);

        RecyclerView.LayoutManager lManager = new LinearLayoutManager(this.getContext());
        recycler.setLayoutManager(lManager);

        RecyclerView.Adapter<ComentarioAdapter.ComentarioViewHolder> adapter = new ComentarioAdapter(items);
        recycler.setAdapter(adapter);




        return inflater.inflate(R.layout.fragment_evaluation, container, false);
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
    public static class ComentarioAdapter extends RecyclerView.Adapter<ComentarioAdapter.ComentarioViewHolder>{
        private List<Comentario> items;

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
        ComentarioAdapter(List<Comentario> items){
            this.items=items;
        }
        @Override
        public int getItemCount() {
            return items.size();
        }

        @Override
        public ComentarioViewHolder onCreateViewHolder(ViewGroup viewGroup, int i ){
            View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.fragment_comment, viewGroup, false);
            return new ComentarioViewHolder(v);
        }

        @Override
        public void onBindViewHolder(ComentarioViewHolder viewHolder, int i ){
            viewHolder.mNameUser.setText(items.get(i).getNameUser());
            viewHolder.mCommentUser.setText(items.get(i).getCommentUser());
        }
    }





}
