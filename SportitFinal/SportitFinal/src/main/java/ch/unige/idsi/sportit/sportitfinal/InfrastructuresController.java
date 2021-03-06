package ch.unige.idsi.sportit.sportitfinal;
import org.springframework.roo.addon.web.mvc.controller.scaffold.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/infrastructureses")
@Controller
@RooWebScaffold(path = "infrastructureses", formBackingObject = Infrastructures.class)
/**
 * Controller du model Infrastructures
 * @author Florine et Tim
 * @version 1.0
 *
 */
public class InfrastructuresController {
}
