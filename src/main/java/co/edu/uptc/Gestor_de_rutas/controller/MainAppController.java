package co.edu.uptc.Gestor_de_rutas.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
public class MainAppController {

    @GetMapping("/")
    public String mainPage(){
        return "redirect:/pages/map.html";
    }
}
