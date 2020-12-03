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

import java.util.List;

public class MyListAdapter extends ArrayAdapter<MemoryDTO> {
    Activity activity;
    int resource;

    public MyListAdapter(@NonNull Context context, int resource, @NonNull List<MemoryDTO> objects) {
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

        MemoryDTO memoryDTO = getItem(position);
//        String fileName = memoryDTO.getMemory_file();

//        String[] array_fileName = fileName.split(", ");
//        String full_filename = "http://192.168.0.109:8082/java/img" + "/" + array_fileName[0];

        if (memoryDTO != null) {
//            ImageView imageView = convertView.findViewById(R.id.imageView);
            TextView textView = convertView.findViewById(R.id.textView);

//            Glide.with(activity).load(full_filename)
//                    .into(imageView);

            textView.setText(memoryDTO.getMemory_id()+"님의 글" +memoryDTO.getMemory_subject()+"이 성공적으로 업로드 되었습니다.");

   }
        return convertView;
    }
}
