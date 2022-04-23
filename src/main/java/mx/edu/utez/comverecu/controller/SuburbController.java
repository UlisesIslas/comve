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

import mx.edu.utez.comverecu.entity.Suburb;
import mx.edu.utez.comverecu.entity.Users;
import mx.edu.utez.comverecu.security.BlacklistController;
import mx.edu.utez.comverecu.service.CityLinkService;
import mx.edu.utez.comverecu.service.CityService;
import mx.edu.utez.comverecu.service.SuburbService;
import mx.edu.utez.comverecu.service.UserService;

@Controller
@RequestMapping(value = "/suburb")
public class SuburbController {

    @Autowired
    private SuburbService suburbService;

    @Autowired
    private CityService cityService;

    @Autowired
    private UserService userService;

    @Autowired
    private CityLinkService linkService;

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String list(Model model, RedirectAttributes redirectAttributes, Pageable pageable) {
        Page<Suburb> listSuburbs = suburbService
                .listPagination(PageRequest.of(pageable.getPageNumber(), 10, Sort.by("id").ascending()));
        model.addAttribute("listSuburbs", listSuburbs);
        return "suburb/list";
    }

    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public String create(Model model, RedirectAttributes redirectAttributes, Suburb suburb,
            Authentication authentication, HttpSession session) {
        Users user = userService.findByUsername(authentication.getName());
        user.setPassword(null);
        session.setAttribute("user", user);
        model.addAttribute("listCities",
                cityService.findAllCitiesByStateId(linkService.findOne(user.getId()).getCity().getState().getId()));
        return "suburb/create";
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public String save(Model model, RedirectAttributes redirectAttributes, Suburb suburb) {
        if (!(BlacklistController.checkBlacklistedWords(suburb.getName())
                || BlacklistController.checkBlacklistedWords(suburb.getPostalCode()))) {
            boolean res = suburbService.save(suburb);
            if (res) {
                model.addAttribute("msg_success", "Se registró la colonia correctamente");
                return "redirect:/suburb/list";
            } else {
                model.addAttribute("msg_error", "Ocurrió un error al registrar la colonia");
            }
        } else {
            model.addAttribute("msg_error", "Ingresó una o más palabras prohibidas");
        }
        return "redirect:/suburb/create";
    }

    @RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
    public String edit(Model model, RedirectAttributes redirectAttributes, Suburb suburb, @PathVariable("id") long id,
            Authentication authentication, HttpSession session) {
        Suburb tmp = suburbService.findOne(id);
        if (!tmp.equals(null)) {
            Users user = userService.findByUsername(authentication.getName());
            user.setPassword(null);
            session.setAttribute("user", user);
            model.addAttribute("listCities",
                    cityService.findAllCitiesByStateId(linkService.findOne(user.getId()).getCity().getState().getId()));
            model.addAttribute("suburb", tmp);
            return "suburb/edit";
        } else {
            redirectAttributes.addFlashAttribute("msg_error", "La colonia seleccionada no existe");
        }
        return "redirect:/suburb/list";
    }

    @RequestMapping(value = "/update/{id}", method = RequestMethod.POST)
    public String update(Model model, RedirectAttributes redirectAttributes, Suburb suburb,
            @PathVariable("id") long id) {
        if (!(BlacklistController.checkBlacklistedWords(suburb.getName())
                || BlacklistController.checkBlacklistedWords(suburb.getPostalCode()))) {
            Suburb tmp = suburbService.findOne(id);
            if (!tmp.equals(null)) {
                suburb.setId(id);
                boolean res = suburbService.save(suburb);
                if (res) {
                    model.addAttribute("msg_success", "Colonia actualizada correctamente");
                    return "redirect:/suburb/list";
                } else {
                    model.addAttribute("msg_error", "Ocurrió un error al actualizar la Colonia");
                }
            } else {
                model.addAttribute("msg_error", "La Colonia seleccionada no existe");
            }
        } else {
            model.addAttribute("msg_error", "Ingresó una o más palabras prohibidas");
        }
        return ("redirect:/suburb/edit/" + id);
    }

}
