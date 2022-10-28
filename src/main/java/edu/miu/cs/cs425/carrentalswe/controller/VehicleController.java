package edu.miu.cs.cs425.carrentalswe.controller;

import edu.miu.cs.cs425.carrentalswe.model.*;
import edu.miu.cs.cs425.carrentalswe.repository.CategoryRepository;
import edu.miu.cs.cs425.carrentalswe.repository.UserRepository;
import edu.miu.cs.cs425.carrentalswe.service.ExternalUserService;
import edu.miu.cs.cs425.carrentalswe.service.VehicleService;
import edu.miu.cs.cs425.carrentalswe.utils.FileUploadUtil;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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
public class VehicleController {

    private VehicleService vehicleService;
   private CategoryRepository categoryRepository;
    private UserRepository userRepository;
    private ExternalUserService externalUserService;
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();


    public VehicleController(VehicleService vehicleService, CategoryRepository categoryRepository, UserRepository userRepository, ExternalUserService externalUserService) {
        this.vehicleService = vehicleService;
        this.categoryRepository = categoryRepository;
        this.userRepository = userRepository;
        this.externalUserService = externalUserService;
    }

    @GetMapping(value={"/secured/addVehicle"})
    public String getVehiclepage(Model model){
        Vehicle vehicle = new Vehicle();
        vehicle.setCategory(new Category());
        vehicle.setExternalUser(new ExternalUser());
        List<Category> categoryList = categoryRepository.findAll();
        model.addAttribute("vehicle", vehicle);
        model.addAttribute("categories", categoryList);
        return "/secured/addVehicle";
    }


    @PostMapping(value={"/secured/addVehicle"})
    public String registerUser(@Valid
                               @ModelAttribute("vehicle") Vehicle vehicle,
                               @RequestParam("image") MultipartFile multipartFile,
                               @RequestParam("username") String username,
                               BindingResult bindingResult,
                               Model model)throws IOException {

        if(bindingResult.hasErrors()) {
            model.addAttribute("errors", bindingResult.getAllErrors());
            vehicle.setCategory(new Category());
            vehicle.setExternalUser(new ExternalUser());
            model.addAttribute("vehicle", vehicle);
            return "/secured/addVehicle";
        }
        String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
        vehicle.setImageUrl(fileName);
        vehicle.setExternalUser(getLoggedInUser(username));
        vehicleService.saveVehicle(vehicle);
        String uploadDir = "src/main/resources/static/images/vehicles";

        FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String name = auth.getName();
        if(name.equals("admin"))
        return "redirect:/eRental/secured/vehicleList";

        return "redirect:/eRental/secured/vehicleList/contractor";
    }

    @GetMapping(value={"/secured/vehicleList"})
    public String getVehiclespage(Model model){
        List<Vehicle> vehicleList = vehicleService.getAllVehicles();
        vehicleList.forEach(vehicle -> {
            String image_Url = "/images/vehicles/"+ vehicle.getImageUrl();
            vehicle.setImageUrl(image_Url);
        });

        model.addAttribute("vehicles", vehicleList);
        return "secured/vehicleList";
    }

    @GetMapping(value={"/public/browseVehicles"})
    public String getBrowsepage(Model model){
        List<Vehicle> vehicleList = vehicleService.getAvailableVehicles("Available");
        vehicleList.forEach(vehicle -> {
            String image_Url = "/images/vehicles/"+ vehicle.getImageUrl();
            vehicle.setImageUrl(image_Url);
        });

        model.addAttribute("vehicles", vehicleList);
        return "public/browseVehicles";
    }

    @GetMapping(value={"/public/browseVehicles/{id}"})
    public String getBrowseByCategorypage(Model model, @PathVariable("id") Long categoryId){
        Category category = categoryRepository.getById(categoryId);
        List<Vehicle> vehicleList = vehicleService.getVehiclesByCategory("Available", category);
        vehicleList.forEach(vehicle -> {
            String image_Url = "/images/vehicles/"+ vehicle.getImageUrl();
            vehicle.setImageUrl(image_Url);
        });

        model.addAttribute("vehicles", vehicleList);
        return "public/browseVehicles";
    }

    @GetMapping(value={"/secured/vehicleList/contractor"})
    public String getVehiclesContrcatorpage(Model model){
        ExternalUser externalUser = getLoggedInUser("");
        List<Vehicle> vehicleList = vehicleService.getUserVehicles(externalUser);
        vehicleList.forEach(vehicle -> {
            String image_Url = "/images/vehicles/"+ vehicle.getImageUrl();
            vehicle.setImageUrl(image_Url);
        });

        model.addAttribute("vehicles", vehicleList);
        return "secured/vehicleList";
    }

    private ExternalUser getLoggedInUser(String username){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String name = auth.getName();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            //String currentUserName = authentication.getName();
            User user = userRepository.findByUsername(name).orElseThrow(()-> new UsernameNotFoundException(name));
ExternalUser externalUser = externalUserService.findByUser(user);
return  externalUser;
        }
        return null;
    }

}
