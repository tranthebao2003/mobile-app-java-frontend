package com.bao.appgame.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bao.appgame.R;
import com.bao.appgame.model.ItemGameBuy;

import java.util.ArrayList;

public class ItemBuyListAdapter extends ArrayAdapter<ItemGameBuy> {
    Activity context;
    int idlayout;
    ArrayList<ItemGameBuy> list;

    public ItemBuyListAdapter(Activity context, int idlayout, ArrayList<ItemGameBuy> list) {
        super(context, idlayout,list );
        this.context = context;
        this.idlayout = idlayout;
        this.list = list;
    }
    // hiển thị dữ liệu

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        convertView = inflater.inflate(idlayout,null);
        ItemGameBuy item = list.get(position);
        ImageView imgItem = convertView.findViewById(R.id.imgItem);
        imgItem.setImageResource(item.getImg());
        TextView gameItem = convertView.findViewById(R.id.itemName);
        gameItem.setText(item.getGamename());
        TextView account = convertView.findViewById(R.id.account);
        account.setText(item.getAccount());
        TextView pass = convertView.findViewById(R.id.pass);
        pass.setText(item.getPass());
        return convertView;


    }
}
