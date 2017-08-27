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
import com.veryworks.iyeongjun.seoulssul.Domain.Row;
import com.veryworks.iyeongjun.seoulssul.Domain.ShuffledData;

import java.util.List;

/**
 * Created by myPC on 2017-03-22.
 */

public class CustomAdapter extends ArrayAdapter<ShuffledData>{
    public CustomAdapter(@NonNull Context context, @LayoutRes int resource) {
        super(context, resource);
    }

    @Override
    public int getCount() {
        return super.getCount();
    }

    @Nullable
    @Override
    public ShuffledData getItem(int position) {
        return super.getItem(position);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return super.getView(position, convertView, parent);
    }
}
