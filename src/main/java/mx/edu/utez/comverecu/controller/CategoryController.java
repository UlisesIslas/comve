package mx.edu.utez.comverecu.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import mx.edu.utez.comverecu.entity.Category;
import mx.edu.utez.comverecu.security.BlacklistController;
import mx.edu.utez.comverecu.service.CategoryService;

@Controller
@RequestMapping(value = "/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String listCategories(Model model, RedirectAttributes redirectAttributes, Pageable pageable,
            Authentication authentication, HttpSession session) {
        Page<Category> listCategory = categoryService
                .listPagination(PageRequest.of(pageable.getPageNumber(), 10, Sort.by("id").ascending()));
        model.addAttribute("listCategories", listCategory);
        return "category/list";
    }

    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public String createCategory(Model model, RedirectAttributes redirectAttributes, Category category) {
        return "category/create";
    }

    @RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
    public String categoryEdit(Model model, RedirectAttributes redirectAttributes, @PathVariable("id") long id,
            Category category) {
        Category tmp = categoryService.findById(id);
        if (!tmp.equals(null)) {
            model.addAttribute("category", tmp);
            return "category/edit";
        } else {
            redirectAttributes.addFlashAttribute("msg_error", "El Servicio p??blico solicitado no existe.");
            return "redirect:/category/list";
        }
    }

    @RequestMapping(value = "/update/{id}", method = RequestMethod.POST)
    public String categoryUpdate(Model model, RedirectAttributes redirectAttributes, @PathVariable("id") long id,
            Category category) {
        Category tmp = categoryService.findById(id);
        if (!tmp.equals(null)) {
            if (!BlacklistController.checkBlacklistedWords(tmp.getName())) {
                tmp.setName(category.getName());
                boolean res = categoryService.save(tmp);
                if (res) {
                    redirectAttributes.addFlashAttribute("msg_success", "Servicio P??blico actualizado");
                    return "redirect:/category/list";
                } else {
                    redirectAttributes.addFlashAttribute("msg_error",
                            "Ocurri?? un error al actualizar el Servicio P??blico");
                }
            } else {
                redirectAttributes.addFlashAttribute("msg_error", "Ingres?? una o m??s palabras prohibidas");
            }
        } else {
            redirectAttributes.addFlashAttribute("msg_error", "El Servicio p??blico solicitado no existe.");
        }
        return "redirect:/category/edit/" + id;
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public String save(Model model, RedirectAttributes redirectAttributes, Category category) {
        if (!categoryService.exists(category.getName())) {
            if (!BlacklistController.checkBlacklistedWords(category.getName())) {
                boolean res = categoryService.save(category);
                if (res) {
                    redirectAttributes.addFlashAttribute("msg_success", "Servicio P??blico registrado exitosamente");
                    return "redirect:/category/list";
                } else {
                    redirectAttributes.addFlashAttribute("msg_error", "No se pudo registrar el Servicio P??blico");
                }
            } else {
                redirectAttributes.addFlashAttribute("msg_error", "Ingres?? una o m??s palabras prohibidas");
            }
        } else {
            redirectAttributes.addFlashAttribute("msg_error", "Este servicio p??blico ya existe");
        }
        return "redirect:/category/create";
    }

}
