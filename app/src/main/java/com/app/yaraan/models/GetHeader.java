package com.app.yaraan.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by root on 30/3/18.
 */

public class GetHeader {

    @SerializedName("menu_id")
    @Expose
    private String menuId;
    @SerializedName("menu_name")
    @Expose
    private String menuName;

    public String getMenuId() {
        return menuId;
    }

    public void setMenuId(String menuId) {
        this.menuId = menuId;
    }

    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }
}
