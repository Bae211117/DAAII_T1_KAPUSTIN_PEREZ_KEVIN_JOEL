package pe.edu.cibertec.DAAII_T1_KAPUSTIN_PEREZ_KEVIN_JOEL.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    @GetMapping("/home")
    public String home(){
        return "home";
    }
}
