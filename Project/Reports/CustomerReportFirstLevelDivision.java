package Reports;

import java.util.ArrayList;

public class CustomerReportFirstLevelDivision {
    public String firstLevelDivision;
    public ArrayList<String> customerList;

    /**
     *
     * @param firstLevelDivision the first-level division to set
     * @param customerList the customer list to set
     */
    public CustomerReportFirstLevelDivision(String firstLevelDivision, ArrayList<String> customerList) {
        this.firstLevelDivision = firstLevelDivision;
        this.customerList = customerList;
    }

    /**
     *
     * @return the first-level division
     */
    public String getFirstLevelDivision() {
        return firstLevelDivision;
    }

    /**
     *
     * @return the customer list
     */
    public ArrayList<String> getCustomerList() {
        return customerList;
    }
}
