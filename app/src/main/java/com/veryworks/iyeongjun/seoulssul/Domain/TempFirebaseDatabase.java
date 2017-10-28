package com.veryworks.iyeongjun.seoulssul.Domain;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by iyeongjun on 2017. 10. 27..
 */

@IgnoreExtraProperties
public class TempFirebaseDatabase {
    public String contents;
    public boolean firebase;
    public Double locationLat;
    public Double locationLong;
    public String section;
    public String title;
    public Map<String,Object> stars = new HashMap<>();
    public TempFirebaseDatabase(){
    }

    public TempFirebaseDatabase(String contents, boolean firebase, Double locationLat, Double locationLong, String section, String title) {
        this.contents = contents;
        this.firebase = firebase;
        this.locationLat = locationLat;
        this.locationLong = locationLong;
        this.section = section;
        this.title = title;
    }
    @Exclude
    public Map<String, Object> toMap(){
        HashMap<String, Object> result = new HashMap<>();
        result.put("contents",contents);
        result.put("firebase",firebase);
        result.put("locationLat",locationLat);
        result.put("locationLong",locationLong);
        result.put("section",section);
        result.put("title",title);
        return result;
    }

}
