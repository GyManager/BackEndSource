package org.gymanager.repository.filters;

import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.gymanager.model.domain.MicroPlan;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
public class MicroPlanSpecification implements Specification<MicroPlan> {

    private static final String LIKE_WILDCARD = "%";

    private static final String CAMPO_NOMBRE = "nombre";
    private static final String CAMPO_ES_TEMPLATE = "esTemplate";
    private static final String CAMPO_CANTIDAD_RUTINAS = "cantidadRutinas";
    private static final String CAMPO_FECHA_BAJA = "fechaBaja";

    private String search;
    private Boolean esTemplate;
    private Integer cantidadRutinas;
    private Boolean excluirEliminados;

    @Override
    public Predicate toPredicate(Root<MicroPlan> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
        List<Predicate> predicateList = new ArrayList<>();

        if(!StringUtils.isEmpty(search)){
            predicateList.add(builder.like(builder.lower(root.get(CAMPO_NOMBRE)),
                    rodearConLikeWildcard(search.toLowerCase())));
        }

        if(Objects.nonNull(esTemplate)){
            predicateList.add(esTemplate ? builder.isTrue(root.get(CAMPO_ES_TEMPLATE)) :
                    builder.isFalse(root.get(CAMPO_ES_TEMPLATE)));
        }

        if(Objects.nonNull(cantidadRutinas)){
            predicateList.add(builder.equal(root.get(CAMPO_CANTIDAD_RUTINAS), cantidadRutinas));
        }

        if(BooleanUtils.isNotFalse(excluirEliminados)){
            predicateList.add(builder.isNull(root.get(CAMPO_FECHA_BAJA)));
        }

        return predicateList.stream().reduce(builder::and)
                .orElse(builder.isTrue(builder.literal(true)));
    }

    private String rodearConLikeWildcard(String valor){
        return LIKE_WILDCARD + valor + LIKE_WILDCARD;
    }

    public void setSearch(String search) {
        this.search = Objects.isNull(search) ? null : search.trim();
    }
}
