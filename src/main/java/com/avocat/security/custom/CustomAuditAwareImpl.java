package com.avocat.security.custom;

import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

/**
 * <p>
 *     Essa classe é utilizada para auditar as modificações realizadas nos registros. Assim essa classe captura
 *     o usuário logado (principal) e preenche as colunas createdBy e updatedBy nas tabelas do banco de dados.
 * </p>
 */
public class CustomAuditAwareImpl implements AuditorAware<String> {

    @Override
    public Optional<String> getCurrentAuditor() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            return null;
        }
        return Optional.of(authentication.getPrincipal().toString());
    }
}
