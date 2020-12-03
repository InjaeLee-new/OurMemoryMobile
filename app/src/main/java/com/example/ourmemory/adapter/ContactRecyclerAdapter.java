package com.example.ourmemory.adapter;

import android.animation.ValueAnimator;
import android.content.Context;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ourmemory.R;
import com.example.ourmemory.model.ContactUsDTO;

import java.util.ArrayList;

public class ContactRecyclerAdapter extends RecyclerView.Adapter<ContactRecyclerAdapter.ItemViewHolder>{


    private ArrayList<ContactUsDTO> listData;
    private Context context;
    // item의 클릭 상태를 저장할 array 객체
    private SparseBooleanArray selectedItems = new SparseBooleanArray();
    // 직전에 클릭됐던 Item의 position
    private int prePosition = -1;
    public ContactRecyclerAdapter(ArrayList<ContactUsDTO> listData, Context context) {
        this.listData = listData;
        this.context = context;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.contactus_item, parent,false);
        ItemViewHolder holder = new ItemViewHolder(view);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        // Item을 하나, 하나 보여주는(bind 되는) 함수입니다.
        holder.onBind(listData.get(position), position);
//        holder.textViewContact1.setText(listData.get(position).getContactUs_subject());
//        holder.textViewContact2.setText(listData.get(position).getContactUs_logtime());

    }

    @Override
    public int getItemCount() {
        // RecyclerView의 총 개수 입니다.
        return listData.size();
    }

    public void add(ContactUsDTO contactUsDTO) {
        // 외부에서 item을 추가시킬 함수입니다.
        listData.add(contactUsDTO);
    }

    class ItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView textViewContact1;
        TextView textViewContact2;
        TextView textViewContact3;
        ContactUsDTO contactUsDTO;
        LinearLayout layoutContact;
        int position;
        ItemViewHolder(View itemView) {
            super(itemView);

            this.textViewContact1 = itemView.findViewById(R.id.textViewContact1);
            this.textViewContact2 = itemView.findViewById(R.id.textViewContact2);
            this.textViewContact3 = itemView.findViewById(R.id.textViewContact3);
            this.layoutContact = itemView.findViewById(R.id.layoutContact);
        }

        void onBind(ContactUsDTO contactUsDTO, int position) {
            this.position = position;
            this.contactUsDTO = contactUsDTO;

            textViewContact1.setText(contactUsDTO.getContactUs_subject());
            textViewContact2.setText(contactUsDTO.getContactUs_logtime()+"  ➕");
            textViewContact3.setText(contactUsDTO.getContactUs_content());

            changeVisibility(selectedItems.get(position));

            layoutContact.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {
            if (selectedItems.get(position)) {
                // 펼쳐진 Item을 클릭 시
                selectedItems.delete(position);
            } else {
                // 직전의 클릭됐던 Item의 클릭상태를 지움
                selectedItems.delete(prePosition);
                // 클릭한 Item의 position을 저장
                selectedItems.put(position, true);
            }
            // 해당 포지션의 변화를 알림
            if (prePosition != -1) notifyItemChanged(prePosition);
            notifyItemChanged(position);
            // 클릭된 position 저장
            prePosition = position;
        }

        private void changeVisibility(final boolean isExpanded) {
            // height 값을 dp로 지정해서 넣고싶으면 아래 소스를 이용
            int dpValue = 150;
            float d = context.getResources().getDisplayMetrics().density;
            int height = (int) (dpValue * d);

            // ValueAnimator.ofInt(int... values)는 View가 변할 값을 지정, 인자는 int 배열
            ValueAnimator va = isExpanded ? ValueAnimator.ofInt(0, height) : ValueAnimator.ofInt(height, 0);
            // Animation이 실행되는 시간, n/1000초
            va.setDuration(600);
            va.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    // value는 height 값
//                    int value = (int) animation.getAnimatedValue();
                    // imageView의 높이 변경
//                    textViewContact3.getLayoutParams().height = value;
//                    textViewContact3.requestLayout();
                    // imageView가 실제로 사라지게하는 부분
                    textViewContact3.setVisibility(isExpanded ? View.VISIBLE : View.GONE);
                }
            });
            // Animation start
            va.start();
        }
    }


}
