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
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.veryworks.iyeongjun.seoulssul.Domain.ARPoint;
import com.veryworks.iyeongjun.seoulssul.Domain.Const;
import com.veryworks.iyeongjun.seoulssul.Domain.TempFirebaseDatabase;
import com.veryworks.iyeongjun.seoulssul.helper.LocationHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import static com.veryworks.iyeongjun.seoulssul.MainActivity.firebaseList;


/**
 * Created by ntdat on 1/13/17.
 */

public class AROverlayView extends View implements ARActivity.CheckView{
    boolean isRedirected = false;
    ImageSet imageSet;
    AppCompatActivity context;
    private float[] rotatedProjectionMatrix = new float[16];
    private Location currentLocation;
    private List<ARPoint> arPoints = new ArrayList<>();
    private DisplayMetrics dm = getResources().getDisplayMetrics();
    int width = dm.widthPixels;
    int height = dm.heightPixels;
    boolean[] arr;
    boolean[] temparr;

    public AROverlayView(Context context) {
        super(context);
        isRedirected = false;
        this.context = (AppCompatActivity)context;
        imageSet = (ImageSet)context;
        setPoint(firebaseList);
        imageSet.setOutImage();
        imageSet.setInImage();
        imageSet.setOutImage();

        //Demo points
        arr = new boolean[arPoints.size()];
        temparr = new boolean[arPoints.size()];
        for(boolean bool : temparr) bool = false;
        Toast.makeText(context, "화면에 안나타날 경우 " + "\n" +
                "뒤로가기 후 다시 눌러주세요", Toast.LENGTH_LONG).show();
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

    boolean currentImage = Const.AR.OUT_IMAGE;
    int count = 0;
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
                canvas.drawText(getDistance(arPoints.get(i).getLocation())+"km", x - (30 * arPoints.get(i).getName().length() / 2)
                        , y + 80, paint);
                if(((x < (width/3)*2) && x > (width/3)*1) && ((y < (height/5)*3) && y > (height/5)*2)){
                    arr[i] = true;
                    count++;
                    Log.d("AR","x:" + x + "/y:" + y + "/width:" + width + "/height:" + height );
                }else{
                    arr[i] = false;
                }
            }
        }
        if(count == 0 && currentImage != Const.AR.OUT_IMAGE){
            currentImage = Const.AR.OUT_IMAGE;
            imageSet.setOutImage();
            Log.d("ARImage","Set Out Image");
        } else if(count != 0 && currentImage != Const.AR.IN_IMAGE){
            currentImage = Const.AR.IN_IMAGE;
            imageSet.setInImage();
            Log.d("ARImage","Set In Image");
        }
        count = 0;
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
                            intent.putExtra("position",i);
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
    private void setPoint(ArrayList<TempFirebaseDatabase> datas){
        Random random = new Random();
        for (TempFirebaseDatabase firebaseDatabase : datas){
            ARPoint point = new ARPoint(firebaseDatabase.title,
                    firebaseDatabase.locationLat,
                    firebaseDatabase.locationLong,
                    ((double)(random.nextInt()%150)));
            arPoints.add(point);
        }
    }
    private double getDistance(Location location){
        return ((int)(currentLocation.distanceTo(location)))/(1000.0);
    }
}
