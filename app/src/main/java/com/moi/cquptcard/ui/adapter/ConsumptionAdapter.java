package com.moi.cquptcard.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.balysv.materialripple.MaterialRippleLayout;
import com.moi.cquptcard.R;
import com.moi.cquptcard.model.bean.ConsumptionBean;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * Created by moi on 15/11/28.
 */
public class ConsumptionAdapter extends RecyclerView.Adapter<ConsumptionAdapter.ConsumptionViewHolder> {

    private Context mContext;
    private List<ConsumptionBean> consumptionBeans = new ArrayList<>();

    public ConsumptionAdapter(Context context, List<ConsumptionBean> consumptionBeans) {
        mContext = context;
        this.consumptionBeans = consumptionBeans;
    }

    @Override
    public ConsumptionViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ConsumptionViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_consumption, parent, false));
    }

    @Override
    public void onBindViewHolder(ConsumptionViewHolder holder, int position) {
        ConsumptionBean consumption = consumptionBeans.get(position);
        String time = consumption.getTime();
        String type = consumption.getType();
        String money = consumption.getMoney();
        String balance = consumption.getBalance();
        String place = consumption.getPlace();
        String placeDetail = consumption.getPlaceDetail();

        holder.time.setText(time);
        holder.type.setText(type);
        holder.money.setText(money);
        holder.balance.setText(balance);
        holder.place.setText(place);
        holder.placeDetail.setText(placeDetail);

    }

    @Override
    public int getItemCount() {
        return consumptionBeans.size();
    }

    static class ConsumptionViewHolder extends RecyclerView.ViewHolder {

        MaterialRippleLayout ripple;
        TextView time;
        TextView type;
        TextView money;
        TextView balance;
        TextView place;
        TextView placeDetail;

        public ConsumptionViewHolder(View itemView) {
            super(itemView);
            time = (TextView) itemView.findViewById(R.id.tv_time);
            type = (TextView) itemView.findViewById(R.id.tv_type);
            money = (TextView) itemView.findViewById(R.id.tv_money);
            balance = (TextView) itemView.findViewById(R.id.tv_balance);
            place = (TextView) itemView.findViewById(R.id.tv_place);
            placeDetail = (TextView) itemView.findViewById(R.id.tv_place_detail);
            ripple = (MaterialRippleLayout) itemView.findViewById(R.id.ripple);
        }
    }
}
