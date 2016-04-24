package ru.niyaz.websocket.dto;

import java.io.Serializable;

/**
 * Created by user on 20.04.2016.
 */
public class UserInfo implements Serializable {
    private String name;
    private String lastName;
    private String photoURL;
    private Integer userID;
    private String pageURL;

    public UserInfo(String name, String lastName, String photoURL, Integer userID, String pageURL) {
        this.name = name;
        this.lastName = lastName;
        this.photoURL = photoURL;
        this.userID = userID;
        this.pageURL = pageURL;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhotoURL() {
        return photoURL;
    }

    public void setPhotoURL(String photoURL) {
        this.photoURL = photoURL;
    }

    public Integer getUserID() {
        return userID;
    }

    public void setUserID(Integer userID) {
        this.userID = userID;
    }

    public String getPageURL() {
        return pageURL;
    }

    public void setPageURL(String pageURL) {
        this.pageURL = pageURL;
    }
}
