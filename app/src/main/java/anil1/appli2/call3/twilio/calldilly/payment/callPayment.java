package anil1.appli2.call3.twilio.calldilly.payment;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
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

public class callPayment extends AppCompatActivity {
    private CardForm cardForm;
    private Button checkout_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_call_payment);
        cardForm = (CardForm) findViewById(R.id.bt_card_form);
        checkout_button = (Button) findViewById(R.id.checkout_card);
        cardForm.cardRequired(true)
                .expirationRequired(true)
                .cvvRequired(true)
                .actionLabel("Checkout")
                .setup(this);
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
    }

    private void getStripeToken(String cardNumber, String expirationMonth, String expirationYear, String cvv) {
        Card card = new Card(cardNumber, Integer.parseInt(expirationMonth), Integer.parseInt(expirationYear), cvv);
        Stripe stripe = null;
        try {
            stripe = new Stripe(callPayment.this, "pk_test_cJhtOjA46tUKvSQIOPxsybmO");
            stripe.createToken(
                    card,
                    new TokenCallback() {
                        public void onSuccess(Token token) {
                            Toast.makeText(callPayment.this, token.getId(), Toast.LENGTH_SHORT).show();
                            System.out.println(token.getId());
                            token.getCard();
                            call(token.getId());
                        }

                        public void onError(Exception error) {
                            // Show localized error message
                            Toast.makeText(callPayment.this,
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

    public void call(final String token) {
        AlertDialog.Builder builder = new AlertDialog.Builder(callPayment.this);
// Add the buttons
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                PaymentPOJO paymentPOJO = new PaymentPOJO();
                paymentPOJO.setAmount((float) 10.00);
                paymentPOJO.setCurrency("usd");
                paymentPOJO.setDescription("tests");
                paymentPOJO.setMethod("charge");
                paymentPOJO.setSource(token);
                ReqPayment reqLogin = new ReqPayment();
                reqLogin.execute(MessageType.POST, Common.PaymentURL, paymentPOJO);
            }
        });
        builder.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User cancelled the dialog
            }
        });


        AlertDialog dialog = builder.create();
    }

    class ReqPayment extends MessageManager {
        @Override
        public void onPostExecute(String result) {
            System.out.println(result);
            if (result == null) {
                finish();
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
