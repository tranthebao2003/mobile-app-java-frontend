package com.bao.appgame.adapter;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bao.appgame.R;
import com.bao.appgame.activity.GameDetailActivity;
import com.bao.appgame.model.Game;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.ArrayList;
import java.util.List;

public class NewestAdapter extends RecyclerView.Adapter<NewestAdapter.ViewHolder> {
    List<Game> newestGame;

    // khởi tạo với data được truyền vào
    public NewestAdapter(List<Game> newestGame) {
        this.newestGame = newestGame;
    }

    // method trả về ViewHolder
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // chuyển đổi file xml thành view để có thể thao túng những thành phần bên trong
        View inflate = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.viewholder_newest, parent, false);
        // trả về 1 instance của ViewHolder (gọi constructor)
        return new ViewHolder(inflate);
    }

    // bind data vào những viewHolder, onBindViewHolder nó sẽ chạy từ vị trí
    // 0 đến categoryDomains.size() - 1
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.gameName.setText(newestGame.get(position).getGameName());
        holder.gamePrice.setText(String.valueOf(newestGame.get(position).getGamePrice()).replace(".0", " Đ"));

        String baseUrl = "http://10.0.2.2:8080/uploadImgGame/";
        String imageUrl = baseUrl + newestGame.get(position).getGameImg();


        Glide.with(holder.itemView.getContext())
                .load(imageUrl)
                .diskCacheStrategy(DiskCacheStrategy.ALL) // Bộ nhớ đệm cho ảnh
                .placeholder(R.drawable.loading_img) // Ảnh hiển thị khi đang tải (thay bằng ảnh của bạn)
                .into(holder.gameImg);

//      add btn
        holder.addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // chuyền từ màn hình chứa recyclerView này sang GameDetailActivity
                Intent intent = new Intent(holder.itemView.getContext(),
                        GameDetailActivity.class);

                // Gửi dữ liệu (đảm bảo Game implements Serializable)
                intent.putExtra("game_object", newestGame.get(position));
                holder.itemView.getContext().startActivity(intent);
            }
        });
    }

    // kích thước của recycleView
    @Override
    public int getItemCount() {
        return newestGame.size();
    }

    // mapping với những thành phần bên trong viewholder_category.xml
    // mỗi item này là 1 ConstraintLayout bên trong chứa textview và imageview
    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView gameName, gamePrice, addBtn;
        ImageView gameImg;

        // constructor
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            gameName = itemView.findViewById(R.id.gameNameNewest);
            gamePrice = itemView.findViewById(R.id.priceGameNewest);
            gameImg = itemView.findViewById(R.id.picNewest);
            addBtn = itemView.findViewById(R.id.addBtnNewest);
        }
    }
}