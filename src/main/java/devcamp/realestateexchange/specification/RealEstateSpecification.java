package devcamp.realestateexchange.specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import devcamp.realestateexchange.entity.realestate.RealEstate;

public class RealEstateSpecification implements Specification<RealEstate> {

    private SearchCriteria criteria;

    public RealEstateSpecification(SearchCriteria criteria) {
        this.criteria = criteria;
    }

    @Override
    public Predicate toPredicate(Root<RealEstate> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
        if (criteria.getOperation().equalsIgnoreCase(">")) {
            return builder.greaterThanOrEqualTo(root.<String>get(criteria.getKey()), criteria.getValue().toString());
        } else if (criteria.getOperation().equalsIgnoreCase("<")) {
            return builder.lessThanOrEqualTo(root.<String>get(criteria.getKey()), criteria.getValue().toString());
        } else if (criteria.getOperation().equalsIgnoreCase(":")) {
            if (criteria.getKey().contains(".")) {
                String[] keys = criteria.getKey().split("\\.");
                Join<RealEstate, ?> join = root.join(keys[0]);
                return builder.equal(join.get(keys[1]), criteria.getValue());
            } else {
                if (root.get(criteria.getKey()).getJavaType() == String.class) {
                    return builder.like(root.<String>get(criteria.getKey()), "%" + criteria.getValue() + "%");
                } else {
                    return builder.equal(root.get(criteria.getKey()), criteria.getValue());
                }
            }
        }
        return null;
    }
}
