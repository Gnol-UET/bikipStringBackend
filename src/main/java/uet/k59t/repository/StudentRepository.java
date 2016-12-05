package uet.k59t.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import uet.k59t.model.Student;

/**
 * Created by Longlaptop on 11/24/2016.
 */
@Repository
public interface StudentRepository extends CrudRepository<Student, Long>{
    Student findByUsername(String username);
}
