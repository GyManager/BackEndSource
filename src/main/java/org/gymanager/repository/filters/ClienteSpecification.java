package org.gymanager.repository.filters;

import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;
import org.gymanager.model.domain.clientes.Cliente;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

@Getter
@Setter
public class ClienteSpecification implements Specification<Cliente> {

    private static final Pattern NUMBER_PATTERN = Pattern.compile("\\d+");
    private static final String PSEUDO_EMAIL_PATTERN = "@";

    private static final String LIKE_WILDCARD = "%";

    private static final String CAMPO_NUMERO_DOCUMENTO = "numeroDocumento";
    private static final String TABLA_USUARIO = "usuario";
    private static final String CAMPO_MAIL = "mail";
    private static final String CAMPO_NOMBRE = "nombre";
    private static final String CAMPO_APELLIDO = "apellido";

    private String fuzzySearch;

    @Override
    public Predicate toPredicate(Root<Cliente> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
        List<Predicate> predicateList = new ArrayList<>();

        if(!StringUtils.isEmpty(fuzzySearch)){
            List<Predicate> fuzzySearchPredicateList = new ArrayList<>();

            if(NUMBER_PATTERN.matcher(fuzzySearch).matches()){
                fuzzySearchPredicateList.add(builder.like(root.get(CAMPO_NUMERO_DOCUMENTO).as(String.class),
                        rodearConLikeWildcard(fuzzySearch)));
            }
            else if (fuzzySearch.contains(PSEUDO_EMAIL_PATTERN)){
                fuzzySearchPredicateList.add(builder.like(
                        builder.lower(root.join(TABLA_USUARIO).get(CAMPO_MAIL)),
                        rodearConLikeWildcard(fuzzySearch.toLowerCase())));
            }
            else {
                fuzzySearchPredicateList.add(builder.like(builder.lower(root.get(CAMPO_NOMBRE)),
                        rodearConLikeWildcard(fuzzySearch.toLowerCase())));
                fuzzySearchPredicateList.add(builder.like(builder.lower(root.get(CAMPO_APELLIDO)),
                        rodearConLikeWildcard(fuzzySearch.toLowerCase())));
            }

            predicateList.add(fuzzySearchPredicateList.stream().reduce(builder::or).get());
        }

        return predicateList.stream().reduce(builder::and)
                .orElse(builder.isTrue(builder.literal(true)));
    }

    private String rodearConLikeWildcard(String valor){
        return LIKE_WILDCARD + valor + LIKE_WILDCARD;
    }
}
