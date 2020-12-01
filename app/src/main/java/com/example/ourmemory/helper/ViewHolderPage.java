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
<<<<<<< HEAD
        String full_filename = "http://192.168.0.109:8082/java/img/" +fileName;
=======
        String full_filename = "http://192.168.1.21:8085/java/img/" +fileName;
>>>>>>> ca2b89fcb15c2023908eef08c2385eb8e56b0ee2
        Glide.with(context).load(full_filename).into(imageViewItem);
    }
}
