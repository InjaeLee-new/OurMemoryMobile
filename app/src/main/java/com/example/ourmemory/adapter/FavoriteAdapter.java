package com.example.ourmemory.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.example.ourmemory.R;
import com.example.ourmemory.model.MemoryDTO;
import com.example.ourmemory.model.RecommandDTO;

import java.util.List;

public class FavoriteAdapter extends ArrayAdapter<RecommandDTO> {
    Activity activity;
    int resource;

    public FavoriteAdapter(@NonNull Context context, int resource, @NonNull List<RecommandDTO> objects) {
        super(context, resource, objects);
        activity = (Activity) context;
        this.resource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = activity.getLayoutInflater().inflate(resource, null);
        }

        RecommandDTO recommandDTO = getItem(position);

        if (recommandDTO != null) {
            TextView textView = convertView.findViewById(R.id.textView);
            textView.setText(recommandDTO.getRecommand_id() + "님이 추천 / 비추천하신 글입니다.");

        }
        return convertView;
    }
}
