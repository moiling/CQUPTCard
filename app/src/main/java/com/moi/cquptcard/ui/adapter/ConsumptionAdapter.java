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

        holder.time.setText(time);
        // +钱和-钱分开显示
        if (type.equals("消费")) {
            // 先去掉有些本身就有的"-"
            money = money.replace("-", "");
            money = "-" + money;
            holder.money.setTextColor(mContext.getResources().getColor(R.color.accent_color));
        } else {
            if (position + 1 <= consumptionBeans.size() - 1) {
                boolean useMoney = Double.valueOf(consumptionBeans.get(position + 1).getBalance()) - Double.valueOf(balance) > 0;
                // 先去掉有些本身就有的"-"
                money = money.replace("-", "");
                money = useMoney ? "-" + money : "+" + money;
                holder.money.setTextColor(useMoney ? mContext.getResources().getColor(R.color.accent_color) : mContext.getResources().getColor(R.color.primary_blue));
            } else {
                holder.money.setTextColor(mContext.getResources().getColor(R.color.primary_blue));
            }
        }
        holder.money.setText(money);
        if (!place.isEmpty())
            holder.place.setText(place);
        else holder.place.setText("未知地点");
    }

    @Override
    public int getItemCount() {
        return consumptionBeans.size();
    }

    static class ConsumptionViewHolder extends RecyclerView.ViewHolder {

        MaterialRippleLayout ripple;
        TextView time;
        TextView money;
        TextView place;

        public ConsumptionViewHolder(View itemView) {
            super(itemView);
            time = (TextView) itemView.findViewById(R.id.tv_time);
            money = (TextView) itemView.findViewById(R.id.tv_money);
            place = (TextView) itemView.findViewById(R.id.tv_place);
            ripple = (MaterialRippleLayout) itemView.findViewById(R.id.ripple);
        }
    }
}
