package com.veryworks.iyeongjun.seoulssul;

import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.location.Location;
import android.opengl.Matrix;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.veryworks.iyeongjun.seoulssul.Domain.ARPoint;
import com.veryworks.iyeongjun.seoulssul.helper.LocationHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;


/**
 * Created by ntdat on 1/13/17.
 */

public class AROverlayView extends View implements ARActivity.CheckView{
    boolean isRedirected = false;
    AppCompatActivity context;
    private float[] rotatedProjectionMatrix = new float[16];
    private Location currentLocation;
    private List<ARPoint> arPoints;
    private DisplayMetrics dm = getResources().getDisplayMetrics();
    int width = dm.widthPixels;
    int height = dm.heightPixels;
    boolean[] arr;
    boolean[] temparr;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("boardData");

    public AROverlayView(Context context) {
        super(context);
        isRedirected = false;
        this.context = (AppCompatActivity)context;
        //Demo points
        arPoints = new ArrayList<ARPoint>() {{
            add(new ARPoint("Sin sa", 37.516174, 127.019510, 0));
            Log.d("Ar","Location Create");
        }};
        arr = new boolean[arPoints.size()];
        temparr = new boolean[arPoints.size()];
        for(boolean bool : temparr) bool = false;
        setTimer();
    }


    public void updateRotatedProjectionMatrix(float[] rotatedProjectionMatrix) {
        this.rotatedProjectionMatrix = rotatedProjectionMatrix;
        this.invalidate();
    }

    public void updateCurrentLocation(Location currentLocation){
        this.currentLocation = currentLocation;
        this.invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (currentLocation == null) {
            return;
        }

        final int radius = 30;
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.WHITE);
        paint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));
        paint.setTextSize(60);

        for (int i = 0; i < arPoints.size(); i ++) {
            float[] currentLocationInECEF = LocationHelper.WSG84toECEF(currentLocation);
            float[] pointInECEF = LocationHelper.WSG84toECEF(arPoints.get(i).getLocation());
            float[] pointInENU = LocationHelper.ECEFtoENU(currentLocation, currentLocationInECEF, pointInECEF);

            float[] cameraCoordinateVector = new float[4];
            Matrix.multiplyMV(cameraCoordinateVector, 0, rotatedProjectionMatrix, 0, pointInENU, 0);
            // cameraCoordinateVector[2] is z, that always less than 0 to display on right position
            // if z > 0, the point will display on the opposite
            if (cameraCoordinateVector[2] < 0) {
                float x  = (0.5f + cameraCoordinateVector[0]/cameraCoordinateVector[3]) * canvas.getWidth();
                float y = (0.5f - cameraCoordinateVector[1]/cameraCoordinateVector[3]) * canvas.getHeight();
                canvas.drawCircle(x, y, radius, paint);
                canvas.drawText(arPoints.get(i).getName(), x - (30 * arPoints.get(i).getName().length() / 2)
                        , y - 80, paint);
                if(((x < (width/3)*2) && x > (width/3)*1) && ((y < (height/5)*3) && y > (height/5)*2)){
                    arr[i] = true;
                    Log.d("AR","x:" + x + "/y:" + y + "/width:" + width + "/height:" + height );
                }else{
                    arr[i] = false;
                }
            }
        }
    }
    @Override
    public void checkView() {

    }
    private void setTimer(){
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                Log.d("LOCATION","timetask");
                for (int i = 0 ; i < arr.length ; i ++){

                    if(arr[i] && temparr[i]){
                        if (!isRedirected) {
                            Intent intent = new Intent(context,DetailActivity.class);
                            context.startActivity(intent);
                            context.finish();
                            isRedirected = true;
                        }
                    }
                        temparr[i] = arr[i];
                    }
                }
        },0,1000);
    }
}
