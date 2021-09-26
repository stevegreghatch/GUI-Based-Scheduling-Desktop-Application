package Reports;

public class AppointmentReportType {
    public String appointmentType;
    public int appointmentTotal;

    /**
     *
     * @param appointmentType the appointment type to set
     * @param appointmentTotal the appointment total to set
     */
    public AppointmentReportType(String appointmentType, int appointmentTotal) {
        this.appointmentType = appointmentType;
        this.appointmentTotal = appointmentTotal;
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
     * @return the appointment total
     */
    public int getAppointmentTotal() {
        return appointmentTotal;
    }

}
