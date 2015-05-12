package ch.unige.idsi.sportit.sportitfinal;
import org.springframework.roo.addon.web.mvc.controller.scaffold.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/cheminses")
@Controller
@RooWebScaffold(path = "cheminses", formBackingObject = Chemins.class)
public class CheminsController {
}
