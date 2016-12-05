package uet.k59t.model;

import javax.persistence.*;

/**
 * Created by Longlaptop on 11/24/2016.
 */
@Entity
public class Faculty {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @Column(unique = true)
    private String facultyName;
    private String facultyAddr;

    public Faculty() {
    }

    public Faculty(String facultyName, String facultyAddr) {
        this.facultyName = facultyName;
        this.facultyAddr = facultyAddr;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFacultyName() {
        return facultyName;
    }

    public void setFacultyName(String facultyName) {
        this.facultyName = facultyName;
    }

    public String getFacultyAddr() {
        return facultyAddr;
    }

    public void setFacultyAddr(String facultyAddr) {
        this.facultyAddr = facultyAddr;
    }
}
