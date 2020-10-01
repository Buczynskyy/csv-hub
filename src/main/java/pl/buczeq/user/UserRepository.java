package pl.buczeq.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface UserRepository extends PagingAndSortingRepository<User, Integer>,
        JpaRepository<User, Integer>, JpaSpecificationExecutor<User> {

    User findByFirstName(String firstName);

    boolean existsByPhoneNumber(String phoneNumber);

    void deleteById(long id);
}
