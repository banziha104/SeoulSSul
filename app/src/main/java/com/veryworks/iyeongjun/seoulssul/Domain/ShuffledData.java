package com.veryworks.iyeongjun.seoulssul.Domain;

/**
 * Created by iyeongjun on 2017. 8. 27..
 */

import android.content.Context;
import android.util.Log;

import java.util.ArrayList;

/**
 * 유저 데이터와 서울시 데이터를 섞음
 */
public class ShuffledData {
    Context context;
    Row[] row;
    // 공통
    private String title = null;
    private String contents = null;

    // SeoulData
    private String org_link = null; // 원문 링크
    private String image = null;    // 이미지 주소
    private String gcode = null;    //지역
    private boolean isSeoulData;
    //Firebase

    public ShuffledData() {
    }
    /**
     *
     * @param row
     * @return contents를 반환함
     */
    private String mergeSeoulData(Row row){
        return  "장르 : " + row.getCODENAME() + "\n" +
                "일시 : " + row.getSTRTDATE() + "\n" +             // 일시 : 2017 - 08 - 17
                "장소 : " + row.getGCODE() + " " + row.getPLACE()  + "\n" + // 장소 : 마포구 프리즘홀
                "주최 : " + row.getSPONSOR() + "\n" +
                "주관 및 후원 : " + row.getSUPPORT() + "\n" +
                "자세한 정보 : " + " " + row.getORG_LINK();
    }

    public void getShuffledData(Row[] row){
        for (int i = 0 ; i < row.length ; i++){
            convertRowToShuffled(row,i);
            Log.d("Shuffled",Data.shuffledData.get(i).getTitle());
        }
    }
    /**
     * 서울 데이터를 셔플데이터로 변환
     * @param row
     * @param index
     * @return
     */
    private void convertRowToShuffled(Row[] row, int index){
        ShuffledData data = new ShuffledData();
        data.setTitle(row[index].getTITLE());
        data.setGcode(row[index].getGCODE());
        data.setImage(row[index].getMAIN_IMG());
        data.setOrg_link(row[index].getORG_LINK());
        data.setContents(mergeSeoulData(row[index]));
        data.setSeoulData(true);
        Data.shuffledData.add(data);
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public String getOrg_link() {
        return org_link;
    }

    public void setOrg_link(String org_link) {
        this.org_link = org_link;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getGcode() {
        return gcode;
    }

    public void setGcode(String gcode) {
        this.gcode = gcode;
    }

    public boolean isSeoulData() {
        return isSeoulData;
    }

    public void setSeoulData(boolean seoulData) {
        isSeoulData = seoulData;
    }
}
