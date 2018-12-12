package anil.appli.call.twilio.calldilly.message;//package cam.gobble.palo3.gobbleup.comm.message;

/**
 * Created by Palo12 on 25-10-2017.
 */

import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Custom implementation of Volley Request Queue
 */
public class RequestQueueManager {

    private static RequestQueue requestQueue;

    public static synchronized RequestQueue getRequestQueue(Context ctx) {
        if (requestQueue == null) {
            requestQueue = Volley.newRequestQueue(ctx);
        }
        return requestQueue;
    }
}