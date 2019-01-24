package anil1.appli2.call3.twilio.calldilly.fragment;

public class Contact_Model {

    // Getter and setter for contacts
    private String contactName, contactNumber;

    public Contact_Model(String contactName,
                         String contactNumber) {
        this.contactName = contactName;
        this.contactNumber = contactNumber;
    }


    public String getContactName() {
        return contactName;
    }

    public String getContactNumber() {
        return contactNumber;
    }

}