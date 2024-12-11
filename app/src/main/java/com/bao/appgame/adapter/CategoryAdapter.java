package com.bao.appgame.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bao.appgame.R;
import com.bao.appgame.model.Category;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {
    ArrayList<Category> categoryList;

    // khởi tạo với data được truyền vào
    public CategoryAdapter(ArrayList<Category> categoryList) {
        this.categoryList = categoryList;
    }

    // method trả về ViewHolder
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // chuyển đổi file xml thành view để có thể thao túng những thành phần bên trong
        View inflate = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.viewholder_category, parent, false);
        // trả về 1 instance của ViewHolder (gọi constructor)
        return new ViewHolder(inflate);
    }

    // bind data vào những viewHolder, onBindViewHolder nó sẽ chạy từ vị trí
    // 0 đến categoryDomains.size() - 1
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.categoryName.setText(categoryList.get(position).getCategoryName());
        String picUrl = "category1";
        holder.mainLayout.setBackground(ContextCompat
                .getDrawable(holder.itemView.getContext(),
                        R.drawable.category_background));

        // getIdentifier(): Hàm này dùng để tìm ID của tài nguyên (resource) dựa vào tên, loại tài nguyên, và package của ứng dụng.
        int drawableResourceId = holder.itemView.getContext().getResources().getIdentifier
                (picUrl, "drawable", holder.itemView.getContext().getPackageName());

        // Sử dụng Glide để tải và hiển thị hình ảnh
        Glide.with(holder.itemView.getContext())
                .load(drawableResourceId)
                .into(holder.categoryPic);
    }

    // kích thước của recycleView
    @Override
    public int getItemCount() {
        return categoryList.size();
    }

    // mapping với những thành phần bên trong viewholder_category.xml
    // mỗi item này là 1 ConstraintLayout bên trong chứa textview và imageview
    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView categoryName;
        ImageView categoryPic;
        ConstraintLayout mainLayout;

        // constructor
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            categoryName = itemView.findViewById(R.id.categoryName);
            categoryPic = itemView.findViewById(R.id.categoryPic);
            mainLayout = itemView.findViewById(R.id.mainLayout);
        }
    }
}