package com.example.ourmemory.adapter;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.ourmemory.R;
import com.example.ourmemory.model.MemoryDTO;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

// List를 불러오는 매개체가 되는 Adapter
public class MemoryAdapter extends ArrayAdapter<MemoryDTO> {

    Activity activity;
    int resource;


    public MemoryAdapter(@NonNull Context context, int resource, @NonNull List<MemoryDTO> objects) {
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


        String fileName = memoryDTO.getMemory_file();

        String[] array_fileName = fileName.split(", ");
        String full_filename = "http://192.168.0.9:8085/java/img" + "/" + array_fileName[0];

        if (memoryDTO != null) {
            ImageView imageView = convertView.findViewById(R.id.imageView);
            TextView textView1 = convertView.findViewById(R.id.textView1);
            TextView textView2 = convertView.findViewById(R.id.textView2);
            TextView textView3 = convertView.findViewById(R.id.textView3);
            TextView textView4 = convertView.findViewById(R.id.textView4);

            Glide.with(activity).load(full_filename)
                    .into(imageView);

//            imageView.setImageURI(Uri.parse(full_filename));

//            Bitmap myBitmap = BitmapFactory.decodeFile(full_filename2);
//
//            imageView.setImageBitmap(myBitmap);

            textView1.setText(memoryDTO.getMemory_name());
            textView2.setText("\uD83D\uDE0D 좋아요 "+memoryDTO.getMemory_rec()+"개 \uD83D\uDE2B 싫어요 "+memoryDTO.getMemory_nrec()+"개");
            textView3.setText(memoryDTO.getMemory_name());
            textView4.setText(memoryDTO.getMemory_hit()+"명이 조회했어요");

//            textView1.setText("글 제목 : " + memoryDTO.getMemory_subject());
//            textView2.setText("작성자 : " + memoryDTO.getMemory_name());
//            textView3.setText("조회수 : " + memoryDTO.getMemory_hit() + " / " +"추천수 : " + memoryDTO.getMemory_rec() + " / 비추천수 : " +memoryDTO.getMemory_nrec());

        }

        return convertView;
    }
}