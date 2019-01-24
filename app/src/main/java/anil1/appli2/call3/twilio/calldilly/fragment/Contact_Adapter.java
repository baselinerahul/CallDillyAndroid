package anil1.appli2.call3.twilio.calldilly.fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;

import java.util.ArrayList;
import java.util.List;

import anil1.appli2.call3.twilio.calldilly.R;
import anil1.appli2.call3.twilio.calldilly.call.VoiceActivity;
import anil1.appli2.call3.twilio.calldilly.pojo.ContactModel;

import static com.facebook.FacebookSdk.getApplicationContext;

public class Contact_Adapter extends RecyclerView.Adapter<Contact_Adapter.DishViewHolder> {

    private List<Contact_Model> dishList;
    private Context context;

    public class DishViewHolder extends RecyclerView.ViewHolder {
        public TextView tet1_old;
        ImageView call, icon;

        public DishViewHolder(View view) {
            super(view);
            tet1_old = (TextView) view.findViewById(R.id.contactName);
            //   tet2_old = (TextView) view.findViewById(R.id.contactNumber);
            call = (ImageView) view.findViewById(R.id.callOnThis);
            icon = (ImageView) view.findViewById(R.id.gmailitem_letter);
        }
    }

    public Contact_Adapter(Context context, List<Contact_Model> dishList) {
        this.context = context;
        this.dishList = dishList;
    }

    private DishViewHolder holder;

    @Override
    public DishViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.singlrcontact, parent, false);
        return new DishViewHolder(itemView);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public void onBindViewHolder(DishViewHolder holder, int position) {
        final Contact_Model dishPOJO = dishList.get(position);
        holder.tet1_old.setText(dishPOJO.getContactName());
        //  holder.tet2_old.setText(dishPOJO.getContactNumber());
        holder.call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), VoiceActivity.class);
                intent.putExtra("callNumber", dishPOJO.getContactNumber());
                getApplicationContext().startActivity(intent);
            }
        });
        ColorGenerator generator = ColorGenerator.MATERIAL;
        String letter = String.valueOf(dishPOJO.getContactName().charAt(0));
        TextDrawable drawable = TextDrawable.builder()
                .buildRound(letter, generator.getRandomColor());

        holder.icon.setImageDrawable(drawable);
    }

    @Override
    public int getItemCount() {
        return dishList.size();
    }
}
//    private Context context;
//    private ArrayList<Contact_Model> arrayList;
//
//    public Contact_Adapter(Context context, ArrayList<Contact_Model> arrayList) {
//        this.context = context;
//        this.arrayList = arrayList;
//    }
//
//    @Override
//    public int getCount() {
//
//        return arrayList.size();
//    }
//
//    @Override
//    public Contact_Model getItem(int position) {
//
//        return arrayList.get(position);
//    }
//
//    @NonNull
//    @Override
//    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        return null;
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
//
//    }
//
//    @Override
//    public long getItemId(int position) {
//
//        return position;
//    }
//
//    @Override
//    public int getItemCount() {
//        return 0;
//    }
//
//    @Override
//    public View getView(int position, View convertView, ViewGroup parent) {
//        LayoutInflater inflater = (LayoutInflater) context
//                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//        Contact_Model model = arrayList.get(position);
//        ViewHodler holder;
//        if (convertView == null) {
//            convertView = inflater.inflate(R.layout.singlrcontact, parent, false);
//            holder = new ViewHodler();
//            holder.contactName = (TextView) convertView
//                    .findViewById(R.id.contactName);
//            holder.contactNumber = (TextView) convertView
//                    .findViewById(R.id.contactNumber);
//            convertView.setTag(holder);
//        } else {
//            holder = (ViewHodler) convertView.getTag();
//        }
//
//        // Set items to all view
//        if (!model.getContactName().equals("")
//                && model.getContactName() != null) {
//            holder.contactName.setText(model.getContactName());
//        } else {
//            holder.contactName.setText("No Name");
//        }
//
//
//        if (!model.getContactNumber().equals("")
//                && model.getContactNumber() != null) {
//            holder.contactNumber.setText("CONTACT NUMBER - n"
//                    + model.getContactNumber());
//        } else {
//            holder.contactNumber.setText("CONTACT NUMBER - n"
//                    + "No Contact Number");
//        }
//
//        return convertView;
//    }
//
//    // View holder to hold views
//    private class ViewHodler {
//         TextView contactName, contactNumber;
//    }
//}