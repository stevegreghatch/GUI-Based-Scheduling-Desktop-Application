package Model;

public class Customers{
    private int customerID;
    private String customerName;
    private String customerAddress;
    private String customerPostalCode;
    private String customerPhoneNumber;
    public int divisionID;

    /**
     *
     * @param customerID the customer ID to set
     * @param customerName the customer name to set
     * @param customerAddress the customer address to set
     * @param customerPostalCode the customer postal code to set
     * @param customerPhoneNumber the customer phone number to set
     * @param divisionID the division ID to set
     */
    public Customers(int customerID, String customerName, String customerAddress, String customerPostalCode,
                     String customerPhoneNumber, int divisionID) {
        this.customerID = customerID;
        this.customerName = customerName;
        this.customerAddress = customerAddress;
        this.customerPostalCode = customerPostalCode;
        this.customerPhoneNumber = customerPhoneNumber;
        this.divisionID = divisionID;
    }

    /**
     *
     * @return the customer ID
     */
    public int getCustomerID() {
        return customerID;
    }

    /**
     *
     * @return the customer name
     */
    public String getCustomerName() {
        return customerName;
    }

    /**
     *
     * @return the customer address
     */
    public String getCustomerAddress() {
        return customerAddress;
    }

    /**
     *
     * @return the customer postal code
     */
    public String getCustomerPostalCode() {
        return customerPostalCode;
    }

    /**
     *
     * @return the customer phone number
     */
    public String getCustomerPhoneNumber() {
        return customerPhoneNumber;
    }

    /**
     *
     * @return the division ID
     */
    public int getDivisionID () {
        return divisionID;
    }

}
