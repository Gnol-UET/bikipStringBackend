package uet.k59t.repository;

import org.springframework.data.repository.CrudRepository;
import uet.k59t.model.Moderator;

/**
 * Created by Long on 12/10/2016.
 */
public interface ModeratorRepository extends CrudRepository<Moderator, Long> {
    Moderator findByUsername(String name);

    Moderator findByToken(String token);
}
