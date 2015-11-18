package de.uni_koblenz.mbrack.unikoblenzmensa;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class MenuAdapter extends RecyclerView.Adapter {
    public List<Menu> menus;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public CardView cardView;
        public ViewHolder(CardView v) {
            super(v);
            cardView = v;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        CardView newView = ((CardView) LayoutInflater.from(parent.getContext()).inflate(R.layout.menu_item_card_view, parent, false));
        ViewHolder newViewHolder = new ViewHolder(newView);
        return newViewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        CardView cardToAdapt = ((ViewHolder) holder).cardView;
        TextView textToReplace = (TextView) cardToAdapt.findViewById(R.id.menu_item_card_view_text);
        textToReplace.setText(menus.get(0).menuItems.get(position).description);
    }

    @Override
    public int getItemCount() {
        if (menus == null) {
            return 0;
        } else {
            return menus.get(0).menuItems.size();
        }
    }
}
