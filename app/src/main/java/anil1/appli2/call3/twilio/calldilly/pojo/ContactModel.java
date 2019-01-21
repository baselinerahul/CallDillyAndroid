package anil1.appli2.call3.twilio.calldilly.pojo;


import java.io.Serializable;

public class ContactModel implements Serializable {

    private String name, number;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }
}