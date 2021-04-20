package com.smart.controllers;

import com.smart.data.transfer.objects.MessageDto;
import com.smart.domain.ContactDomain;
import com.smart.domain.UserDomain;
import com.smart.repositories.UserRepository;
import com.smart.repositories.ContactRepository;
import com.smart.services.ContactServices;
import com.smart.services.UserServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserServices userServices;

    @Autowired
    private ContactServices contactServices;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ContactRepository contactRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;


    @ModelAttribute
    public void addCommonData(Model model, Principal principal) {
        String userEmail = principal.getName();
        UserDomain userDomain = userRepository.getUserByName(userEmail);
        // UserDto userDto = userServices.mapDomainObjectToDto(userDomain); causing error
        model.addAttribute("user", userDomain);
    }

    @RequestMapping("/index")
    public String dashBoard(Model model, Principal principal) {
        return "normal/userDashboard";
    }

    @GetMapping("/add-contact")
    public String addContact(Model model) {
        model.addAttribute("contact", new ContactDomain());
        return "normal/addContact";
    }

    @PostMapping("/process-contact")
    public String processContact(@ModelAttribute ContactDomain contactDomain, @RequestParam("profileImage") MultipartFile file, Principal principal, HttpSession session) {
        try {
            if (file.isEmpty()) {
                contactDomain.setContactImageUrl("defaultContact.jpeg");
            } else {
                contactDomain.setContactImageUrl(file.getOriginalFilename());
                File saveFile = new ClassPathResource("static/img").getFile();
                Files.copy(file.getInputStream(), Paths.get(saveFile.getAbsolutePath() + File.separator + file.getOriginalFilename()), StandardCopyOption.REPLACE_EXISTING);
            }
            String userEmail = principal.getName();
            UserDomain userDomain = userRepository.getUserByName(userEmail);
            userDomain.getContactDomain().add(contactDomain);
            contactDomain.setUserDomain(userDomain);
            this.userRepository.saveAndFlush(userDomain);
            session.setAttribute("message", new MessageDto("Contact have been successfully added !", "alert-success"));

        } catch (Exception e) {
            e.printStackTrace();
            session.setAttribute("message", new MessageDto("something went wrong", "alert-danger"));

        }

        return "normal/addContact";
    }

    @GetMapping("/view-contacts")
    public String viewContact(Model model, Principal principal) {
        String userName = principal.getName();
        UserDomain userDomain = this.userRepository.getUserByName(userName);
        int fetchedId = userDomain.getUserId();
        List<ContactDomain> contactDomains = this.contactRepository.getContactsByUser(fetchedId);
        // List<ContactDto> contactDtos =contactServices.mapDomainObjectToDto(contactDomains);
        model.addAttribute("contact", contactDomains);
        return "normal/viewContacts";
    }

    @GetMapping("/contact-detail/{contactId}")
    public String showParticularContact(@PathVariable("contactId") int contactId, Model model) {
        Optional<ContactDomain> contact = this.contactRepository.findById(contactId);
        ContactDomain contactDomain = contact.get();
        model.addAttribute("contact", contactDomain);
        return "normal/contact-detail";
    }

    @GetMapping("/delete/{contactId}")
    public String deleteContact(@PathVariable("contactId") int contactId, Model model, HttpSession session) {
        Optional<ContactDomain> contactDomainOptional = this.contactRepository.findById(contactId);
        ContactDomain contactDomain = contactDomainOptional.get();
        this.contactRepository.delete(contactDomain);
        session.setAttribute("message", new MessageDto("contact have been deleted successfully", "alert-success"));

        return "redirect:/user/view-contacts";
    }

    @PostMapping("/update/{contactId}")
    public String updateContact(@PathVariable("contactId") int contactId, Model model) {
        ContactDomain contactDomain = this.contactRepository.findById(contactId).get();
        model.addAttribute("contact", contactDomain);
        return "normal/updateContact";
    }

    @PostMapping("/update-contact")
    public String processUpdate(@ModelAttribute ContactDomain contactDomain, @RequestParam("profileImage") MultipartFile file, Principal principal, HttpSession session) {
        try {
            ContactDomain oldContactDetails = this.contactRepository.findById(contactDomain.getContactId()).get();
            if (!file.isEmpty()) {
                // deleting old file
                File deleteFile = new ClassPathResource("static/img").getFile();
                File file1 = new File(deleteFile, oldContactDetails.getContactImageUrl());
                file1.delete();

                // updating new file
                contactDomain.setContactImageUrl(file.getOriginalFilename());
                File saveFile = new ClassPathResource("static/img").getFile();
                Files.copy(file.getInputStream(), Paths.get(saveFile.getAbsolutePath() + File.separator + file.getOriginalFilename()), StandardCopyOption.REPLACE_EXISTING);
            } else {

                contactDomain.setContactImageUrl(oldContactDetails.getContactImageUrl());
            }

            String userEmail = principal.getName();
            UserDomain userDomain = userRepository.getUserByName(userEmail);
            contactDomain.setUserDomain(userDomain);
            this.contactRepository.saveAndFlush(contactDomain);
            session.setAttribute("message", new MessageDto("Contact have been Updated!", "alert-success"));

        } catch (Exception e) {
            session.setAttribute("message", new MessageDto("Error updating your contact!", "alert-danger"));
        }
        return "redirect:/user/contact-detail/" + contactDomain.getContactId();
    }

    @GetMapping("/userProfile")
    public String showUserProfile() {
        return "normal/userProfile";
    }

    @GetMapping("/settings")
    public String changePasswordPage()
    {
        return "normal/settings";
    }

    @PostMapping("/change-password")
    public String changePassword(@RequestParam("oldPassword") String oldPassword , @RequestParam("newPassword") String newPassword , Principal principal , HttpSession session)
    {
        String userEmail = principal.getName();
        UserDomain currentUser = userRepository.getUserByName(userEmail);

        if(bCryptPasswordEncoder.matches(oldPassword , currentUser.getUserPassword()))
        {

            currentUser.setUserPassword(bCryptPasswordEncoder.encode(newPassword));
            this.userRepository.saveAndFlush(currentUser);
            session.setAttribute("message", new MessageDto("Your password have been changed !", "alert-success"));

        }
        else {

            session.setAttribute("message", new MessageDto("Error changing your password!", "alert-danger"));
            return "redirect:/user/settings";
        }
        return "redirect:/user/index";
    }


}
