package com.bao.appgame.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bao.appgame.R;
import com.bao.appgame.response.ReviewCommentRes;


import java.util.List;

//CHƯA LÀM
public class ReviewCommentAdapter extends RecyclerView.Adapter<ReviewCommentAdapter.ViewHolder> {
    private List<ReviewCommentRes> reviewCommentRes;

    public ReviewCommentAdapter(List<ReviewCommentRes> reviewCommentRes) {
        this.reviewCommentRes = reviewCommentRes;
    }

    // method trả về ViewHolder
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // chuyển đổi file xml thành view để có thể thao túng những thành phần bên trong
        View inflate = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.viewholder_review, parent, false);
        // trả về 1 instance của ViewHolder (gọi constructor)
        return new ViewHolder(inflate);
    }

    // bind data vào những viewHolder, onBindViewHolder nó sẽ chạy từ vị trí
    // 0 đến categoryDomains.size() - 1
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.fullNameDetail.setText(reviewCommentRes.get(position).getUserInReviewComment().getFullName());
        holder.reviewStartUserDetail.setRating((float)reviewCommentRes.get(position).getScore());
        holder.userReviewTxtDetail.setText(reviewCommentRes.get(position).getComment());
    }

    // kích thước của recycleView
    @Override
    public int getItemCount() {
        return reviewCommentRes.size();
    }

    // mapping với những thành phần bên trong viewholder_category.xml
    // mỗi item này là 1 ConstraintLayout bên trong chứa textview và imageview
    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView fullNameDetail, userReviewTxtDetail;
        RatingBar reviewStartUserDetail;

        // constructor
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            fullNameDetail = itemView.findViewById(R.id.fullNameDetail);
            userReviewTxtDetail = itemView.findViewById(R.id.userReviewTxtDetail);
            reviewStartUserDetail = itemView.findViewById(R.id.reviewStartUserDetail);
        }
    }
}