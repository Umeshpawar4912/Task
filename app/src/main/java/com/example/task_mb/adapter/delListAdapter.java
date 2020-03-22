package com.example.task_mb.adapter;


import android.content.Context;
import android.content.DialogInterface;
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

import java.util.ArrayList;
import java.util.List;

public class delListAdapter extends RecyclerView.Adapter<delListAdapter.MyViewHolder> {

    private List<ContactModel> contactModels = new ArrayList<ContactModel>();
    Context context;
    private RecyclerView recycleView;

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView C_name,C_number,initial;
        LinearLayout clickoncontact;
        ImageView d_restore;
        private SQLiteDBHelper database ;

        public MyViewHolder(View view) {
            super(view);

            database = new SQLiteDBHelper(context);
            C_name = view.findViewById(R.id.d_contactName);
            C_number = view.findViewById(R.id.d_contactNumber);
            clickoncontact = view.findViewById(R.id.d_clickoncontact);
            initial = view.findViewById(R.id.d_initial);
            d_restore = view.findViewById(R.id.d_restore);
        }
    }

    public delListAdapter(Context context, List<ContactModel> contactModels1) {

        this.context = context;
        this.contactModels = contactModels1;

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        context = parent.getContext();

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.del_list_view, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {

        final ContactModel contactModel = contactModels.get(position);
        final int itemPosition = holder.getAdapterPosition();
        final SQLiteDBHelper database = new SQLiteDBHelper(context);
        holder.C_name.setText(contactModel.getC_name());
        holder.C_number.setText(contactModel.getC_number());



        String initialName = contactModel.getC_name();
        String afterTrim = initialName.trim().replaceAll(" +", " ");
        String parts[] = afterTrim.split(" ");
        if (parts.length == 1) {

            String part1 = parts[0];
            holder.initial.setText(String.valueOf(part1.charAt(0)));

        } else if (parts.length > 1) {
            String part1 = parts[0];
            String part2 = parts[1];
            holder.initial.setText(String.valueOf(part1.charAt(0)) + String.valueOf(part2.charAt(0)));
        }

        holder.d_restore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        holder.d_restore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AlertDialog.Builder(context)
                        .setTitle("Restore Number..?")
                        .setMessage("Are you sure want to Restore number")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog, int which) {
                                database.updateStatus(contactModel.getC_name(), "1",contactModel.getC_id());
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
