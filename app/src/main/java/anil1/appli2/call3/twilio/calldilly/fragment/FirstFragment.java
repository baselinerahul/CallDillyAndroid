package anil1.appli2.call3.twilio.calldilly.fragment;

 
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;

import anil1.appli2.call3.twilio.calldilly.AddContact;
import anil1.appli2.call3.twilio.calldilly.R;
import static com.facebook.FacebookSdk.getApplicationContext;


@SuppressLint("ValidFragment")
public class FirstFragment extends Fragment {

    private static RecyclerView list;
    private static ArrayList<Contact_Model> arrayList;
    private static Contact_Adapter adapter;
    private static final int PERMISSIONS_REQUEST_READ_CONTACTS = 100;
    private static ProgressDialog pd;

    public FirstFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
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
        if (arrayList == null) {
            new LoadContacts().execute();
        } else {
            initview();
        }
        return view;
    }

    public void initview() {
        adapter = new Contact_Adapter(getApplicationContext(), arrayList);
        list.setAdapter(adapter);// set adapter
        final LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        list.setLayoutManager(layoutManager);
    }


    private class LoadContacts extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {
            arrayList = readContacts();// Get contacts array list from this
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            // If array list is not null and is contains value
            if (arrayList != null && arrayList.size() > 0) {
                adapter = null;
                if (adapter == null) {
                    adapter = new Contact_Adapter(getApplicationContext(), arrayList);
                    list.setAdapter(adapter);// set adapter
                    final LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
                    layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                    list.setLayoutManager(layoutManager);
                }
                adapter.notifyDataSetChanged();
            } else {
                Toast.makeText(getApplicationContext(), "There are no contacts.",
                        Toast.LENGTH_LONG).show();
            }
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

    }

    private ArrayList<Contact_Model> readContacts() {
        ArrayList<Contact_Model> contactList = new ArrayList<Contact_Model>();

        Uri uri = ContactsContract.Contacts.CONTENT_URI; // Contact URI
        Cursor contactsCursor = getApplicationContext().getContentResolver().query(uri, null, null,
                null, ContactsContract.Contacts.DISPLAY_NAME + " ASC ");
        if (contactsCursor.moveToFirst()) {
            do {
                long contctId = contactsCursor.getLong(contactsCursor
                        .getColumnIndex("_ID")); // Get contact ID
                Uri dataUri = ContactsContract.Data.CONTENT_URI; // URI to get
                Cursor dataCursor = getApplicationContext().getContentResolver().query(dataUri, null,
                        ContactsContract.Data.CONTACT_ID + " = " + contctId,
                        null, null);// Retrun data cusror represntative to
                String displayName = "";
                String mobilePhone = "";
                String contactNumbers = "";

                // Now start the cusrsor
                if (dataCursor.moveToFirst()) {
                    displayName = dataCursor
                            .getString(dataCursor
                                    .getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));// get
                    do {
                        if (dataCursor
                                .getString(
                                        dataCursor.getColumnIndex("mimetype"))
                                .equals(ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE)) {
                            switch (dataCursor.getInt(dataCursor
                                    .getColumnIndex("data2"))) {
                                case ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE:
                                    mobilePhone = dataCursor.getString(dataCursor
                                            .getColumnIndex("data1"));
                                    contactNumbers = mobilePhone;
                                    break;

                            }
                        }
                    } while (dataCursor.moveToNext());
                    contactList.add(new Contact_Model(displayName, contactNumbers));
                }
            } while (contactsCursor.moveToNext());
        }
        return contactList;
    }

    /*ArrayList<ContactModel> StoreContacts;
    private static final int PERMISSIONS_REQUEST_READ_CONTACTS = 100;
    RecyclerView list;
    private DishAdapter dAdapter;
    Context context;

    @SuppressLint("ValidFragment")
    public FirstFragment(Context context) {
        StoreContacts = new ArrayList<>();
        this.context = context;

        // showContacts();
        //
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        System.out.println(StoreContacts.size());
        EnableRuntimePermission();
        //  GetContactsIntoArrayList();
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
        if(StoreContacts.size()>0) {
            initViews();
        }
        return view;
    }


    public void EnableRuntimePermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.READ_CONTACTS}, PERMISSIONS_REQUEST_READ_CONTACTS);
            //After this point you wait for callback in onRequestPermissionsResult(int, String[], int[]) overriden method
        } else {
            // Android version is lesser than 6.0 or the permission is already granted.
            GetContactsIntoArrayList();
        }

//        if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.READ_CONTACTS)) {
//            Toast.makeText(getApplicationContext(), "CONTACTS permission allows us to Access CONTACTS app", Toast.LENGTH_LONG).show();
//        } else {
//            requestPermissions(new String[]{
//                    Manifest.permission.READ_CONTACTS}, RequestPermissionCode);
//        }
    }

    @Override
    public void onRequestPermissionsResult(int RC, String per[], int[] PResult) {
        switch (RC) {
            case RequestPermissionCode:
                if (PResult.length > 0 && PResult[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(getApplicationContext(), "Permission Granted, Now your application can access CONTACTS.", Toast.LENGTH_LONG).show();
                    initViews();
                } else {
                    Toast.makeText(getApplicationContext(), "Permission Canceled, Now your application cannot access CONTACTS.", Toast.LENGTH_LONG).show();
                }
                break;
        }
    }

    private void initViews() {

        dAdapter = new DishAdapter(getApplicationContext(), StoreContacts);
        // list.setItemAnimator(new DefaultItemAnimator());
        list.setAdapter(dAdapter);
//        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
//        list.setLayoutManager(mLayoutManager);
        final LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        list.setLayoutManager(layoutManager);
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

        private  DishViewHolder holder;

        @Override
        public  DishViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.singlrcontact, parent, false);
            return new  DishViewHolder(itemView);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public void onBindViewHolder( DishViewHolder holder, int position) {
            final ContactModel dishPOJO = dishList.get(position);
            holder.tet1_old.setText(dishPOJO.getName());
            holder.tet2_old.setText(dishPOJO.getNumber());
            holder.call.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getApplicationContext(), VoiceActivity.class);
                    callNumber = dishPOJO.getNumber();
                    intent.putExtra("callNumber", dishPOJO.getNumber());
                    startActivity(intent);
                }
            });
        }

        @Override
        public int getItemCount() {
            return dishList.size();
        }
    }
    private void startApp(){
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivityForResult(intent, 0);
    }

    public void GetContactsIntoArrayList() {
        String lastnumber = "0";
        ContentResolver cr = getApplicationContext().getContentResolver();
        Cursor cursor = cr.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null,  ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + " ASC");
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
                        System.out.println(StoreContacts.size());
                    }
                }

            } finally {
                cursor.close();
            }
          //  initViews();
        }

    }*/
}
