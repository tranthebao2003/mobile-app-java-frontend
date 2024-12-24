package com.bao.appgame.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bao.appgame.R;
import com.bao.appgame.model.ItemGameBuy;

import java.util.ArrayList;

public class ItemBuyListAdapter extends BaseAdapter {
    private Context context;
    private int idLayout;
    private ArrayList<ItemGameBuy> list;

    public ItemBuyListAdapter(Context context, int idLayout, ArrayList<ItemGameBuy> list) {
        this.context = context;
        this.idLayout = idLayout;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(idLayout, null);

            holder = new ViewHolder();
            holder.imgItem = convertView.findViewById(R.id.imgItem);
            holder.gameItem = convertView.findViewById(R.id.itemName);
            holder.account = convertView.findViewById(R.id.account);
            holder.pass = convertView.findViewById(R.id.pass);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        ItemGameBuy item = list.get(position);
        holder.imgItem.setImageResource(item.getImg());
        holder.gameItem.setText(item.getGamename());
        holder.account.setText(item.getAccount());
        holder.pass.setText(item.getPass());

        return convertView;
    }

    private static class ViewHolder {
        ImageView imgItem;
        TextView gameItem;
        TextView account;
        TextView pass;
    }
}
