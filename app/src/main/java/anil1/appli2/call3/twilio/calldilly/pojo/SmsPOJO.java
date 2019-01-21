package anil1.appli2.call3.twilio.calldilly.pojo;

import java.io.Serializable;

public class SmsPOJO implements Serializable {
    String to;
    String body;

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}
