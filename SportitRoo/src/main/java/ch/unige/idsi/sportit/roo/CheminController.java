package ch.unige.idsi.sportit.roo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.roo.addon.web.mvc.controller.scaffold.RooWebScaffold;

@RequestMapping("/chemins")
@Controller
@RooWebScaffold(path = "chemins", formBackingObject = Chemin.class)
public class CheminController {
}
