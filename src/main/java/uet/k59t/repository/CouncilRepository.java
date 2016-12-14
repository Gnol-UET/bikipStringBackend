package uet.k59t.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import uet.k59t.model.Council;

/**
 * Created by Longlaptop on 12/14/2016.
 */
@Repository
public interface CouncilRepository  extends CrudRepository<Council,Long>{
    Council findByCouncilname(String councilname);
}
