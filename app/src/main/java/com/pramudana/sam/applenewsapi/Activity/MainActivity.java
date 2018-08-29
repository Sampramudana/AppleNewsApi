package com.pramudana.sam.applenewsapi.Activity;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.pramudana.sam.applenewsapi.Model.ArticlesItem;
import com.pramudana.sam.applenewsapi.Model.ResponseNewsApi;
import com.pramudana.sam.applenewsapi.Model.Source;
import com.pramudana.sam.applenewsapi.Network.ApiService;
import com.pramudana.sam.applenewsapi.Network.InstanceRetrofit;
import com.pramudana.sam.applenewsapi.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    RecyclerView rcNews;
    CustomAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //TODO Inlitialize Widget to Variable
        rcNews = findViewById(R.id.rcNews);

        getData();
    }

    private void getData() {
        final ApiService apiService = InstanceRetrofit.getInstance();
        Call<ResponseNewsApi> call = apiService.readNewsApi();
        call.enqueue(new Callback<ResponseNewsApi>() {
            @Override
            public void onResponse(Call<ResponseNewsApi> call, Response<ResponseNewsApi> response) {
                if (response.body().getStatus().equals("ok")) {

                    List<ArticlesItem> articlesItems = response.body().getArticles();
                    adapter = new CustomAdapter(rcNews, MainActivity.this, articlesItems);
                    rcNews.setAdapter(adapter);
                    rcNews.setLayoutManager(new LinearLayoutManager(MainActivity.this));
                }

            }

            @Override
            public void onFailure(Call<ResponseNewsApi> call, Throwable t) {

            }
        });
    }

    private class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder> {

        Context context;
        List<ArticlesItem> articlesItems;

        public CustomAdapter(RecyclerView rcNews, Context context, List<ArticlesItem> articlesItems) {
            this.context = context;
            this.articlesItems = articlesItems;
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(context).inflate(R.layout.list_item, parent, false);
            return new MyViewHolder(view);
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, int position) {

            holder.txtPublish.setText(articlesItems.get(position).getPublishedAt());
            holder.txtTitle.setText(articlesItems.get(position).getTitle());
            holder.txtAuthor.setText(articlesItems.get(position).getAuthor());
            holder.txtDesc.setText(articlesItems.get(position).getDescription());
            Source source = (Source) articlesItems.get(position).getSource();
            holder.txtName.setText(source.getName());

            Glide.with(context)
                    .load(articlesItems.get(position).getUrlToImage())
                    .centerCrop()
                    .into(holder.image);
        }

        @Override
        public int getItemCount() {
            return articlesItems.size();
        }

        public class MyViewHolder extends RecyclerView.ViewHolder {

            TextView txtName, txtTitle, txtAuthor, txtPublish, txtDesc;
            ImageView image;

            public MyViewHolder(View itemView) {
                super(itemView);

                txtTitle = itemView.findViewById(R.id.txtTitle);
                txtName = itemView.findViewById(R.id.txtname);
                txtAuthor = itemView.findViewById(R.id.txtAuthor);
                txtPublish = itemView.findViewById(R.id.txtPublished);
                txtDesc = itemView.findViewById(R.id.txtDescription);
                image = itemView.findViewById(R.id.img);
            }
        }
    }
}
