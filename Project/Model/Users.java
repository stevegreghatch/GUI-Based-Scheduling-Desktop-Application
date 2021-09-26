package Model;

public class Users {
    public int userID;
    public String userName;
    public String userPassword;

    /**
     *
     * @param userID the user ID to set
     * @param userName the user name to set
     * @param userPassword the user password to set
     */
    public Users(int userID, String userName, String userPassword) {
        this.userID = userID;
        this.userName = userName;
        this.userPassword = userPassword;
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
     * @return the user name
     */
    public String getUserName() {
        return userName;
    }

    /**
     *
     * @return the user password
     */
    public String getUserPassword() {
        return userPassword;
    }
}