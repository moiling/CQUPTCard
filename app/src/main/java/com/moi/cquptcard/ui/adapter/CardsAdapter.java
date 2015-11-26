package com.moi.cquptcard.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.balysv.materialripple.MaterialRippleLayout;
import com.moi.cquptcard.R;
import com.moi.cquptcard.model.bean.Card;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * Created by moi on 15/11/25.
 */
public class CardsAdapter extends RecyclerView.Adapter<CardsAdapter.CardsViewHolder> {

    private Context mContext;
    private List<Card> cards = new ArrayList<>();

    public CardsAdapter(Context context, List<Card> cards) {
        mContext = context;
        this.cards = cards;
    }

    @Override
    public CardsViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new CardsViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_card, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(CardsViewHolder holder, int i) {
        Card card = cards.get(i);
        String name = card.getName();
        String money = card.getMoney();
        String time = card.getTime();

        holder.name.setText(name);
        holder.money.setText(money);
        holder.time.setText(time);

        holder.ripple.setOnClickListener(v -> Toast.makeText(mContext, "点击了", Toast.LENGTH_SHORT).show());
    }

    @Override
    public int getItemCount() {
        return cards.size();
    }

    class CardsViewHolder extends RecyclerView.ViewHolder {

        MaterialRippleLayout ripple;
        TextView name;
        TextView money;
        TextView time;

        public CardsViewHolder(View itemView) {
            super(itemView);
            money = (TextView) itemView.findViewById(R.id.tv_money);
            time = (TextView) itemView.findViewById(R.id.tv_time);
            name = (TextView) itemView.findViewById(R.id.tv_name);
            ripple = (MaterialRippleLayout) itemView.findViewById(R.id.ripple);
        }
    }
}