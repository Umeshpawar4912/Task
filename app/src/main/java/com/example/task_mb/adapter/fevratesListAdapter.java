package com.example.task_mb.adapter;


import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.task_mb.R;
import com.example.task_mb.datbade.SQLiteDBHelper;
import com.example.task_mb.model.ContactModel;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class fevratesListAdapter extends RecyclerView.Adapter<fevratesListAdapter.MyViewHolder> {

    private List<ContactModel> contactModels = new ArrayList<ContactModel>();
    Context context;
    private RecyclerView recycleView;


    public class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView C_name, C_number, initial;
        LinearLayout clickoncontact;
        ImageView faverate, liked_button;
        CircleImageView imageview;
        private SQLiteDBHelper database;

        public MyViewHolder(View view) {
            super(view);

            database = new SQLiteDBHelper(context);
            C_name = view.findViewById(R.id.fev_contactName);
            C_number = view.findViewById(R.id.fev_contactNumber);
            clickoncontact = view.findViewById(R.id.fev_clickoncontact);
            initial = view.findViewById(R.id.fev_initial);
            faverate = view.findViewById(R.id.fev_like_button);
            liked_button = view.findViewById(R.id.fev_liked_button);
            imageview = view.findViewById(R.id.fev_imageview);
        }
    }

    public fevratesListAdapter(Context context, List<ContactModel> contactModels1) {

        this.context = context;
        this.contactModels = contactModels1;

    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        context = parent.getContext();

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fev_list_view, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {

        final ContactModel contactModel = contactModels.get(position);
        final int itemPosition = holder.getAdapterPosition();
        final SQLiteDBHelper database = new SQLiteDBHelper(context);
        holder.C_name.setText(contactModel.getC_name());
        holder.C_number.setText(contactModel.getC_number());

        String fevrates_sign = contactModel.getC_status();


        String initialName = contactModel.getC_name();
        String afterTrim = initialName.trim().replaceAll(" +", " ");
        String parts[] = afterTrim.split(" ");


        String img_url = contactModel.getC_url();


        if (img_url != null) {
            Uri imageUri = Uri.parse(img_url);
            Bitmap photo1 = null;
            try {
                photo1 = MediaStore.Images.Media.getBitmap(context.getContentResolver(), imageUri);
            } catch (IOException e) {
                e.printStackTrace();
            }
            holder.imageview.setVisibility(View.VISIBLE);
            holder.initial.setVisibility(View.GONE);
            holder.imageview.setImageBitmap(photo1);
        } else {
            holder.initial.setVisibility(View.VISIBLE);
            holder.imageview.setVisibility(View.GONE);

            if (parts.length == 1) {
                String part1 = parts[0];
                holder.initial.setText(String.valueOf(part1.charAt(0)));
            } else if (parts.length > 1) {
                String part1 = parts[0];
                String part2 = parts[1];
                holder.initial.setText(String.valueOf(part1.charAt(0)) + String.valueOf(part2.charAt(0)));
            }
        }


        if (fevrates_sign.equals("2")) {
            holder.liked_button.setVisibility(View.VISIBLE);
            holder.faverate.setVisibility(View.GONE);
        } else {
            holder.faverate.setVisibility(View.VISIBLE);
            holder.liked_button.setVisibility(View.GONE);
        }


        ViewGroup viewGroup;
        holder.clickoncontact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent callIntent = new Intent(Intent.ACTION_DIAL);
                callIntent.setData(Uri.parse("tel:" + Uri.encode(contactModel.getC_number().trim())));
                callIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(callIntent);


            }
        });

        holder.faverate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                database.updateStatus(contactModel.getC_name(), "2", contactModel.getC_id());
                holder.liked_button.setVisibility(View.VISIBLE);
                holder.faverate.setVisibility(View.GONE);
            }
        });

        holder.liked_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                database.updateStatus(contactModel.getC_name(), "1", contactModel.getC_id());
                holder.liked_button.setVisibility(View.GONE);
                holder.faverate.setVisibility(View.VISIBLE);
                contactModels.remove(position);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position, contactModels.size());
            }
        });

    }

    @Override
    public int getItemCount() {
        return contactModels.size();
    }


}
