package com.app.yaraan.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by root on 28/3/18.
 */

public class GetNews {
    @SerializedName("details_id")
    @Expose
    private String detailsId;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("image")
    @Expose
    private String image;
    @SerializedName("author_name")
    @Expose
    private String authorName;
    @SerializedName("author_image")
    @Expose
    private String authorImage;
    @SerializedName("time")
    @Expose
    private String time;
    @SerializedName("new_likes")
    @Expose
    private String newLikes;
    @SerializedName("comment_count")
    @Expose
    private String commentCount;
    @SerializedName("gggggggg")
    @Expose
    private String gggggggg;

    public String getDetailsId() {
        return detailsId;
    }

    public void setDetailsId(String detailsId) {
        this.detailsId = detailsId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getAuthorName() {
            return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public String getAuthorImage() {
        return authorImage;
    }

    public void setAuthorImage(String authorImage) {
        this.authorImage = authorImage;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getNewLikes() {
        return newLikes;
    }

    public void setNewLikes(String newLikes) {
        this.newLikes = newLikes;
    }

    public String getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(String commentCount) {
        this.commentCount = commentCount;
    }

    public String getGggggggg() {
        return gggggggg;
    }

    public void setGggggggg(String gggggggg) {
        this.gggggggg = gggggggg;
    }


}
