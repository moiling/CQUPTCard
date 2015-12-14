package com.moi.cquptcard.ui.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.balysv.materialripple.MaterialRippleLayout;
import com.moi.cquptcard.R;
import com.moi.cquptcard.model.bean.Card;
import com.moi.cquptcard.ui.activity.ConsumptionActivity;
import com.moi.cquptcard.util.DatabaseUtils;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * Created by moi on 15/11/25.
 */
public class CardsAdapter extends RecyclerView.Adapter<CardsAdapter.CardsViewHolder> {

    private Context mContext;
    private List<Card> cards = new ArrayList<>();
    private boolean shouldDelete = false;
    private boolean isDeleting = false;

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

        holder.ripple.setOnClickListener(v -> ConsumptionActivity.actionStart(mContext, card.getCardId()));
        holder.ripple.setOnLongClickListener(v -> {
            shouldDelete = true;
            if (!isDeleting) {
                isDeleting = true;
                cards.remove(i);
                // 这里通知第i个元素被删除了，但是它只是执行动画，并没有更改保存的数据
                notifyItemRemoved(i);
                // 通知第i个元素之后的所有元素都改变了，这里改变数据
                notifyItemRangeChanged(i, cards.size());
                Snackbar snackbar = Snackbar.make(((Activity) mContext).findViewById(R.id.home_fab), "已删除" + name, Snackbar.LENGTH_LONG)
                        .setAction("UNDO", view -> {
                            shouldDelete = false;
                            cards.add(i, card);
                            notifyItemInserted(i);
                            notifyItemRangeChanged(i, cards.size());
                            isDeleting = false;
                        })
                        .setActionTextColor(mContext.getResources().getColor(R.color.accent_color));
                snackbar.setCallback(new Snackbar.Callback() {
                    @Override
                    public void onDismissed(Snackbar snackbar, int event) {
                        super.onDismissed(snackbar, event);
                        if (shouldDelete) {
                            DatabaseUtils.deleteCard(card);
                            isDeleting = false;
                        }
                    }
                });
                snackbar.show();
            }
            return false;
        });
    }

    @Override
    public int getItemCount() {
        return cards.size();
    }

    static class CardsViewHolder extends RecyclerView.ViewHolder {

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