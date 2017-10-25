package com.veryworks.iyeongjun.seoulssul;

import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.location.Location;
import android.opengl.Matrix;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.veryworks.iyeongjun.seoulssul.Domain.ARPoint;
import com.veryworks.iyeongjun.seoulssul.helper.LocationHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Handler;


/**
 * Created by ntdat on 1/13/17.
 */

public class AROverlayView extends View implements ARActivity.CheckView{

    Context context;
    private float[] rotatedProjectionMatrix = new float[16];
    private Location currentLocation;
    private List<ARPoint> arPoints;
    private DisplayMetrics dm = getResources().getDisplayMetrics();
    int width = dm.widthPixels;
    int height = dm.heightPixels;
    boolean[] arr;
    public AROverlayView(Context context) {
        super(context);

        this.context = context;

        //Demo points
        arPoints = new ArrayList<ARPoint>() {{
            add(new ARPoint("Sin sa", 37.516174, 127.019510, 0));
            Log.d("Ar","Location Create");
        }};
        arr = new boolean[arPoints.size()];
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
            Log.d("LOCATION",i+"");
            // cameraCoordinateVector[2] is z, that always less than 0 to display on right position
            // if z > 0, the point will display on the opposite
            if (cameraCoordinateVector[2] < 0) {
                float x  = (0.5f + cameraCoordinateVector[0]/cameraCoordinateVector[3]) * canvas.getWidth();
                float y = (0.5f - cameraCoordinateVector[1]/cameraCoordinateVector[3]) * canvas.getHeight();
                canvas.drawCircle(x, y, radius, paint);
                canvas.drawText(arPoints.get(i).getName(), x - (30 * arPoints.get(i).getName().length() / 2)
                        , y - 80, paint);
//                if(((x < (width/3)*2) && x > (width/3)*1) && ((y < (height/5)*3) && y > (height/5)*2)){
//                    arr[i] = true;
//                    Log.d("AR","x:" + x + "/y:" + y + "/width:" + width + "/height:" + height );
//                }else{
//                    arr[i] = false;
//                }
            }
        }
    }

    public boolean runThread = false;
    public void runTimer(int postion){
        if(!runThread) {
            runThread = true;
            new Thread(){
                public void run(){
                    long start = System.currentTimeMillis();
                    while(System.currentTimeMillis() < start + 1000){

                    }
                }
            }.start();
        }
    }
    @Override
    public void checkView() {

    }
}
