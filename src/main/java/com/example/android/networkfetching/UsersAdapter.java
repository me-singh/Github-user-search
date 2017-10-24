package com.example.android.networkfetching;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by me_singh on 7/10/17.
 */

public class UsersAdapter extends RecyclerView.Adapter<UsersAdapter.UsersViewHolder> {

    Context context;
    ArrayList<User> users;

    public UsersAdapter(Context context, ArrayList<User> users) {
        this.context = context;
        this.users = users;
    }

    @Override
    public UsersViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.single_user,parent,false);
        return new UsersViewHolder(view);
    }

    @Override
    public void onBindViewHolder(UsersViewHolder holder, int position) {

        User user=users.get(position);

        Picasso.with(context).load(user.avatar).into(holder.avatar);//to download(or load) and set picture in android
        holder.score.setText(user.score);
        holder.id.setText(user.id);
        holder.login.setText(user.login);
        holder.url.setText(user.url);
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    class UsersViewHolder extends RecyclerView.ViewHolder{

        ImageView avatar;
        TextView login,id,url,score;

        public UsersViewHolder(View itemView) {
            super(itemView);

            avatar=itemView.findViewById(R.id.avatar);
            login=itemView.findViewById(R.id.login);
            id=itemView.findViewById(R.id.id);
            url=itemView.findViewById(R.id.url);
            score=itemView.findViewById(R.id.score);
        }
    }
}
