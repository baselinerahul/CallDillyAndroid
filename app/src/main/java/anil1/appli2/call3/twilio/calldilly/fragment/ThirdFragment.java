package anil1.appli2.call3.twilio.calldilly.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.braintreepayments.cardform.OnCardFormSubmitListener;
import com.braintreepayments.cardform.view.CardForm;
import com.stripe.android.Stripe;
import com.stripe.android.TokenCallback;
import com.stripe.android.model.Card;
import com.stripe.android.model.Token;

import org.json.JSONObject;

import anil1.appli2.call3.twilio.calldilly.R;
import anil1.appli2.call3.twilio.calldilly.comm.Common;
import anil1.appli2.call3.twilio.calldilly.message.MessageManager;
import anil1.appli2.call3.twilio.calldilly.message.MessageType;
import anil1.appli2.call3.twilio.calldilly.pojo.PaymentPOJO;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import static com.facebook.FacebookSdk.getApplicationContext;

public class ThirdFragment extends Fragment {
    @BindView(R.id.ampunt)
    EditText ampunt;
    @BindView(R.id.conform)
    Button conform;
    Unbinder unbinder;
    private CardForm cardForm;
    private Button checkout_button;
    String token2;

    public ThirdFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_third, container, false);
        cardForm = (CardForm) view.findViewById(R.id.bt_card_form);
        checkout_button = (Button) view.findViewById(R.id.checkout_card);
        cardForm.cardRequired(true)
                .expirationRequired(true)
                .cvvRequired(true)
                .actionLabel("Checkout")
                .setup(getActivity());
        checkout_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cardForm.isValid()) {
                    getStripeToken(cardForm.getCardNumber(), cardForm.getExpirationMonth(), cardForm.getExpirationYear(), cardForm.getCvv());
                } else {
                    cardForm.validate();
                }
            }
        });
        cardForm.setOnCardFormSubmitListener(new OnCardFormSubmitListener() {
            @Override
            public void onCardFormSubmit() {
                if (cardForm.isValid()) {
                    getStripeToken(cardForm.getCardNumber(), cardForm.getExpirationMonth(), cardForm.getExpirationYear(), cardForm.getCvv());
                } else {
                    cardForm.validate();
                }
            }
        });

        unbinder = ButterKnife.bind(this, view);
        conform.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PaymentPOJO paymentPOJO = new PaymentPOJO();
                paymentPOJO.setAmount(Float.valueOf(ampunt.getText().toString()));
                paymentPOJO.setCurrency("usd");
                paymentPOJO.setDescription("tests");
                paymentPOJO.setMethod("charge");
                paymentPOJO.setSource(token2);
                ReqPayment reqLogin = new ReqPayment();
                reqLogin.execute(MessageType.POST, Common.PaymentURL, paymentPOJO);
            }
        });
        return view;
    }

    private void getStripeToken(String cardNumber, String expirationMonth, String expirationYear, String cvv) {
        Card card = new Card(cardNumber, Integer.parseInt(expirationMonth), Integer.parseInt(expirationYear), cvv);
        Stripe stripe = null;
        try {
            stripe = new Stripe(getApplicationContext(), "pk_test_cJhtOjA46tUKvSQIOPxsybmO");
            stripe.createToken(
                    card,
                    new TokenCallback() {
                        public void onSuccess(Token token) {
                            Toast.makeText(getApplicationContext(), token.getId(), Toast.LENGTH_SHORT).show();
                            System.out.println(token.getId());
                            token2 = String.valueOf(token.getId().toString());
                            call();
                        }

                        public void onError(Exception error) {
                            // Show localized error message
                            Toast.makeText(getApplicationContext(),
                                    error.getLocalizedMessage(),
                                    Toast.LENGTH_LONG
                            ).show();
                        }
                    }
            );
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void call() {
        cardForm.setVisibility(View.GONE);
        checkout_button.setVisibility(View.GONE);
        ampunt.setVisibility(View.VISIBLE);
        conform.setVisibility(View.VISIBLE);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


    class ReqPayment extends MessageManager {
        @Override
        public void onPostExecute(String result) {
            System.out.println(result);
            if (result == null) {

            } else {
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    System.out.println(jsonObject);
                } catch (Exception e) {
                    System.out.println(e);
                }
            }
        }
    }

}