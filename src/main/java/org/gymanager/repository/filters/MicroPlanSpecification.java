package org.gymanager.repository.filters;

import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;
import org.gymanager.model.domain.MicroPlan;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class MicroPlanSpecification implements Specification<MicroPlan> {

    private static final String LIKE_WILDCARD = "%";

    private static final String CAMPO_NOMBRE = "nombre";

    private String search;

    @Override
    public Predicate toPredicate(Root<MicroPlan> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
        List<Predicate> predicateList = new ArrayList<>();

        if(!StringUtils.isEmpty(search)){
            predicateList.add(builder.like(builder.lower(root.get(CAMPO_NOMBRE)),
                    rodearConLikeWildcard(search.toLowerCase())));
        }

        return predicateList.stream().reduce(builder::and)
                .orElse(builder.isTrue(builder.literal(true)));
    }

    private String rodearConLikeWildcard(String valor){
        return LIKE_WILDCARD + valor + LIKE_WILDCARD;
    }
}
