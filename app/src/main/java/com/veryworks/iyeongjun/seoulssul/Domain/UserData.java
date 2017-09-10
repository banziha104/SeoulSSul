package com.veryworks.iyeongjun.seoulssul.Domain;

/**
 * Created by iyeongjun on 2017. 9. 4..
 */

public class UserData {
    private String name;
    private String image_url;
    private Boolean isNextTime;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public Boolean getNextTime() {
        return isNextTime;
    }

    public void setNextTime(Boolean nextTime) {
        isNextTime = nextTime;
    }
}
