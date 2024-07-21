package co.edu.uptc.Gestor_de_rutas.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

//El objetivo de este controlador es redirigir a las distintas páginas de la aplicación, namás

@Controller
public class MainAppController {


    @GetMapping("/")
    public String mainPage() {
        return "redirect:/pages/OrderDelivery.html";
    }


}
