package uet.k59t.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import uet.k59t.model.Field;

/**
 * Created by Long on 12/10/2016.
 */
@Repository
public interface FieldRepository extends CrudRepository<Field, Long>{

}
