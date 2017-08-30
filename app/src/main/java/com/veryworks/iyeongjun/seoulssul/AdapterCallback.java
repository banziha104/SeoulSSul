package com.veryworks.iyeongjun.seoulssul;

import com.veryworks.iyeongjun.seoulssul.Domain.ShuffledData;

import java.util.ArrayList;

/**
 * Created by iyeongjun on 2017. 8. 30..
 */
/**
 * 어뎁터 연결용 인터페이스
 */
public interface AdapterCallback {
    public void callback(ArrayList<ShuffledData> datas);
}
