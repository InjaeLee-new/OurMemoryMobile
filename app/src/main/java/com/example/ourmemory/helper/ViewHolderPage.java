package com.example.ourmemory.helper;


import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.ourmemory.R;

public class ViewHolderPage extends RecyclerView.ViewHolder {
    private ImageView imageViewItem;

    public ViewHolderPage(@NonNull View itemView) {
        super(itemView);
        imageViewItem = itemView.findViewById(R.id.imageViewItem);
    }

    public void onBind(String fileName, Context context){
        String full_filename = "http://192.168.0.109:8082/java/img/" +fileName;
        Glide.with(context).load(full_filename).into(imageViewItem);
    }
}
