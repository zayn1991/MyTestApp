package com.developers.stable.mytestapp.ui;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.developers.stable.mytestapp.R;
import com.developers.stable.mytestapp.models.Datum;
import com.squareup.picasso.Picasso;

import java.util.List;

public class AdapterItems extends RecyclerView.Adapter<AdapterItems.ItemViewHolder> {

    private final LayoutInflater mInflater;
    private List<Datum> list; // Cached copy
    private IFragmentList fragmentInterfase;

    public AdapterItems(Context context, IFragmentList fragmentInterfase) {
        mInflater = LayoutInflater.from(context);
        this.fragmentInterfase = fragmentInterfase;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = mInflater.inflate(R.layout.item_list, viewGroup, false);
        return new ItemViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, final int i) {
        Picasso.get()
                .load(!TextUtils.isEmpty(list.get(i).getUrl()) ? list.get(i).getUrl() : null)
                .centerCrop()
                .fit()
                .error(R.drawable.nophotoerror)
                .into(holder.imageView);

        holder.itemId.setText(String.format("%d", i));
        holder.itemTitle.setText(list.get(i).getTitle());
        holder.itemId.getRootView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Datum item = list.get(i);
                item.setPosition(i);
                fragmentInterfase.openChoosed(item);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (list != null)
            return list.size();
        else return 0;
    }

    public void setData(List<Datum> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    class ItemViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageView;
        private TextView itemId;
        private TextView itemTitle;

        private ItemViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.image);
            itemId = itemView.findViewById(R.id.item_id);
            itemTitle = itemView.findViewById(R.id.item_title);
        }
    }
}
