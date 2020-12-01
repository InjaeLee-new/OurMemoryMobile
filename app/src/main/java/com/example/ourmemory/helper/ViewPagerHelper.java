package com.example.ourmemory.helper;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ourmemory.R;


public class ViewPagerHelper extends RecyclerView.Adapter<ViewHolderPage> {
    String[] array_fileName;
    Context context;
    public ViewPagerHelper(String[] array_fileName, Context context) {
        this.array_fileName = array_fileName;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolderPage onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.view_item, parent, false);
        return new ViewHolderPage(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderPage holder, int position) {
        if(holder instanceof ViewHolderPage){
            ViewHolderPage viewHolder = (ViewHolderPage) holder;
            viewHolder.onBind(array_fileName[position], context);
        }
    }

    @Override
    public int getItemCount() {
        return array_fileName.length;
    }
}
