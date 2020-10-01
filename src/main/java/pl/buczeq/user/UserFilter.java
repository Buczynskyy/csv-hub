package pl.buczeq.user;

import javax.ws.rs.QueryParam;

public class UserFilter {

    @QueryParam("firstName")
    private String firstName;

    @QueryParam("lastName")
    private String lastName;

    @QueryParam("birthDate")
    private String birthDate;

    @QueryParam("phoneNumber")
    private String phoneNumber;

    @QueryParam("phoneNumberRequired")
    private boolean phoneNumberRequired;

    public static UserFilterBuilder builder() {
        return new UserFilterBuilder();
    }

    public static class UserFilterBuilder {

        private String firstName;

        private String lastName;

        private String birthDate;

        private String phoneNumber;

        private boolean phoneNumberRequired;

        public UserFilterBuilder firstName(String firstName) {
            this.firstName = firstName;
            return this;
        }

        public UserFilterBuilder lastName(String lastName) {
            this.lastName = lastName;
            return this;
        }

        public UserFilterBuilder birthDate(String birthDate) {
            this.birthDate = birthDate;
            return this;
        }

        public UserFilterBuilder phoneNumber(String phoneNumber) {
            this.phoneNumber = phoneNumber;
            return this;
        }

        public UserFilterBuilder phoneNumberRequired(boolean phoneNumberRequired) {
            this.phoneNumberRequired = phoneNumberRequired;
            return this;
        }

        public UserFilter build() {
            UserFilter userFilter = new UserFilter();
            userFilter.firstName = this.firstName;
            userFilter.lastName = this.lastName;
            userFilter.birthDate = this.birthDate;
            userFilter.phoneNumber = this.phoneNumber;
            userFilter.phoneNumberRequired = this.phoneNumberRequired;
            return userFilter;
        }
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public boolean isPhoneNumberRequired() {
        return phoneNumberRequired;
    }

    public void setPhoneNumberRequired(boolean phoneNumberRequired) {
        this.phoneNumberRequired = phoneNumberRequired;
    }
}
