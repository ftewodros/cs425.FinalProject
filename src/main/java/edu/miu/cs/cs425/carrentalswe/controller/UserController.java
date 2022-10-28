package edu.miu.cs.cs425.carrentalswe.controller;

import edu.miu.cs.cs425.carrentalswe.model.Address;
import edu.miu.cs.cs425.carrentalswe.model.ExternalUser;
import edu.miu.cs.cs425.carrentalswe.model.User;
import edu.miu.cs.cs425.carrentalswe.repository.RolesRepository;
import edu.miu.cs.cs425.carrentalswe.service.ExternalUserService;
import edu.miu.cs.cs425.carrentalswe.utils.FileUploadUtil;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping(value = {"/eRental"})
public class UserController {

   private ExternalUserService externalUserService;

   private RolesRepository rolesRepository;

    public UserController(ExternalUserService externalUserService, RolesRepository rolesRepository) {
        this.externalUserService = externalUserService;
        this.rolesRepository = rolesRepository;
    }

    @GetMapping(value={"/public/register"})
    public String getRegisterpage(Model model){
        ExternalUser externalUser = new ExternalUser();
        externalUser.setUser(new User());
        externalUser.setAddress(new Address());
        model.addAttribute("user", externalUser);
        return "public/register";
    }


    @PostMapping(value={"/public/register"})
    public String registerUser(@Valid
                                   @ModelAttribute("user") ExternalUser externalUser,
                               @RequestParam("image") MultipartFile multipartFile,
                               BindingResult bindingResult,
                               Model model)throws IOException {

        if(bindingResult.hasErrors()) {
            model.addAttribute("errors", bindingResult.getAllErrors());
            externalUser.setUser(new User());
            externalUser.setAddress(new Address());
            model.addAttribute("user", externalUser);
            return "/public/register";
        }
        String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
        externalUser.setIdImage_url(fileName);
        externalUserService.registerUser(externalUser);
        String uploadDir = "src/main/resources/static/images/" + externalUser.getId();

        FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);
        return "redirect:/eRental/public/login?register";
    }

    @GetMapping(value={"/secured/users"})
    public String getUserspage(Model model){
        List<ExternalUser> externalUserList = externalUserService.getUsers();
        model.addAttribute("users", externalUserList);
        return "secured/userslist";
    }


    @GetMapping(value={"/secured/approveUser/{userId}"})
    public String getAproveUserPage(@PathVariable Long userId,Model model){
        ExternalUser externalUser = externalUserService.getOneUser(userId);
        String image_Url = "/images/"+userId + "/" + externalUser.getIdImage_url();
        externalUser.setIdImage_url(image_Url);
        model.addAttribute("user", externalUser);
        return "secured/editUser.html";
    }



    @PostMapping(value={"/secured/approve"})
    public String approveUser(@Valid
                               @ModelAttribute("user") ExternalUser externalUser,
                               BindingResult bindingResult,
                               Model model){

        if(bindingResult.hasErrors()) {
            model.addAttribute("errors", bindingResult.getAllErrors());
            model.addAttribute("user", externalUser);
            return "/secured/approveUser";
        }
ExternalUser externalUser1 = externalUserService.getOneUser(externalUser.getId());
        externalUser1.setApproved(true);
externalUserService.registerUser(externalUser1);
        return "redirect:/eRental/secured/users";
    }

}
