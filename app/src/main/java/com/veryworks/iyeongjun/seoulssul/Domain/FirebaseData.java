package com.veryworks.iyeongjun.seoulssul.Domain;

/**
 * Created by iyeongjun on 2017. 8. 27..
 */

public class FirebaseData {
    private String userName;
    private String contents;
    private String objectID;
    private float locationLat = 0.0f;
    private float locationLong = 0.0f;
    private String gCode = null;
    private boolean isFirebase;

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

    public String getgCode() {
        return gCode;
    }

    public void setgCode(String gCode) {
        this.gCode = gCode;
    }

    public boolean isFirebase() {
        return isFirebase;
    }

    public void setFirebase(boolean firebase) {
        isFirebase = firebase;
    }
}
