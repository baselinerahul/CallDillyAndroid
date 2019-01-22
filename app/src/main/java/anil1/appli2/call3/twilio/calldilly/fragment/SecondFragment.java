package anil1.appli2.call3.twilio.calldilly.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import anil1.appli2.call3.twilio.calldilly.R;
import anil1.appli2.call3.twilio.calldilly.call.VoiceActivity;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

import static com.facebook.FacebookSdk.getApplicationContext;

public class SecondFragment extends Fragment {

    @BindView(R.id.number)
    EditText number;
    @BindView(R.id.call_fab)
    FloatingActionButton callFab;
    Unbinder unbinder;

    public SecondFragment() {
// Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
// Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_second, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.call_fab)
    public void onViewClicked() {
        if (isValidate()) {
            Intent intent = new Intent(getApplicationContext(), VoiceActivity.class);
            intent.putExtra("callNumber", number.getText().toString());
            startActivity(intent);
        }
    }

    public boolean isValidate() {
        String numberstr = number.getText().toString();
        if (numberstr.isEmpty()) {
            number.setError("Please enter your number");
            return false;
        }
        if (numberstr.length() <= 10) {
            Toast.makeText(getApplicationContext(), "check your number", Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }
}