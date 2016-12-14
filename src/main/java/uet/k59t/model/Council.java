package uet.k59t.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Longlaptop on 12/14/2016.
 */
@Entity
public class Council {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String councilname;
    @OneToMany
    @JoinTable(name = "council_has_teacher",
            joinColumns = {
                    @JoinColumn(name = "council_id", referencedColumnName = "id")
            },
            inverseJoinColumns = {
                    @JoinColumn(name = "teacher_id", referencedColumnName = "id")
            }
    )
    private List<Teacher> teachers =new ArrayList<Teacher>();




    public List<Teacher> getTeachers() {
        return teachers;
    }

    public void setTeachers(List<Teacher> teachers) {
        this.teachers = teachers;
    }





    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCouncilname() {
        return councilname;
    }

    public void setCouncilname(String councilname) {
        this.councilname = councilname;
    }
}
