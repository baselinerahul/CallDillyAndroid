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
    private static ProgressDialog mProgressDialog;

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


    private void showProgressDialog() {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(getApplicationContext());
            mProgressDialog.setMessage("please wait...");
            mProgressDialog.setIndeterminate(true);
        }
        mProgressDialog.show();
    }

    private void hideProgressDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.hide();
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
}
