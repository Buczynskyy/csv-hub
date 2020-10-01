package pl.buczeq.csv.integrationTest;

import com.opencsv.CSVWriter;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.apache.commons.lang3.RandomStringUtils.randomNumeric;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.greaterThanOrEqualTo;

import pl.buczeq.Application;
import pl.buczeq.user.*;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = Application.class)
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
public class UserControllerIntegrationTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private UserRepository userRepository;

    @Test
    public void whenValidInput_thenCreateUser() throws Exception {
        String[] data = new String[]{"bob", "hammerman", "1/1/2000", "123456789"};

        mvc.perform(multipart("/api/users")
                .file(convertStringArrayToCsvAndThenToMultipartfile(data)))
                .andDo(print())
                .andExpect(status().isOk());


        List<User> found = userRepository.findAll();
        assertThat(found).extracting(User::getFirstName).containsOnly("bob");
    }

    @Test
    public void givenUsers_whenDeleteUsers_thenStatus200() throws Exception {
        createTestUser(randomAlphabetic(10));
        createTestUser(randomAlphabetic(10));

        mvc.perform(delete("/api/users"))
                .andDo(print())
                .andExpect(status().isOk());
        assertThat(userRepository.findAll()).isEmpty();
    }

    @Test
    public void givenUser_whenDeleteUserById_thenStatus200() throws Exception {
        createTestUser(randomAlphabetic(10));

        mvc.perform(delete("/api/users/1"))
                .andDo(print())
                .andExpect(status().isOk());
        assertThat(userRepository.findAll()).isEmpty();
    }

    @Test
    public void givenUser_whenGetUser_thenStatus200() throws Exception {
        createTestUser("bob");

        mvc.perform(get("/api/users").contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", hasSize(greaterThanOrEqualTo(1))))
                .andExpect(jsonPath("$.content[0].firstName").value("bob"));
    }


    private void createTestUser(String firstName) {
        User user = new User();
        user.setFirstName(firstName);
        user.setLastName(randomAlphabetic(10));
        user.setBirthDate(LocalDate.now());
        user.setPhoneNumber(randomNumeric(9));

        userRepository.saveAndFlush(user);
    }

    private MockMultipartFile convertStringArrayToCsvAndThenToMultipartfile(String[] data) throws IOException {

        List<String[]> stringArray = new ArrayList<>();
        stringArray.add(new String[]{"first_name", "last_name", "brith_date", "phone_no"});
        stringArray.add(data);

        Path path = Files.createTempFile("", "testfile.csv");
        CSVWriter writer = new CSVWriter(new FileWriter(path.toString()));
        writer.writeAll(stringArray);
        writer.close();
        return new MockMultipartFile("file", "testfile.csv", "text/plain", Files.readAllBytes(path));
    }

}