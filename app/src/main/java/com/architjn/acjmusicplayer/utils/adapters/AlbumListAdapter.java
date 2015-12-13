package com.architjn.acjmusicplayer.utils.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.architjn.acjmusicplayer.R;
import com.architjn.acjmusicplayer.task.AlbumItemLoad;
import com.architjn.acjmusicplayer.ui.layouts.activity.AlbumActivity;
import com.architjn.acjmusicplayer.utils.items.Album;

import java.util.ArrayList;

/**
 * Created by architjn on 28/11/15.
 */
public class AlbumListAdapter extends RecyclerView.Adapter<AlbumListAdapter.SimpleItemViewHolder> {
    private static final int ITEM_VIEW_TYPE_HEADER = 0;
    private static final int ITEM_VIEW_TYPE_ITEM = 1;

    private ArrayList<Album> items;
    private Context context;
    private View header;

    public AlbumListAdapter(Context context, ArrayList<Album> items, View header) {
        this.context = context;
        this.header = header;
        this.items = items;
    }


    public boolean isHeader(int position) {
        return position == 0;
    }

    @Override
    public AlbumListAdapter.SimpleItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == ITEM_VIEW_TYPE_HEADER) {
            return new SimpleItemViewHolder(header);
        }
        View itemView = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.album_grid_item, parent, false);
        return new SimpleItemViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final AlbumListAdapter.SimpleItemViewHolder holder, final int position) {
        if (isHeader(position)) {
            return;
        }
        holder.bottomBg.setBackgroundColor(Color.parseColor("#ffffff"));
        holder.name.setText(items.get(position - 1).getAlbumTitle());
        holder.artist.setText(items.get(position - 1).getAlbumArtist());
        new AlbumItemLoad(context, items.get(position - 1).getAlbumArtPath(), holder).execute();
        holder.mainView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(context, AlbumActivity.class);
                i.putExtra("albumName", items.get(position - 1).getAlbumTitle());
                i.putExtra("albumId", items.get(position - 1).getAlbumId());
                context.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size() + 1;
    }

    public class SimpleItemViewHolder extends RecyclerView.ViewHolder {

        public TextView name, artist;
        public ImageView img;
        public View bottomBg, mainView;

        public SimpleItemViewHolder(View itemView) {
            super(itemView);
            mainView = itemView;
            bottomBg = itemView.findViewById(R.id.album_list_bottom);
            name = (TextView) itemView.findViewById(R.id.album_list_name);
            artist = (TextView) itemView.findViewById(R.id.album_list_artist);
            img = (ImageView) itemView.findViewById(R.id.album_list_img);
        }
    }

    @Override
    public int getItemViewType(int position) {
        return isHeader(position) ? ITEM_VIEW_TYPE_HEADER : ITEM_VIEW_TYPE_ITEM;
    }

}