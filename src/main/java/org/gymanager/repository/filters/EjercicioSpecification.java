package org.gymanager.repository.filters;

import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;
import org.gymanager.model.domain.Ejercicio;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class EjercicioSpecification implements Specification<Ejercicio> {

    private static final String LIKE_WILDCARD = "%";

    private static final String TABLA_TIPO_EJERCICIO = "tipoEjercicio";
    private static final String CAMPO_NOMBRE = "nombre";
    private static final String CAMPO_NOMBRE_TIPO_EJERCICIO = "nombre";

    private String nombreEjercicioOrTipoEjercicio;

    @Override
    public Predicate toPredicate(Root<Ejercicio> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
        List<Predicate> predicateList = new ArrayList<>();

        if(!StringUtils.isEmpty(nombreEjercicioOrTipoEjercicio)){

            List<Predicate> orPredicateList = new ArrayList<>();
            var joinTipoEjercicio = root.join(TABLA_TIPO_EJERCICIO);

            orPredicateList.add(builder.like(
                    builder.lower(joinTipoEjercicio.get(CAMPO_NOMBRE_TIPO_EJERCICIO)),
                    rodearConLikeWildcard(nombreEjercicioOrTipoEjercicio.toLowerCase())));

            orPredicateList.add(builder.like(
                    builder.lower(root.get(CAMPO_NOMBRE)),
                    rodearConLikeWildcard(nombreEjercicioOrTipoEjercicio.toLowerCase())));

            predicateList.add(orPredicateList.stream().reduce(builder::or).get());
        }

        return predicateList.stream().reduce(builder::and)
                .orElse(builder.isTrue(builder.literal(true)));
    }

    private String rodearConLikeWildcard(String valor){
        return LIKE_WILDCARD + valor + LIKE_WILDCARD;
    }
}
