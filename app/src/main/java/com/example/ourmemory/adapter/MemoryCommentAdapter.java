package com.example.ourmemory.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.example.ourmemory.R;
import com.example.ourmemory.model.MemoryCommentDTO;
import com.example.ourmemory.model.MemoryDTO;
import android.widget.TextView;

import com.example.ourmemory.R;
import com.example.ourmemory.model.MemoryCommentDTO;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

// 댓글창을 불러오는 ListView의 Adapter
public class MemoryCommentAdapter extends ArrayAdapter<MemoryCommentDTO> {

    Activity activity;
    int resource;

    public MemoryCommentAdapter(@NonNull Context context, int resource, @NonNull List<MemoryCommentDTO> objects) {
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

        MemoryCommentDTO memoryCommentDTO = getItem(position);

        if (memoryCommentDTO != null) {
            TextView textView1 = convertView.findViewById(R.id.textView1);
            TextView textView2 = convertView.findViewById(R.id.textView2);
            TextView textView3 = convertView.findViewById(R.id.textView3);

            textView1.setText("\uD83D\uDC8C"+memoryCommentDTO.getMemory_comment_name());
            textView2.setText(memoryCommentDTO.getMemory_comment_content());
            textView3.setText(memoryCommentDTO.getReg_date().substring(0,10));

        }

        return convertView;
    }
}