package ch.unige.idsi.sportit.roo;
import org.springframework.roo.addon.web.mvc.controller.scaffold.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/chemins")
@Controller
@RooWebScaffold(path = "chemins", formBackingObject = Chemin.class)
public class CheminController {
}
