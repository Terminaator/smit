package secret.data.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PersonDTO {
    private String realName;
    private String secretName;
    private String number;
    public PersonDTO(){
    }
}
