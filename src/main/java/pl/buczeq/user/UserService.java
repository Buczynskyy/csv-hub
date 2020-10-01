package pl.buczeq.user;

import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.stream.Collectors;

@Service
public class UserService {

     private final UserRepository userRepository;

     private final UserSpecification userSpecification;

     Logger logger = LoggerFactory.getLogger(UserService.class);

     public UserService(UserRepository userRepository, UserSpecification userSpecification) {
          this.userRepository = userRepository;
          this.userSpecification = userSpecification;
     }

     public void saveUsers(MultipartFile file) throws IOException, CsvException {
          Path tempFile = Files.createTempFile("", file.getOriginalFilename());
          file.transferTo(tempFile.toFile());

          HashSet<String> savedPhoneNumbers = new HashSet<>();

          userRepository.saveAll(new CSVReaderBuilder(Files.newBufferedReader(tempFile))
                  .withSkipLines(1)
                  .build().readAll().stream()
                  .filter(entry -> entry.length >= 3 && !entry[2].isEmpty())
                  .filter(entry -> entry[3] == null || entry[3].isEmpty() || savedPhoneNumbers.add(entry[3]))
                  .filter(entry -> !userRepository.existsByPhoneNumber(entry[3]))
                  .map(User::new)
                  .collect(Collectors.toList()));
     }

     public void deleteUserById(long userId) {
          userRepository.deleteById(userId);
          logger.info("User with id: " + userId + " deleted");
     }

     public void deleteAllUsers() {
          userRepository.deleteAll();
          logger.info("All users deleted");
     }

     public Page<User> findUsers(final UserFilter filter, final Pageable pageable) {
          return userRepository.findAll(userSpecification.getUserSpecification(filter), pageable);
     }
    }

