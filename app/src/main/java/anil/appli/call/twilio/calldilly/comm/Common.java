package anil.appli.call.twilio.calldilly.comm;

import java.util.ArrayList;


/**
 * Created by guruji on 9/4/2018.
 */

public class Common {
    // 56a5d3bd515ee9fa315d605d
    // 5372988731131cdb3534860e
    //517064acf61e46764000000c
    public static int totalItenInCart = 0;
    public static String totalAmount = "0";
    public static String restrorentId = "5372988731131cdb3534860e";

    public static final String BaseURL = "https://opendining.net/api/v1/";
    public static final String RestrorentURL = BaseURL + "restaurants/5372988731131cdb3534860e?key=8017edbeab4660d84bc31296ae6f302ad98f14bd";
    public static final String RestrorentMenuTierURL = BaseURL + "restaurants/5372988731131cdb3534860e/menu/tier?key=8017edbeab4660d84bc31296ae6f302ad98f14bd";
    public static final String ORDERS = BaseURL + "/orders" + "/5372988731131cdb3534860e?key=8017edbeab4660d84bc31296ae6f302ad98f14bd";
    public static String order_id = null;
}
