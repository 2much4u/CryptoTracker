package finalproject.cryptotracker;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class CoinAdapter extends RecyclerView.Adapter<CoinAdapter.viewHolder> {
    Context context;
    List<CoinItem> coins;

    public CoinAdapter(Context setContext, List<CoinItem> setCoins) {
        this.context = setContext;
        this.coins = setCoins;
    }

    @Override
    public viewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.card_item, parent, false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(viewHolder holder, int position) {
        CoinItem coin = coins.get(position);
        holder.coinPicture.setImageResource(coin.getPictureID());
        holder.coinTicker.setText(coin.getTicker());
        holder.coinValue.setText(coin.getCurrentPrice());
        holder.percentChange.setText(coin.getPercentChange());

        int color = Color.GREEN;
        if (coin.getPercentChange().contains("-")) {
            color = Color.RED;
        }
        holder.percentChange.setTextColor(color);
    }

    @Override
    public int getItemCount() {
        return coins.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {
        private ImageView coinPicture;
        private TextView coinTicker;
        private TextView coinValue;
        private TextView percentChange;

        public viewHolder(View itemView) {
            super(itemView);
            coinPicture = itemView.findViewById(R.id.coinPicture);
            coinTicker = itemView.findViewById(R.id.coinTicker);
            coinValue = itemView.findViewById(R.id.coinValue);
            percentChange = itemView.findViewById(R.id.percentChange);
        }
    }
}
