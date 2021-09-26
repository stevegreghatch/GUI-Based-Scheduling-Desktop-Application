package Model;

public class Countries {
    private int countryID;
    private String countryName;

    /**
     *
     * @param countryID the country ID to set
     * @param countryName the country name to set
     */
    public Countries(int countryID, String countryName) {
        this.countryID = countryID;
        this.countryName = countryName;
    }

    /**
     *
     * @return the country ID
     */
    public int getCountryID() {
        return countryID;
    }

    /**
     *
     * @return the country name
     */
    public String getCountryName() {
        return countryName;
    }
}
