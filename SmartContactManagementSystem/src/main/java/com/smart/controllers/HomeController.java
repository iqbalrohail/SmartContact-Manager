package com.smart.controllers;

import com.smart.data.transfer.objects.MessageDto;
import com.smart.data.transfer.objects.UserDto;
import com.smart.domain.UserDomain;
import com.smart.repositories.UserRepository;
import com.smart.services.UserServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Controller
public class HomeController {

    @Autowired
    UserServices userServices;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @RequestMapping("/")
    public String home() {
        return "home";
    }

    @RequestMapping("/about")
    public String about() {
        return "about";
    }

    @RequestMapping("/signup")
    public String signup(Model model) {
        model.addAttribute("user", new UserDomain());
        return "signup";
    }

    @RequestMapping("/signin")
    public String login(Model model) {
        model.addAttribute("title", "Login-page");
        return "login";
    }

    //@PostMapping(value = "/register", consumes= MediaType.MULTIPART_FORM_DATA_VALUE)
    @RequestMapping(value = ("/do_register"), method = RequestMethod.POST)
    public String registerUser(@Valid @ModelAttribute("user") UserDto userDto, BindingResult bindingResult, @RequestParam(value = "agreement", defaultValue = "false") Boolean agreement, @RequestParam("profileImage") MultipartFile file, Model model, HttpSession session) {
        // for password encoding (Refference : config package)
        userDto.setUserPassword(bCryptPasswordEncoder.encode(userDto.getUserPassword()));
        userDto.setUserRole("ROLE_USER");
        userDto.setUserEnabledStatus(true);

        try {
            if (file.isEmpty()) {
                userDto.setUserImageUrl("defaultContact.jpeg");
            } else {
                userDto.setUserImageUrl(file.getOriginalFilename());
                File saveFile = new ClassPathResource("static/img").getFile();
                Files.copy(file.getInputStream(), Paths.get(saveFile.getAbsolutePath() + File.separator + file.getOriginalFilename()), StandardCopyOption.REPLACE_EXISTING);
            }

            if (!agreement) {
                System.out.println("not agreed terms and condition");
                throw new Exception("not agreed terms and condition");
            }

            if (bindingResult.hasErrors()) {
                model.addAttribute("user", userDto);
                return "signup";
            }

            UserDomain userDomain = userServices.mapDtoObjectToDomain(userDto);
            UserDomain result = userRepository.saveAndFlush(userDomain);
            model.addAttribute("user", new UserDto());
            session.setAttribute("message", new MessageDto("Successfully Registered", "alert-success"));
            return "signup";

        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("user", userDto);
            session.setAttribute("message", new MessageDto("something went wrong !! "+e.getMessage(), "alert-danger"));
            return "signup";
        }
    }
}
