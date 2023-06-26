package jpabook.jpashop.repository.query;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@RequiredArgsConstructor
@Repository
public class OrderQueryRepository {
    private final EntityManager entityManager;

    public List<FindOrderQueryDto> findOrderQueryDtos() {
        return entityManager.createQuery(
                        "select new jpabook.jpashop.repository.query.FindOrderQueryDto(o.id, m.name, o.orderDate, o.status, d.address)" +
                                " from Order o" +
                                " join o.member m" +
                                " join o.delivery d", FindOrderQueryDto.class)
                .getResultList();
    }
}
