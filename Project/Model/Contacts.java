package Model;

public class Contacts {
    public int contactID;
    public String contactName;
    public String contactEmail;

    /**
     *
     * @param contactID the contact ID to set
     * @param contactName the contact name to set
     * @param contactEmail the contact email to set
     */
    public Contacts(int contactID, String contactName, String contactEmail) {
        this.contactID = contactID;
        this.contactName = contactName;
        this.contactEmail = contactEmail;
    }

    /**
     *
     * @return the contact ID
     */
    public int getId() {
        return contactID;
    }

    /**
     *
     * @return the contact name
     */
    public String getContactName() {
        return contactName;
    }

    /**
     *
     * @return the contact email
     */
    public String getContactEmail() {
        return contactEmail;
    }
}
