package pl.buczeq.user;

import com.opencsv.exceptions.CsvException;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.ws.rs.*;
import java.io.IOException;

@Controller
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public void handleFileUpload(@RequestParam("file") MultipartFile file) throws IOException, CsvException {
        userService.saveUsers(file);
    }

    @Transactional
    @RequestMapping(value = "/{userId}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.OK)
    public void handleDeleteUser(@PathVariable long userId) {
        userService.deleteUserById(userId);
    }

    @RequestMapping(method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.OK)
    public void handleDeleteAllUsers() {
        userService.deleteAllUsers();
    }

    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public Page<User> findUsers(@BeanParam UserFilter filter,
                                @BeanParam PageableParams pageParams) {
        return userService.findUsers(filter, pageParams.toPageable());
    }
}
