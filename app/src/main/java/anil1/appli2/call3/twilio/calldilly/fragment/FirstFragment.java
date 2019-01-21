package anil1.appli2.call3.twilio.calldilly.fragment;

import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import anil1.appli2.call3.twilio.calldilly.AddContact;
import anil1.appli2.call3.twilio.calldilly.R;
import anil1.appli2.call3.twilio.calldilly.call.VoiceActivity;
import anil1.appli2.call3.twilio.calldilly.pojo.ContactModel;

import static anil1.appli2.call3.twilio.calldilly.comm.Common.callNumber;
import static com.facebook.FacebookSdk.getApplicationContext;


@SuppressLint("ValidFragment")
public class FirstFragment extends Fragment {

    ArrayList<ContactModel> StoreContacts;

    RecyclerView list;
    private DishAdapter dAdapter;

    @SuppressLint("ValidFragment")
    public FirstFragment(Context context) {
        StoreContacts = new ArrayList<>();
        GetContactsIntoArrayList(context);
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        System.out.println(StoreContacts.size());
        View view = inflater.inflate(R.layout.fragment_first, container, false);
        list = (RecyclerView) view.findViewById(R.id.contactList);
        Button button = (Button) view.findViewById(R.id.topScrol);
        Button add_Contact = (Button) view.findViewById(R.id.addContact);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LinearLayoutManager layoutManager = (LinearLayoutManager) list
                        .getLayoutManager();
                layoutManager.scrollToPositionWithOffset(0, 0);

            }
        });

        add_Contact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), AddContact.class);
                startActivity(intent);
            }
        });
        initViews();
        return view;
    }


    private void initViews() {

        dAdapter = new DishAdapter(getApplicationContext(), StoreContacts);
        // list.setItemAnimator(new DefaultItemAnimator());
        list.setAdapter(dAdapter);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        list.setLayoutManager(mLayoutManager);
    }

    class DishAdapter extends RecyclerView.Adapter<DishAdapter.DishViewHolder> {

        private List<ContactModel> dishList;
        private Context context;

        public class DishViewHolder extends RecyclerView.ViewHolder {
            public TextView tet1_old, tet2_old;
            ImageView call;
            public DishViewHolder(View view) {
                super(view);
                tet1_old = (TextView) view.findViewById(R.id.contactName);
                tet2_old = (TextView) view.findViewById(R.id.contactNumber);
                call = (ImageView) view.findViewById(R.id.callOnThis);
            }
        }

        public DishAdapter(Context context, List<ContactModel> dishList) {
            this.context = context;
            this.dishList = dishList;
        }

        private DishAdapter.DishViewHolder holder;

        @Override
        public DishAdapter.DishViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.singlrcontact, parent, false);
            return new DishAdapter.DishViewHolder(itemView);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public void onBindViewHolder(DishAdapter.DishViewHolder holder, int position) {
            final ContactModel dishPOJO = dishList.get(position);
            holder.tet1_old.setText(dishPOJO.getName());
            holder.tet2_old.setText(dishPOJO.getNumber());
            holder.call.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getApplicationContext(), VoiceActivity.class);
                    callNumber = dishPOJO.getNumber();
                    startActivity(intent);
                }
            });
        }

        @Override
        public int getItemCount() {
            return dishList.size();
        }
    }

//    private void addContact(String name, String phone) {
//        ContentValues values = new ContentValues();
//        values.put(Contacts.People.NUMBER, phone);
//        values.put(Contacts.People.TYPE, ContactsContract.CommonDataKinds.Phone.TYPE_CUSTOM);
//        values.put(Contacts.People.LABEL, name);
//        values.put(Contacts.People.NAME, name);
//        Uri dataUri = getContentResolver().insert(Contacts.People.CONTENT_URI, values);
//        Uri updateUri = Uri.withAppendedPath(dataUri, Contacts.People.Phones.CONTENT_DIRECTORY);
//        values.clear();
//        values.put(Contacts.People.Phones.TYPE, Contacts.People.TYPE_MOBILE);
//        values.put(Contacts.People.NUMBER, phone);
//        updateUri = getContentResolver().insert(updateUri, values);
//    }


    public void GetContactsIntoArrayList(Context context) {
        String lastnumber = "0";
        ContentResolver cr = context.getContentResolver();
        Cursor cursor = cr.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, null);
        if (cursor != null) {
            try {
                final int nameIndex = cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME);
                final int numberIndex = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
                String name, number;
                while (cursor.moveToNext()) {
                    name = cursor.getString(nameIndex);
                    number = cursor.getString(numberIndex).trim();
                    number = number.replaceAll("\\s", "");
                    if (number.equals(lastnumber)) {

                    } else {
                        lastnumber = number;
                        ContactModel contact = new ContactModel();
                        contact.setName(name);
                        contact.setNumber(number);
                        StoreContacts.add(contact);
//                        if (dAdapter != null)
//                            dAdapter.notifyDataSetChanged();
//                        System.out.println("ContactFragment.readContact ==>" + name);
                    }
                }
            } finally {
                cursor.close();
            }
//        cursor = context.getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,null, null, null);
//        String name, number;
//        while (cursor.moveToNext()) {
//            name = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
//            phonenumber = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
//
//
//            ContactModel contactModel = new ContactModel();
//            contactModel.setName(name);
//            contactModel.setNumber(phonenumber);
//            StoreContacts.add(contactModel);
//        }
//        cursor.close();

        }
    }
}
