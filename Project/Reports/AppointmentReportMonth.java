package Reports;

public class AppointmentReportMonth {
    public String appointmentMonth;
    public int appointmentTotal;

    /**
     *
     * @param appointmentMonth the appointment month to set
     * @param appointmentTotal the appointment total to set
     */
    public AppointmentReportMonth(String appointmentMonth, int appointmentTotal) {
        this.appointmentMonth = appointmentMonth;
        this.appointmentTotal = appointmentTotal;
    }

    /**
     *
     * @return the appointment month
     */
    public String getAppointmentMonth() {
        return appointmentMonth;
    }

    /**
     *
     * @return the appointment total
     */
    public int getAppointmentTotal() {
        return appointmentTotal;
    }
}
