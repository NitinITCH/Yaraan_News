package com.app.yaraan.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by root on 11/4/18.
 */

public class GetNotification {

    @SerializedName("notifications_id")
    @Expose
    private String notificationsId;
    @SerializedName("new_id")
    @Expose
    private String newId;
    @SerializedName("notifications_message")
    @Expose
    private String notificationsMessage;
    @SerializedName("notifications_image")
    @Expose
    private String notificationsImage;
    @SerializedName("time")
    @Expose
    private String time;

    public String getNotificationsId() {
        return notificationsId;
    }

    public void setNotificationsId(String notificationsId) {
        this.notificationsId = notificationsId;
    }

    public String getNewId() {
        return newId;
    }

    public void setNewId(String newId) {
        this.newId = newId;
    }

    public String getNotificationsMessage() {
        return notificationsMessage;
    }

    public void setNotificationsMessage(String notificationsMessage) {
        this.notificationsMessage = notificationsMessage;
    }

    public String getNotificationsImage() {
        return notificationsImage;
    }

    public void setNotificationsImage(String notificationsImage) {
        this.notificationsImage = notificationsImage;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }


}
