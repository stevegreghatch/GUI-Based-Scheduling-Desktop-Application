package Model;
import java.time.LocalDateTime;

public class Appointments {
    private int appointmentID;
    private String appointmentTitle;
    private String appointmentDescription;
    private String appointmentLocation;
    private String appointmentType;
    private LocalDateTime start;
    private LocalDateTime end;
    public int customerID;
    public int userID;
    public int contactID;

    /**
     *
     * @param appointmentID the appointment ID to set
     * @param appointmentTitle the appointment title to set
     * @param appointmentDescription the appointment description to set
     * @param appointmentLocation the appointment location to set
     * @param appointmentType the appointment type to set
     * @param start the appointment start LocalDateTime to set
     * @param end the appointment end LocalDateTime to set
     * @param customerID the customer ID to set
     * @param userID the user ID to set
     * @param contactID the contact ID to set
     */
    public Appointments(int appointmentID, String appointmentTitle, String appointmentDescription,
                        String appointmentLocation, String appointmentType, LocalDateTime start, LocalDateTime end, int customerID,
                        int userID, int contactID) {
        this.appointmentID = appointmentID;
        this.appointmentTitle = appointmentTitle;
        this.appointmentDescription = appointmentDescription;
        this.appointmentLocation = appointmentLocation;
        this.appointmentType = appointmentType;
        this.start = start;
        this.end = end;
        this.customerID = customerID;
        this.userID = userID;
        this.contactID = contactID;
    }

    /**
     *
     * @return the appointmentID
     */
    public int getAppointmentID() {
        return appointmentID;
    }

    /**
     *
     * @return the appointment title
     */
    public String getAppointmentTitle() {
        return appointmentTitle;
    }

    /**
     *
     * @return the appointment description
     */
    public String getAppointmentDescription() {
        return appointmentDescription;
    }

    /**
     *
     * @return the appointment location
     */
    public String getAppointmentLocation() {
        return appointmentLocation;
    }

    /**
     *
     * @return the appointment type
     */
    public String getAppointmentType() {
        return appointmentType;
    }

    /**
     *
     * @return the start LocalDateTime
     */
    public LocalDateTime getStart() {
        return start;
    }

    /**
     *
     * @return the end LocalDateTime
     */
    public LocalDateTime getEnd() {
        return end;
    }

    /**
     *
     * @return the customer ID
     */
    public int getCustomerID () {
        return customerID;
    }

    /**
     *
     * @return the user ID
     */
    public int getUserID() {
        return userID;
    }

    /**
     *
     * @return the contact ID
     */
    public int getContactID() {
        return contactID;
    }

}

