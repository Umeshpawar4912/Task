package com.example.task_mb.adapter;


import android.content.Context;
import android.content.DialogInterface;
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

import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.task_mb.R;
import com.example.task_mb.datbade.SQLiteDBHelper;
import com.example.task_mb.model.ContactModel;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class ContactListAdapter extends RecyclerView.Adapter<ContactListAdapter.MyViewHolder> {

    private List<ContactModel> contactModels = new ArrayList<ContactModel>();
    Context context;


    public class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView C_name, C_number, initial;
        LinearLayout clickoncontact;
        ImageView faverate, delete, liked_button;
        CircleImageView imageview;
        private SQLiteDBHelper database;

        public MyViewHolder(View view) {
            super(view);

            database = new SQLiteDBHelper(context);
            C_name = view.findViewById(R.id.contactName);
            C_number = view.findViewById(R.id.contactNumber);
            clickoncontact = view.findViewById(R.id.clickoncontact);
            initial = view.findViewById(R.id.initial);
            faverate = view.findViewById(R.id.like_button);
            delete = view.findViewById(R.id.delete);
            liked_button = view.findViewById(R.id.liked_button);
            imageview = view.findViewById(R.id.c_imageview);
        }
    }

    public ContactListAdapter(Context context, List<ContactModel> contactModels1) {

        this.context = context;
        this.contactModels = contactModels1;

    }




    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        context = parent.getContext();

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.contact_list_view, parent, false);

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


        if (img_url !=null){
            Uri imageUri= Uri.parse(img_url);
            Bitmap photo1 =null;
            try {
                photo1 = MediaStore.Images.Media.getBitmap(context.getContentResolver(), imageUri);
            } catch (IOException e) {
                e.printStackTrace();
            }
            holder.imageview.setVisibility(View.VISIBLE);
            holder.initial.setVisibility(View.GONE);
            holder.imageview.setImageBitmap(photo1);
        }else {
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


                new AlertDialog.Builder(context)
                        .setTitle("Call Or Message")
                        .setMessage("Choose One for call contact or send SMS to this number")
                        .setPositiveButton("CALL", new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog, int which) {
                                Intent callIntent = new Intent(Intent.ACTION_DIAL);
                                callIntent.setData(Uri.parse("tel:" + Uri.encode(contactModel.getC_number().trim())));
                                callIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                context.startActivity(callIntent);
                                dialog.dismiss();
                            }
                        })
                        .setNegativeButton("SMS", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.fromParts("sms", contactModel.getC_number().trim(), null)));

                                dialog.dismiss();
                            }
                        })
                        .show();




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
            }
        });

        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                new AlertDialog.Builder(context)
                        .setTitle("Delete Number")
                        .setMessage("Are you sure want to Delete number")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog, int which) {
                                database.updateStatus(contactModel.getC_name(), "3", contactModel.getC_id());
                                contactModels.remove(position);
                                notifyItemRemoved(position);
                                notifyItemRangeChanged(position, contactModels.size());
                                dialog.dismiss();
                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        .show();


            }
        });
    }


    @Override
    public int getItemCount() {
        return contactModels.size();
    }


}
