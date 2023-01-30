package com.avocat.security.filter;

import com.avocat.exceptions.IDORException;
import com.avocat.exceptions.InvalidPermissionOrRoleException;
import com.avocat.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.UUID;

/*
 * Filter utilizado para prevenir ataques de IDOR.
 * Quando algum usuário tentar acessar um recurso que não
 * esta autorizado a acessar.
 */
@Component
public class PreventAttackIdorFilter implements Filter {

    Logger logger = LoggerFactory.getLogger(PreventAttackIdorFilter.class);

    @Autowired
    private UserService userService;

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) servletRequest;
        String username = request.getUserPrincipal().getName();
        String uri = request.getRequestURI();

        UUID uriBranchOfficeId = null;

        if(uri.contains("v1/branch-office/")) {
            uriBranchOfficeId = UUID.fromString(uri.split("/")[3]);
        }

        var userLogged = userService.findByUsernameAndBranchOfficeId(username, uriBranchOfficeId);

        if(userLogged.isPresent()) {
            filterChain.doFilter(servletRequest, servletResponse);
        } else {
            logger.warn("IDOR attack attempt with user: " + username);
        }
    }
}
