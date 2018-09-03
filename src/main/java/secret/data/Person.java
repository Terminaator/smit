package secret.data;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Size;

@Getter
@Setter
@Entity
@Table(name = "PERSONS")
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Size(min = 1, max = 100)
    private String realName;
    @Column(unique=true)
    @Size(min = 1, max = 50)
    private String secretName;
    @Size(min = 1, max = 20)
    private String number;
    public Person(){

    }
}
