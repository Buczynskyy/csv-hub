package pl.buczeq.user;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

import static org.springframework.data.jpa.domain.Specification.where;

@Component
public class UserSpecification {

    public Specification<User> getUserSpecification(final UserFilter filter) {
        return where(firstName(filter.getFirstName()))
                .and(lastName(filter.getLastName()))
                .and(birthDate(filter.getBirthDate()))
                .and(phoneNumber(filter.getPhoneNumber()))
                .and(phoneNumberRequired(filter.isPhoneNumberRequired()));
    }

    public Specification<User> firstName(final String firstName) {
        if (firstName != null && !firstName.isEmpty()) {
            return (Specification<User>) (root, query, cb) -> cb.like(root.get("firstName"), firstName);
        } else return null;
    }

    public Specification<User> lastName(final String lastName) {
        if (lastName != null && !lastName.isEmpty()) {
            return (Specification<User>) (root, query, cb) -> cb.like(root.get("lastName"), lastName);
        } else return null;
    }

    public Specification<User> birthDate(final String birthDate) {
        if (birthDate != null) {
            String[] str = birthDate.split("/");
            int day = Integer.parseInt(str[0]);
            int month = Integer.parseInt(str[1]);
            int year = Integer.parseInt(str[2]);
            return (Specification<User>) (root, query, cb) -> cb.equal(root.get("birthDate"), LocalDate.of(year, month, day));
        } else return null;
    }

    public Specification<User> phoneNumber(final String phoneNumber) {
        if (phoneNumber != null && !phoneNumber.isEmpty()) {
            return (Specification<User>) (root, query, cb) -> cb.like(root.get("phoneNumber"), phoneNumber);
        } else return null;
    }
    public Specification<User> phoneNumberRequired(final boolean phoneNumberRequired) {
        if(phoneNumberRequired) {
            return (Specification<User>) (root, query, cb) -> cb.isNotNull(root.get("phoneNumber"));
        }else return null;
    }

}
