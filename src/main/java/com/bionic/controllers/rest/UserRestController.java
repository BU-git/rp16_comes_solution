package com.bionic.controllers.rest;

import com.bionic.dto.PasswordsDTO;
import com.bionic.exception.auth.impl.CredentialsExpired;
import com.bionic.exception.auth.impl.PasswordIncorrectException;
import com.bionic.exception.auth.impl.TemporaryPassword;
import com.bionic.exception.auth.impl.UserNotExistsException;
import com.bionic.model.User;
import com.bionic.service.MailService;
import com.bionic.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Date;
import java.util.List;

import static com.bionic.service.UserService.ONE_HOUR;

/**
 * @author taras.yaroshchuk
 */
@RestController
@RequestMapping("/rest/api/users")
public class UserRestController {

    @Autowired
    private UserService userService;

    @Autowired
    MailService mailService;

    @RequestMapping(method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public List<User> getAllUsers() {
        return userService.getAll();
    }

    @RequestMapping(value = "{id}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public User getUser(@PathVariable int id) throws UserNotExistsException {
        User user = userService.findById(id);
        if (user == null) throw new UserNotExistsException();
        return user;
    }

    @RequestMapping(value = "{id}", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.NO_CONTENT) //204
    public void putUser(@PathVariable int id, @Valid @RequestBody User user) {
        userService.saveUser(user);
    }

    @RequestMapping(value = "{id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT) //204
    public void deleteUser(@PathVariable int id) {
        userService.delete(id);
    }


    @RequestMapping(value = "login", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public User login() throws CredentialsExpired, TemporaryPassword, UserNotExistsException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String name = auth.getName();
        User user = userService.findByUserEmail(name);

        if (user.getPasswordExpire().before(new Date())) throw new CredentialsExpired();
        if (user.getPasswordExpire().before(new Date(System.currentTimeMillis() + ONE_HOUR))) throw new TemporaryPassword();
        if (user == null) throw new UserNotExistsException();
        return user;
    }

    @RequestMapping(value = "password", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    public void changePassword(@RequestBody PasswordsDTO passwordsDTO) throws UserNotExistsException, PasswordIncorrectException {
        userService.changePassword(passwordsDTO.getEmail(), passwordsDTO.getOldPassword(), passwordsDTO.getNewPassword());
    }
}
