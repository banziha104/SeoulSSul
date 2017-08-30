package com.veryworks.iyeongjun.seoulssul;

import android.content.Context;
import android.media.Image;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.veryworks.iyeongjun.seoulssul.Domain.Data;
import com.veryworks.iyeongjun.seoulssul.Domain.Row;
import com.veryworks.iyeongjun.seoulssul.Domain.ShuffledData;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by myPC on 2017-03-22.
 */

public class CustomAdapter extends ArrayAdapter<ShuffledData>{
    List<ShuffledData> datas;
    Context context;

    public CustomAdapter(@NonNull Context context, @LayoutRes int resource, @IdRes int textViewResource,@NonNull List<ShuffledData> objects) {
        super(context, resource, objects);
        datas = objects;
        for(int i = 0 ; i < datas.size() ; i++){
            Log.d("Adapter",datas.get(i).getContents());
        }
        this.context = context;

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView,  @NonNull ViewGroup parent) {
        View view= super.getView(position, convertView, parent);
        ShuffledData data = datas.get(position);
        TextView txtTitle = view.findViewById(R.id.txtTitle);
        TextView txtContents =  view.findViewById(R.id.txtContents);
        ImageView imageView =  view.findViewById(R.id.imageView);

        txtTitle.setText(data.getTitle());
        txtContents.setText(data.getContents());
        Glide.with(context).load(data.getImage()).into(imageView);
        return view;
    }
    //    ArrayList<ShuffledData> datas;
//    LayoutInflater inflater;
//    Context context;
//    public CustomAdapter(ArrayList<ShuffledData> datas,Context context) {
//        this.datas = datas;
//        this.context = context;
//        this.inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//    }
//
//    @Override
//    public int getCount() {
//        return datas.size();
//    }
//
//    @Override
//    public Object getItem(int position) {
//        return datas.get(position);
//    }
//
//    @Override
//    public long getItemId(int position) {
//        return position;
//    }
//
//    @Override
//    public View getView(int postion, View convertView, ViewGroup parent) {
//        Holder holder;
//        if(convertView == null){
//            convertView = inflater.inflate(R.layout.item,null);
//            holder = new Holder(convertView,context);
//            convertView.setTag(holder);
//        }else{
//            holder = (Holder) convertView.getTag();
//        }
//        ShuffledData data = datas.get(postion);
//        holder.setPostion(postion);
//        holder.setTxtTitle(data.getTitle());
//        holder.setTxtContents(data.getContents());
//        return convertView;
//    }
//
//    /**
//     * 홀더 패턴
//     */
//    class Holder{
//        int postion;
//        @BindView(R.id.txtTitle) TextView txtTitle;
//        @BindView(R.id.txtContents) TextView txtContents;
//        @BindView(R.id.imageView) ImageView imageView;
//        int resID;
//        public Holder(View view, final Context context){
//            ButterKnife.bind(this,view);
//        }
//        public void setPostion(int postion){
//            this.postion = postion;
//        }
//
//        public void setTxtTitle(String title) {
//            this.txtTitle.setText(title);
//        }
//
//        public void setTxtContents(String contents) {
//            this.txtContents.setText(contents);
//        }
//
//        public void setImageView(ImageView imageView) {
//            this.imageView = imageView;
//        }
//
//        public void setResID(int resID) {
//            this.resID = resID;
//        }
//    }
}
