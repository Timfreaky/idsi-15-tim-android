package ch.unige.idsi.sportit.sportitfinal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/adresse/**")
@Controller
public class AdresseController {

    @RequestMapping
    public String index() {
        return "adresse/index";
    }
}
