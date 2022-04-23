package mx.edu.utez.comverecu.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import mx.edu.utez.comverecu.entity.Users;
import mx.edu.utez.comverecu.service.CityLinkService;
import mx.edu.utez.comverecu.service.RolesService;
import mx.edu.utez.comverecu.service.SuburbService;
import mx.edu.utez.comverecu.service.UserService;

@Controller
@RequestMapping(value = "/users")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private RolesService rolesService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private CityLinkService linkService;

    @Autowired
    private SuburbService suburbService;

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String findAll(Model model, Pageable pageable) {
        Page<Users> listUsers = userService
        .listPagination(PageRequest.of(pageable.getPageNumber(), 10, Sort.by("id").descending()));
        model.addAttribute("listUsers", listUsers);
        return "users/listUser";
    }

    @RequestMapping(value = "/find/{id}", method = RequestMethod.GET)
    public String findOne(Model model, @PathVariable("id") long id, RedirectAttributes redirectAttributes) {
        Users user = userService.findById(id);
        if (!user.equals(null)) {
            model.addAttribute("user", user);
        } else {
            redirectAttributes.addFlashAttribute("msg_error", "No se encontró el usuario solicitado");
        }
        return "";
    }

    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public String create(Users user, Model modelo) {
        modelo.addAttribute("listSuburbs", suburbService.findAll());
        return "users/create";
    }

    @RequestMapping(value = "/disable/{id}", method = RequestMethod.GET)
    public String disableUser(@PathVariable("id") long id, RedirectAttributes redirectAttributes,
    Authentication authentication, HttpSession session) {
        Users user = userService.findByUsername(authentication.getName());
        user.setPassword(null);
        session.setAttribute("user", user);
        user.setPassword(userService.findPasswordById(user.getId()));
        Users tmp = userService.findById(id);
        tmp.setPassword(userService.findPasswordById(id));
        if (user.getUsername().equals(tmp.getUsername())) {
            redirectAttributes.addFlashAttribute("msg_error", "No puedes deshabilitarte");
            return "redirect:/users/list";
        } else {
            if (tmp.getEnabled() == 1) {
                tmp.setEnabled(0);
                redirectAttributes.addFlashAttribute("msg_success", "Usuario deshabilitado");
            } else {
                tmp.setEnabled(1);
                redirectAttributes.addFlashAttribute("msg_success", "Usuario habilitado");
            }
        }
        userService.save(tmp);
        return "redirect:/users/list";
    }
    
}
