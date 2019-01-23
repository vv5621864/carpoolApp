package io.carpoolapp.model;

public class UserDetails {

    private String name = null;
    private String gender = null;
    private String yearOfBirth = null;
    private String mobile = null;
    private String email = null;
    private String password = null;
    private String profileUri = null;
    private long profileCreationTime = 0;
    private long profileModificationTime = 0;

    private static final UserDetails ourInstance = new UserDetails();

    public static UserDetails getInstance() {
        return ourInstance;
    }

    private UserDetails() {
    }

    public String getProfileUri() {
        return profileUri;
    }

    public void setProfileUri(String profileUri) {
        this.profileUri = profileUri;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getYearOfBirth() {
        return yearOfBirth;
    }

    public void setYearOfBirth(String yearOfBirth) {
        this.yearOfBirth = yearOfBirth;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public long getProfileCreationTime() {
        return profileCreationTime;
    }

    public void setProfileCreationTime(long profileCreationTime) {
        this.profileCreationTime = profileCreationTime;
    }

    public long getProfileModificationTime() {
        return profileModificationTime;
    }

    public void setProfileModificationTime(long profileModificationTime) {
        this.profileModificationTime = profileModificationTime;
    }
}
