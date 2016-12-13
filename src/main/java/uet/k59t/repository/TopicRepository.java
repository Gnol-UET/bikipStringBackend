package uet.k59t.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import uet.k59t.model.Student;
import uet.k59t.model.Topic;

/**
 * Created by Longlaptop on 12/13/2016.
 */
@Repository
public interface TopicRepository extends CrudRepository<Topic, Long> {
    Topic findById(long id);
}
