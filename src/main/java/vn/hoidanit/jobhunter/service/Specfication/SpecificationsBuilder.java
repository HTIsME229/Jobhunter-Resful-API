package vn.hoidanit.jobhunter.service.Specfication;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public final class SpecificationsBuilder<T> {
    public Specification<T> whereAttributeContains(String attributeName, String value) {
        return (Root<T> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> {
            return cb.like(root.get(attributeName), "%" + value + "%");
        };

    }

    public Specification<T> whereAttributewhereAttributeObjectIn(String attributeName, List<T> value) {
        return (Root<T> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> {
            return root.join(attributeName).in(value);
        };

    }

    public Specification<T> whereAttributewhereAttributeIn(String attributeName, List<T> value) {
        return (Root<T> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> {
            return root.get(attributeName).in(value);
        };

    }

}