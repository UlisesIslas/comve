package mx.edu.utez.comverecu.controller;

import mx.edu.utez.comverecu.entity.Commentary;
import mx.edu.utez.comverecu.entity.Request;
import mx.edu.utez.comverecu.entity.Roles;
import mx.edu.utez.comverecu.entity.Users;
import mx.edu.utez.comverecu.security.BlacklistController;
import mx.edu.utez.comverecu.service.CityLinkService;
import mx.edu.utez.comverecu.service.CommentaryService;
import mx.edu.utez.comverecu.service.RequestAttachmentsService;
import mx.edu.utez.comverecu.service.RequestService;
import mx.edu.utez.comverecu.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping(value = "/request")
public class RequestController {

    @Autowired
    private RequestService requestService;
    @Autowired
    private UserService userService;
    @Autowired
    private CommentaryService commentaryService;
    @Autowired
    RequestAttachmentsService attachmentsService;
    @Autowired
    private CityLinkService linkService;

    @RequestMapping(value = "/amount/{id}", method = RequestMethod.GET)
    public String amount(Model model, @PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
        Request request = requestService.findById(id);
        if (!request.equals(null)) {
            model.addAttribute("request", requestService.findById(id));
            model.addAttribute("listRequests", requestService.findAll());
            return "requests/amountRequests";
        } else {
            redirectAttributes.addFlashAttribute("msg_error", "Registro No Encontrado");
            return "redirect:/request/list";
        }
    }

    @RequestMapping(value = "/details/{id}", method = RequestMethod.GET)
    public String details(Model model, @PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
        if (!requestService.findById(id).equals(null)) {
            model.addAttribute("request", requestService.findById(id));
            model.addAttribute("attachment", attachmentsService.findByRequestId(id));
            return "/requests/detailsRequests";
        } else {
            redirectAttributes.addFlashAttribute("msg_error", "La solicitud que buscas no existe");
            return "redirect:/request/list";
        }
    }
/*
    @PostMapping(value = "/update")
    public String actualizar(@ModelAttribute("request") Request request, Model modelo,
                             RedirectAttributes redirectAttributes) {
        Request obj = requestService.findById(request.getId());
        if (!BlacklistController.checkBlacklistedWords(obj.getDescription())) {
            obj.setPaymentAmount(request.getPaymentAmount());
            if (obj != null) {
                requestService.save(obj);
                modelo.addAttribute("listRequests", requestService.findAll());
            }
            return "redirect:/request/list";
        } else {
            redirectAttributes.addFlashAttribute("msg_error", "Ingresó una o más palabras prohibidas.");
            return "redirect:/request/list";
        }

    }*/

    @PostMapping(value = "/update")
    public String actualizar(@ModelAttribute("request") Request request, Model modelo,
                             RedirectAttributes redirectAttributes) {
        Request obj = requestService.findById(request.getId());
        if (!BlacklistController.checkBlacklistedWords(obj.getDescription())) {
            obj.setPaymentAmount(request.getPaymentAmount());
            if (obj != null) {
                requestService.save(obj);
                modelo.addAttribute("listRequests", requestService.findAll());
            }
            return "redirect:/request/list";
        } else {
            redirectAttributes.addFlashAttribute("msg_error", "Ingresó una o más palabras prohibidas.");
            return "redirect:/request/list";
        }

    }

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String findAll(Model model, Pageable pageable, Authentication authentication, HttpSession session) {
        Users user = userService.findByUsername(authentication.getName());
        user.setPassword(null);
        session.setAttribute("user", user);
        /*Page<Request> listRequests = requestService
                .listarPaginacion(PageRequest.of(pageable.getPageNumber(), 2, Sort.by("startDate").descending()));*/
        model.addAttribute("listRequests", requestService.findAllByCityId(linkService.findByUserId(user.getId()).getCity().getId()));
        return "requests/listRequests";
    }

    @GetMapping("/create")

    public String create(Request request, Model modelo) {
        modelo.addAttribute("listRequests", requestService.findAll());
        return "requests/amountRequests";
    }
    @GetMapping(value = "/find/{id}")
    public String findOne(Model model, @PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
        Request request = requestService.findById(id);
        if (!request.equals(null)) {
            model.addAttribute("request", request);
        } else {
            redirectAttributes.addFlashAttribute("msg_error", "No se encontró la solicitud solicitada");
        }
        return "requests/listRequests";
    }

    @PostMapping(value = "/save")
    public String save(Model model, Request request, RedirectAttributes redirectAttributes) {
        String msgOk = "";
        String msgError = "";

        if (!BlacklistController.checkBlacklistedWords(request.getDescription())) {
            if (request.getId() != null) {
                msgOk = "Solictud Actualizada correctamente";
                msgError = "La solicitud NO pudo ser Actualizada correctamente";
            } else {
                msgOk = "Solicitud Guardada correctamente";
                msgError = "La solicitud NO pudo ser Guardada correctamente";
            }

            boolean res = requestService.save(request);
            if (res) {
                redirectAttributes.addFlashAttribute("msg_success", msgOk);
            } else {
                redirectAttributes.addFlashAttribute("msg_error", msgError);
            }
        } else {
            redirectAttributes.addFlashAttribute("msg_error", "Ingresó una o más palabras prohibidas.");
        }
        return "redirect:/request/list";

    }

    @RequestMapping(value = "/commentary/{id}", method = RequestMethod.GET)
    public String chat(@PathVariable("id") long id, Authentication authentication, HttpSession session, Model model,
                       RedirectAttributes redirectAttributes, Commentary commentary) {
        Users user = userService.findByUsername(authentication.getName());
        user.setPassword(null);
        session.setAttribute("user", user);
        model.addAttribute("listComents", commentaryService.findAllByRequestId(id));
        model.addAttribute("request", requestService.findById(id));
        return "requests/comments";
    }

    @RequestMapping(value = "/commentary/save/{id}", method = RequestMethod.POST)
    public String saveCommentary(Model model, Commentary commentary, Authentication authentication,
                                 HttpSession session, @PathVariable("id") long id, RedirectAttributes redirectAttributes) {
        Users user = userService.findByUsername(authentication.getName());
        user.setPassword(null);
        session.setAttribute("user", user);
        commentary.setRequest(requestService.findById(id));
        if (!BlacklistController.checkBlacklistedWords(commentary.getContent())) {
            Users tmp = userService.findById(user.getId());
            tmp.setPassword(userService.findPasswordById(tmp.getId()));
            Roles tmpRole = (Roles) tmp.getRoles().toArray()[0];
            if (tmpRole.getAuthority().equals("ROL_PRESIDENTE")) {
                commentary.setAutor("Presidente");
            } else {
                commentary.setAutor("Enlace");
            }
            commentary.setId(null);
            boolean res = commentaryService.save(commentary);
            if (res) {
                redirectAttributes.addFlashAttribute("msg_success", "Comentario publicado");
            } else {
                redirectAttributes.addFlashAttribute("msg_error", "Ocurrió un error al publicar el comentario");
            }
        } else {
            redirectAttributes.addFlashAttribute("msg_error", "Ingresó una o más palabras prohibidas.");
        }
        return ("redirect:/request/commentary/" + id);
    }
}
