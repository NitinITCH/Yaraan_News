package com.app.yaraan.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by root on 29/3/18.
 */

public class GetDetail {


    @SerializedName("master_id")
    @Expose
    private String masterId;
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
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("time")
    @Expose
    private String time;
    @SerializedName("new_likes")
    @Expose
    private String newLikes;
    @SerializedName("comment_count")
    @Expose
    private String commentCount;
    @SerializedName("status")
    @Expose
    private String status;

    public String getMasterId() {
        return masterId;
    }

    public void setMasterId(String masterId) {
        this.masterId = masterId;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public String getStatus() {
        return status;
    }

    public String setStatus(String status) {
        this.status = status;
        return status;
    }

}
