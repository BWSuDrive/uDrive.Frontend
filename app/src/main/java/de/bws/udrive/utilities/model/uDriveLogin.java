package de.bws.udrive.utilities.model;

public class uDriveLogin {

    private String loginName;
    private String hashedPassword;

    public uDriveLogin() {}

    public void setLoginName(String loginName) { this.loginName = loginName; }
    public void setHashedPassword(String hashedPassword) { this.hashedPassword = hashedPassword; }

    public String getLoginName() { return this.loginName; }
    public String getHashedPassword() { return this.hashedPassword; }
}
