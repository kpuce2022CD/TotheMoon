package Youtube.SpringbootServer.Login;

import lombok.AllArgsConstructor;
import org.springframework.core.ResolvableType;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@Controller
@AllArgsConstructor
public class OAuth2LoginController {

    private final HttpSession httpSession;

    @GetMapping("/")
    public String index(Model model) {


        return "redirect:/home";
    }

//
//
//    private static final String authorizationRequestBaseUrl = "oauth2/authorization";
//    Map<String, String> oauth2AuthenticationUrls = new HashMap<>();
//    private final ClientRegistrationRepository clientRegistrationRepository;
//
//    @GetMapping("/login2")
//    public String getLoginPage(Model model) throws Exception {
//
//        Iterable<ClientRegistration> clientRegistrations = null;
//        ResolvableType type = ResolvableType.forInstance(clientRegistrationRepository)
//                .as(Iterable.class);
//        if (type != ResolvableType.NONE &&
//                ClientRegistration.class.isAssignableFrom(type.resolveGenerics()[0])) {
//            clientRegistrations = (Iterable<ClientRegistration>) clientRegistrationRepository;
//        }
//        assert clientRegistrations != null;
//        clientRegistrations.forEach(registration ->
//                oauth2AuthenticationUrls.put(registration.getClientName(),
//                        authorizationRequestBaseUrl + "/" + registration.getRegistrationId()));
//        model.addAttribute("urls", oauth2AuthenticationUrls);
//
//
//
//        return "login";
//    }

//    @GetMapping("/login/{oauth2}")
//    public String loginGoogle(@PathVariable String oauth2, HttpServletResponse httpServletResponse) {
//
//        return "redirect:/oauth2/authorization/" + oauth2;
//    }
//
//    @RequestMapping("/accessDenied")
//    public String accessDenied() {
//        return "accessDenied";
//    }
}
