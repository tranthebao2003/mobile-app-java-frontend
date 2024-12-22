package com.bao.appgame.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bao.appgame.R;
import com.bao.appgame.model.ItemOrderList;

import java.util.ArrayList;

public class ListOrderAdapter extends ArrayAdapter<ItemOrderList> {

    Activity context;
    int idlayout;
    ArrayList<ItemOrderList> list;

    public ListOrderAdapter(Activity context, int idlayout, ArrayList<ItemOrderList> list) {
        super(context,idlayout,list);
        this.context = context;
        this.idlayout = idlayout;
        this.list = list;
    }
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        convertView = inflater.inflate(idlayout,null);
        ItemOrderList item = list.get(position);
        TextView gameItem = convertView.findViewById(R.id.itemName);
        gameItem.setText(item.getGamename());
        TextView account = convertView.findViewById(R.id.account);
        account.setText(item.getAccount());
        TextView pass = convertView.findViewById(R.id.pass);
        pass.setText(item.getPass());
        TextView price = convertView.findViewById(R.id.price);
        price.setText(item.getPrice());

        return convertView;
    }
}
