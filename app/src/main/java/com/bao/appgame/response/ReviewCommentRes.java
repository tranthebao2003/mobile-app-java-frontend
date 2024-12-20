package com.bao.appgame.response;

import com.bao.appgame.model.UserInReviewComment;
import com.google.gson.annotations.SerializedName;

public class ReviewCommentRes {
    private Long reviewId;
    private Integer score;
    private String comment;

    @SerializedName("user")
    private UserInReviewComment userInReviewComment;

    public Long getReviewId() {
        return reviewId;
    }

    public Integer getScore() {
        return score;
    }

    public String getComment() {
        return comment;
    }

    public UserInReviewComment getUserInReviewComment() {
        return userInReviewComment;
    }
}
