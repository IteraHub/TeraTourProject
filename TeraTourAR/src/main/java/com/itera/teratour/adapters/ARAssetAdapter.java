package com.itera.teratour.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.itera.teratour.R;
import com.itera.teratour.model.ARAssetModel;

import java.util.List;

public class ARAssetAdapter extends RecyclerView.Adapter<ARAssetAdapter.ARAssetViewHolder> {

    private Context _context;
    private List<ARAssetModel> arAssetData;

    public ARAssetAdapter(Context context, List<ARAssetModel> arAssetData){
        _context = context;
        this.arAssetData = arAssetData;
    }

    @NonNull
    @Override
    public ARAssetViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view;
        LayoutInflater layoutInflater = LayoutInflater.from(_context);
        view = layoutInflater.inflate(R.layout.ar_asset_layout, viewGroup, false);

        return new ARAssetViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ARAssetViewHolder arAssetViewHolder, int i) {

        arAssetViewHolder.arAssetName.setText(arAssetData.get(i).GetAssetName());
        arAssetViewHolder.arAssetThumbnail.setImageResource(arAssetData.get(i).GetAssetThumbnail());

        arAssetViewHolder.assetCardItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(_context, "Image at position " + arAssetViewHolder.getAdapterPosition() + " Clicked", Toast.LENGTH_LONG).show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return arAssetData.size();
    }

    public static class ARAssetViewHolder extends RecyclerView.ViewHolder {

        TextView arAssetName;
        ImageView arAssetThumbnail;
        CardView assetCardItem;

        public ARAssetViewHolder(@NonNull View itemView) {
            super(itemView);

            arAssetName = itemView.findViewById(R.id.asset_name);
            arAssetThumbnail = itemView.findViewById(R.id.asset_image);
            assetCardItem = itemView.findViewById(R.id.asset_card_item);


        }
    }
}
