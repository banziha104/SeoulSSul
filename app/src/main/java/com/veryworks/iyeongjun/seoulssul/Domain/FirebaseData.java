package com.veryworks.iyeongjun.seoulssul.Domain;

/**
 * Created by iyeongjun on 2017. 8. 27..
 */

public class FirebaseData {
    private String userName;
    private String userImg;
    private String contents;
    private String objectID;
    private float locationLat = 0.0f;
    private float locationLong = 0.0f;
    private String section = null;
    private boolean isFirebase;

    public FirebaseData() {
    }

    public FirebaseData(String userName, String userImg, String contents,  float locationLat, float locationLong, String section, boolean isFirebase) {
        this.userName = userName;
        this.userImg = userImg;
        this.contents = contents;
        this.locationLat = locationLat;
        this.locationLong = locationLong;
        this.section = section;
        this.isFirebase = isFirebase;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public String getObjectID() {
        return objectID;
    }

    public void setObjectID(String objectID) {
        this.objectID = objectID;
    }

    public float getLocationLat() {
        return locationLat;
    }

    public void setLocationLat(float locationLat) {
        this.locationLat = locationLat;
    }

    public float getLocationLong() {
        return locationLong;
    }

    public void setLocationLong(float locationLong) {
        this.locationLong = locationLong;
    }


    public boolean isFirebase() {
        return isFirebase;
    }

    public void setFirebase(boolean firebase) {
        isFirebase = firebase;
    }

    public String getUserImg() {
        return userImg;
    }

    public void setUserImg(String userImg) {
        this.userImg = userImg;
    }

    public String getSection() {
        return section;
    }

    public void setSection(String section) {
        this.section = section;
    }
}
