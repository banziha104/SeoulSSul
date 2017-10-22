package com.veryworks.iyeongjun.seoulssul.Domain;

/**
 * Created by iyeongjun on 2017. 8. 24..
 */

/**
 * 기호상수 클래스
 */
public class Const {
    public static class Key{
        public static final String SEOUL_API_KEY = "6d59646256646c6431303073497a5353";
        public static final String FACEBOOK_APP_ID = "487874731592926";
        public static final String FACEBOOK_CODE ="54b8b3be4b99d2f54300e7d609a1a8b5,https://seoulssul.firebaseapp.com/__/auth/handler";
        public static final String GOOGLE_MAP_KEY ="AIzaSyChOAuUgCbADQ5vcgzOloV0J6-SloAASWk";
        public static final String KAKAO_API_KEY = "7bb96e6dddb70dad97e62f352c848d36";
    }
    public static class DataType{
        public static final boolean SEOUL_DATA = true;
        public static final boolean USER_DATA = false;
    }
    public static class Num{
        public static final int NEXT_PAGE = 100;
        public static final int IMG_LENGTH = 28;
    }
    public static class Guest{
        public static final String GUEST_NAME = "GUEST";
        public static final String GUEST_EMAIL = "guest@guest.com";
        public static final String GUEST_PASSWORD = "id3dkgajkdl93910";
    }
    public static class GPS{
        public static final int GPS_MILE_SECOND = 100;
        public static final int GPS_MIN_LENGTH = 1;
        public static final int GPS_DEFAULT_RESULT = 1;

    }
    public static class AR{
        public final static int REQUEST_CAMERA_PERMISSIONS_CODE = 11;
        public static final int REQUEST_LOCATION_PERMISSIONS_CODE = 0;

        public static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 0; // 10 meters
        public static final long MIN_TIME_BW_UPDATES = 0;//1000 * 60 * 1; // 1 minute

    }

}
