package pe.edu.cibertec.DAAII_T1_KAPUSTIN_PEREZ_KEVIN_JOEL.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pe.edu.cibertec.DAAII_T1_KAPUSTIN_PEREZ_KEVIN_JOEL.model.bd.Usuario;
import pe.edu.cibertec.DAAII_T1_KAPUSTIN_PEREZ_KEVIN_JOEL.service.UsuarioService;

@Controller
@RequestMapping("/auth")
public class LoginController {

    private final UsuarioService usuarioService;

    public LoginController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @GetMapping("/login")
    public String login(){
        return "auth/frmlogin";
    }

    @GetMapping("/registro")
    public String registro(Model model) {
        model.addAttribute("usuario", new Usuario());
        return "auth/registro";
    }

    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String nomusuario = auth.getName();
        Usuario usuario = usuarioService.buscarUsuarioXNomUsuario(nomusuario);
        model.addAttribute("nomusuario", usuario.getNomusuario());
        return "layout";
    }

    @PostMapping("/registro")
    public String registro(@ModelAttribute Usuario usuario, Model model) {
        usuarioService.guardarUsuario(usuario);
        model.addAttribute("message", "Usuario registrado exitosamente");
        model.addAttribute("usuario", new Usuario());
        return "auth/registro";
    }
}