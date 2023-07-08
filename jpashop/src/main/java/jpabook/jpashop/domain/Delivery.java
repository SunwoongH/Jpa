package jpabook.jpashop.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;

import javax.persistence.*;

@Getter
@Entity
public class Delivery {
    @Id
    @GeneratedValue
    @Column(name = "delivery_id")
    private Long id;
    @JsonIgnore
    @OneToOne(mappedBy = "delivery", fetch = FetchType.LAZY)
    private Order order;
    @Embedded
    private Address address;
    @Enumerated(EnumType.STRING)
    private DeliveryStatus status;

    public void setOrder(Order order) {
        this.order = order;
    }

    public void setAddress(Address address) { this.address = address; }
}
