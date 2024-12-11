package com.bao.appgame.adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import androidx.recyclerview.widget.RecyclerView;

import com.bao.appgame.R;
import com.bao.appgame.model.Game;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class NewestAdapter extends RecyclerView.Adapter<NewestAdapter.ViewHolder> {
    ArrayList<Game> newestGame;

    // khởi tạo với data được truyền vào
    public NewestAdapter(ArrayList<Game> newestGame) {
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
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.gameName.setText(newestGame.get(position).getGameName());
        holder.gamePrice.setText(String.valueOf(newestGame.get(position).getPrice()));

        // getIdentifier(): Hàm này dùng để tìm ID của tài nguyên (resource) dựa vào tên, loại tài nguyên, và package của ứng dụng.
        int drawableResourceId = holder.itemView.getContext().getResources().getIdentifier
                (newestGame.get(position).getGameImg(), "drawable", holder.itemView.getContext().getPackageName());

        // Sử dụng Glide để tải và hiển thị hình ảnh
        Glide.with(holder.itemView.getContext())
                .load(drawableResourceId)
                .into(holder.gameImg);

        // add btn
//        holder.addBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(holder.itemView.getContext(),
//                        ShowDetailActivity.class);
//                intent.putExtra("object", newestGame.get(position));
//                holder.itemView.getContext().startActivity(intent);
//            }
//        });
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