package practice;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
//@Table(name = "Member")
public class Member {
    @Id // primary key
    private Long id;

    //@Column(name = "name")
    private String name;

    public Member() {
    }

    public Member(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
