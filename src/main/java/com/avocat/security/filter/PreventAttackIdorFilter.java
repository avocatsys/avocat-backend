package com.avocat.security.filter;

import com.avocat.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Set;
import java.util.UUID;

/*
 * Filter utilizado para prevenir ataques de IDOR.
 * Quando algum usuário tentar acessar um recurso que não
 * esta autorizado a acessar.
 */
@Component
public class PreventAttackIdorFilter implements Filter {

    private Logger logger = LoggerFactory.getLogger(PreventAttackIdorFilter.class);

    private Set<String> whiteList = Set.of("/v1/account", "/v1/authentication", "/v1/customers/signup");

    @Autowired
    private UserService userService;

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) servletRequest;
        String uri = request.getRequestURI().replace("/avocat", "");

        if(!whiteList.stream().anyMatch(i -> uri.contains(i))){

            String username = request.getUserPrincipal().getName();
            UUID uriBranchOfficeId = null;

            if (uri.contains("v1/branch-office/")) {
                uriBranchOfficeId = UUID.fromString(uri.split("/")[2]);
            }

            var userLogged = userService.findByUsernameAndBranchOfficeId(username, uriBranchOfficeId);

            if (userLogged.isPresent()) {
                logger.warn("IDOR attack attempt with user: " + username);//todo pensar em uma exception personalizada
            }
        }

        filterChain.doFilter(servletRequest, servletResponse);
    }
}
