package jpabook.jpashop.repository.simplequery;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@RequiredArgsConstructor
@Repository
public class OrderSimpleQueryRepository {
    private final EntityManager entityManager;

    public List<FindSimpleOrderQueryDto> findOrderQueryDtos() {
        return entityManager.createQuery(
                        "select new jpabook.jpashop.repository.simplequery.FindSimpleOrderQueryDto(o.id, m.name, o.orderDate, o.status, d.address)" +
                                " from Order o" +
                                " join o.member m" +
                                " join o.delivery d", FindSimpleOrderQueryDto.class)
                .getResultList();
    }
}
