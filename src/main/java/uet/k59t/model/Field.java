package uet.k59t.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Long on 12/10/2016.
 */
@Entity
public class Field {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String fieldName;
    @ManyToMany
    @JoinTable(name = "field_has_teacher",
        joinColumns = {
                @JoinColumn(name = "field_id", referencedColumnName = "id")
        },
        inverseJoinColumns = {
                @JoinColumn(name = "user_id", referencedColumnName = "id")
        }
    )
    private List<Teacher> users =new ArrayList<Teacher>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

}
