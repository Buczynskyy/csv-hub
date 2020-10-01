package pl.buczeq.csv.integrationTest;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;
import pl.buczeq.user.User;
import pl.buczeq.user.UserRepository;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
public class UserRepositoryIntegrationTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private UserRepository userRepository;

    @Test
    public void whenFindByFirstName_thenReturnUser() {
        User alex = new User(new String[]{"alex", "alexlastname", "1/1/2000"});
        entityManager.persist(alex);
        entityManager.flush();
        User found = userRepository.findByFirstName(alex.getFirstName());

        assertThat(found.getFirstName())
                .isEqualTo(alex.getFirstName());
    }

}
