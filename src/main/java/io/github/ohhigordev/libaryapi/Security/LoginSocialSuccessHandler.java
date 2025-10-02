package io.github.ohhigordev.libaryapi.Security;

import io.github.ohhigordev.libaryapi.model.Usuario;
import io.github.ohhigordev.libaryapi.service.UsuarioService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

@Component
@RequiredArgsConstructor
public class LoginSocialSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    private final static String SENHA_PADRAO = "321";

    private final UsuarioService usuarioService;

    //Esse metodo é chamado quando o usuário faz uma autenticação em sucedida
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws ServletException, IOException {

        OAuth2AuthenticationToken oAuth2AuthenticationToken = (OAuth2AuthenticationToken) authentication;
        OAuth2User oAuth2User = oAuth2AuthenticationToken.getPrincipal();

        String email = oAuth2User.getAttribute("email");

        Usuario usuario = usuarioService.obterPorEmail(email);

        if(usuario == null){
            usuario = cadastarUsuarioNaBase(email);
        }


        authentication = new CustomAuthentication(usuario);

        SecurityContextHolder.getContext().setAuthentication(authentication);

        super.onAuthenticationSuccess(request, response, authentication);
    }

    private Usuario cadastarUsuarioNaBase(String email) {
        Usuario usuario;
        usuario = new Usuario();
        usuario.setEmail(email);
        
        usuario.setLogin(obterLoginApartirDoEmail(email));
        
        usuario.setSenha(SENHA_PADRAO);
        usuario.setRoles(List.of("OPERADOR"));

        usuarioService.salvar(usuario);
        return usuario;
    }

    private String obterLoginApartirDoEmail(String email) {
        return email.substring(0, email.indexOf("@"));
    }
}
