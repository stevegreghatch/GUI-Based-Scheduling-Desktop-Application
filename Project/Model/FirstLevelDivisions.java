package Model;

public class FirstLevelDivisions {
    private int divisionID;
    private String divisionName;
    public int country_ID;

    /**
     *
     * @param divisionID the division ID to set
     * @param divisionName the division name to set
     * @param country_ID the country ID to set
     */
    public FirstLevelDivisions(int divisionID, String divisionName, int country_ID) {
        this.divisionID = divisionID;
        this.divisionName = divisionName;
        this.country_ID = country_ID;
    }

    /**
     *
     * @return the division ID
     */
    public int getDivisionID() {
        return divisionID;
    }

    /**
     *
     * @return the division name
     */
    public String getDivisionName() {
        return divisionName;
    }

    /**
     *
     * @return the country ID
     */
    public int getCountry_ID() {
        return country_ID;
    }

}
