package edu.miu.cs.cs425.carrentalswe.controller;

import edu.miu.cs.cs425.carrentalswe.model.Category;

import edu.miu.cs.cs425.carrentalswe.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping(value ="/eRental/")
public class CategoryController {
    @Autowired
    CategoryService categoryService;


    @GetMapping(value = {"secured/category/list"})
    public ModelAndView getAllVehicles(){
        ModelAndView modelAndView = new ModelAndView();
        List<Category> categories=categoryService.getAll();
        modelAndView.addObject("categories", categories);
        modelAndView.setViewName("secured/category/list");
        return modelAndView;
    }

    @GetMapping(value = {"public/category/browse"})
    public ModelAndView getAllBrowseCategories(){
        ModelAndView modelAndView = new ModelAndView();
        List<Category> categories=categoryService.getAll();
        modelAndView.addObject("categories", categories);
        modelAndView.setViewName("public/vehicleType");
        return modelAndView;
    }

    @GetMapping(value = "secured/category/new")
    public String newCategoryForm(Model model) {
        model.addAttribute("category", new Category());
        return "secured/category/new";
    }

    @PostMapping(value = "secured/category/new")
    public String addNewCategory(@Valid @ModelAttribute("category") Category category,
                                 BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("errors", bindingResult.getAllErrors());
            System.out.println("found errors");
            return "secured/category/new";
        }
        category = categoryService.createCategory(category);
        System.out.println("now try to redirect");
        return "redirect:/eRental/secured/category/list";
    }

    @GetMapping(value = {"secured/category/edit/{categoryId}"})
    public String editCategory(@PathVariable Long categoryId, Model model) {
        Category category = categoryService.getCategoryById(categoryId);
        if(category != null) {
            model.addAttribute("category", category);
            return "secured/category/edit";
        } else {
            // TODO -- may do nothing
        }
        return "secured/category/list";
    }

    @PostMapping(value={"secured/category/edit"})
    public String updateBook(@Valid @ModelAttribute("category") Category category,
                             BindingResult bindingResult,
                             Model model) {
        if(bindingResult.hasErrors()) {
            model.addAttribute("errors", bindingResult.getAllErrors());
            return "secured/category/edit";
        }
        category = categoryService.createCategory(category);
        return "redirect:/eRental/secured/category/list";
    }

    @GetMapping(value = {"secured/category/delete/{categoryId}"})
    public String deleteBook(@PathVariable Long categoryId) {
        categoryService.deleteCategoryById(categoryId);
        return "redirect:/eRental/secured/category/list";
    }

}
