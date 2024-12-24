package com.bao.appgame.adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bao.appgame.R;
import com.bao.appgame.activity.HomeActivity;
import com.bao.appgame.model.Category;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {
    private List<Category> categoryList;

    // Lưu giữ tham chiếu tới CategoryClickListener được truyền từ Activity
    private HomeActivity.CategoryClickListener categoryClickListener;

    // khởi tạo với data được truyền vào
    public CategoryAdapter(List<Category> categoryList, HomeActivity.CategoryClickListener categoryClickListener) {
        this.categoryList = categoryList;
        this.categoryClickListener = categoryClickListener;
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
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.categoryName.setText(categoryList.get(position).getCategoryName());
        String baseUrl = "http://10.0.2.2:8080/img/category/";
        String imageUrl = baseUrl + categoryList.get(position).getCategoryImg();

        Glide.with(holder.itemView.getContext())
                .load(imageUrl)
                .diskCacheStrategy(DiskCacheStrategy.ALL) // Bộ nhớ đệm cho ảnh
                .placeholder(R.drawable.loading_img) // Ảnh hiển thị khi đang tải (thay bằng ảnh của bạn)
                .into(holder.categoryPic);

        // gán sự kiện onclick cho từng category item
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // GỌI VÀ TRUYỀN THAM SỐ
                categoryClickListener.onCategoryClick(categoryList.get(position));
            }
        });
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