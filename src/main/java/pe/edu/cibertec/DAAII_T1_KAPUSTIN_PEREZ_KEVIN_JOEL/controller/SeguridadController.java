package pe.edu.cibertec.DAAII_T1_KAPUSTIN_PEREZ_KEVIN_JOEL.controller;

import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import pe.edu.cibertec.DAAII_T1_KAPUSTIN_PEREZ_KEVIN_JOEL.model.bd.Usuario;
import pe.edu.cibertec.DAAII_T1_KAPUSTIN_PEREZ_KEVIN_JOEL.service.UsuarioService;

@AllArgsConstructor
@Controller
@RequestMapping("/seguridad")
public class SeguridadController {
    private UsuarioService usuarioService;

    @GetMapping("/cambiar-password")
    public String cambiarPassword(Model model) {
        return "seguridad/nuevacontrasena";
    }

    @PostMapping("/cambiar-password")
    public String cambiarPassword(@RequestParam("newPassword") String newPassword,
                                  @RequestParam("confirmNewPassword") String confirmNewPassword,
                                  RedirectAttributes redirectAttributes) {

        if (!validarContrasena(newPassword)) {
            redirectAttributes.addFlashAttribute("error", "La contraseña debe ser de mínimo 8 caracteres, al menos una letra mayúscula, una letra minúscula, un número y un carácter especial");
            return "redirect:/seguridad/cambiar-password";
        }

        if (!newPassword.equals(confirmNewPassword)) {
            redirectAttributes.addFlashAttribute("error", "Las contraseñas no coinciden");
            return "redirect:/seguridad/cambiar-password";
        }

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode(newPassword);

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String nomusuario = auth.getName();
        Usuario usuario = usuarioService.buscarUsuarioXNomUsuario(nomusuario);
        usuario.setPassword(encodedPassword);
        usuarioService.actualizarUsuario(usuario);

        redirectAttributes.addFlashAttribute("success", "Realizado");
        return "redirect:/seguridad/cambiar-password";
    }

    private boolean validarContrasena(String password) {
        String passwordRegex = "(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*\\W).{8,}";
        return password.matches(passwordRegex);
    }

}