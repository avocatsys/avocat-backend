package com.avocat.security.filter;

import com.avocat.service.UserService;
import io.jsonwebtoken.lang.Assert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Set;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
        String uri = request.getRequestURI();

        if(!whiteList.stream().anyMatch(i -> uri.contains(i))) {

            String username = request.getUserPrincipal().getName();

            String branchOfficeId = null;
            if (uri.contains("v1/branch-office/")) {
                branchOfficeId = extractBranchOfficeId(uri);
            }

            var userLogged = userService.findByUsernameAndBranchOfficeId(username, UUID.fromString(branchOfficeId));

            if (userLogged.isPresent()) {
                logger.warn("IDOR attack attempt with user: " + username);//todo pensar em uma exception personalizada
            }
        }

        filterChain.doFilter(servletRequest, servletResponse);
    }

    private static String extractBranchOfficeId(String uri) {
        String branchOfficeId;
        final String regex = "[a-f0-9]{8}-[a-f0-9]{4}-[a-f0-9]{4}-[a-f0-9]{4}-[a-f0-9]{12}";
        final String string = uri;

        final Pattern pattern = Pattern.compile(regex);
        final Matcher matcher = pattern.matcher(string);

        branchOfficeId = null;
        if (matcher.find()) {
            branchOfficeId = matcher.group(0);
        }
        Assert.notNull(branchOfficeId, "branchOfficeId not be null");
        return branchOfficeId;
    }
}
