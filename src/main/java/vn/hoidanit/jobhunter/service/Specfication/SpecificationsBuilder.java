package vn.hoidanit.jobhunter.service.Specfication;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
public final class SpecificationsBuilder<T> {
    public Specification<T> whereAttributeContains(String attributeName, String value) {
        return (Root<T> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> {
            return cb.like(root.get(attributeName), "%" + value + "%");
        };
    }
}