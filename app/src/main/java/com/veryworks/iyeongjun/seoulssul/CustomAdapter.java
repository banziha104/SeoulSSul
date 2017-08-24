package com.veryworks.iyeongjun.seoulssul;

import android.content.Context;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.veryworks.iyeongjun.seoulssul.Domain.Data;

import java.util.List;

/**
 * Created by myPC on 2017-03-22.
 */

public class CustomAdapter extends ArrayAdapter<Data> {

    private Context context;
    List<Data> datas;

//    public CustomAdapter(Context context, List datas) {
//        this.datas = datas;
//        this.context = context;
//        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//    }


    public CustomAdapter(@NonNull Context context, @LayoutRes int resource, @IdRes int textViewResourceId, @NonNull List<Data> objects) {
        super(context, resource, textViewResourceId, objects);
        datas = objects;
        this.context = context;

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View myView =  super.getView(position, convertView, parent);

        Data data = datas.get(position);


        return myView;
    }
}
