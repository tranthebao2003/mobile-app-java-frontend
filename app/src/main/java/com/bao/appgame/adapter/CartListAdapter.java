package com.bao.appgame.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bao.appgame.model.CartManager;
import com.bao.appgame.model.Game;
import com.bao.appgame.R;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.List;

public class CartListAdapter extends RecyclerView.Adapter<CartListAdapter.ViewHolder> {
    private List<Game> gameItem;

    public CartListAdapter(List<Game> gameItem) {
        this.gameItem = gameItem;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_cart, parent, false);
        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.gameNameCartItem.setText(gameItem.get(position).getGameName());
        holder.gamePriceCartItem.setText(String.valueOf(gameItem.get(position).getGamePrice()).replace(".0", " Đ"));

        String baseUrl = "http://10.0.2.2:8080/uploadImgGame/";
        String imageUrl = baseUrl + gameItem.get(position).getGameImg();

        holder.btnRemoveCartItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CartManager.getInstance().removeItem(gameItem.get(position));
            }
        });

        Glide.with(holder.itemView.getContext())
                .load(imageUrl)
                .diskCacheStrategy(DiskCacheStrategy.ALL) // Bộ nhớ đệm cho ảnh
                .placeholder(R.drawable.loading_img) // Ảnh hiển thị khi đang tải (thay bằng ảnh của bạn)
                .into(holder.imgCartItem);

    }

    @Override
    public int getItemCount() {
        return gameItem.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView gameNameCartItem, gamePriceCartItem;
        ImageView btnRemoveCartItem, imgCartItem;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            gameNameCartItem = itemView.findViewById(R.id.gameNameCartItem);
            gamePriceCartItem = itemView.findViewById(R.id.gamePriceCartItem);
            btnRemoveCartItem = itemView.findViewById(R.id.btnRemoveCartItem);
            imgCartItem = itemView.findViewById(R.id.gameImgCartItem);
        }
    }
}
