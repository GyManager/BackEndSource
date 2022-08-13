package org.gymanager.repository.filters;

import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;
import org.gymanager.model.domain.Cliente;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Getter
@Setter
public class ClienteSpecification implements Specification<Cliente> {

    private static final Pattern NUMBER_PATTERN = Pattern.compile("\\d+");
    private static final Pattern NOMBRE_APELLIDO_PATTERN = Pattern.compile("([A-Za-z])+");

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

            var parameters = Arrays.stream(fuzzySearch.split(" "))
                    .filter(member -> !member.isBlank())
                    .map(String::trim)
                    .collect(Collectors.toList());

            var joinUsuario = root.join(TABLA_USUARIO);

            for (String member: parameters) {

                List<Predicate> fuzzySearchPredicateList = new ArrayList<>();

                if(NOMBRE_APELLIDO_PATTERN.matcher(member).matches()){
                    fuzzySearchPredicateList.add(builder.like(
                            builder.lower(joinUsuario.get(CAMPO_NOMBRE)),
                            rodearConLikeWildcard(member.toLowerCase())));
                    fuzzySearchPredicateList.add(builder.like(
                            builder.lower(joinUsuario.get(CAMPO_APELLIDO)),
                            rodearConLikeWildcard(member.toLowerCase())));
                    fuzzySearchPredicateList.add(builder.like(
                            builder.lower(joinUsuario.get(CAMPO_MAIL)),
                            rodearConLikeWildcard(member.toLowerCase())));
                }
                else if(NUMBER_PATTERN.matcher(member).matches()){
                    fuzzySearchPredicateList.add(builder.like(
                            joinUsuario.get(CAMPO_NUMERO_DOCUMENTO).as(String.class),
                            rodearConLikeWildcard(member)));
                    fuzzySearchPredicateList.add(builder.like(
                            builder.lower(joinUsuario.get(CAMPO_MAIL)),
                            rodearConLikeWildcard(member.toLowerCase())));
                }
                else {
                    fuzzySearchPredicateList.add(builder.like(
                            builder.lower(joinUsuario.get(CAMPO_MAIL)),
                            rodearConLikeWildcard(member.toLowerCase())));
                }

                predicateList.add(fuzzySearchPredicateList.stream().reduce(builder::or).get());
            }
        }

        return predicateList.stream().reduce(builder::and)
                .orElse(builder.isTrue(builder.literal(true)));
    }

    private String rodearConLikeWildcard(String valor){
        return LIKE_WILDCARD + valor + LIKE_WILDCARD;
    }
}
