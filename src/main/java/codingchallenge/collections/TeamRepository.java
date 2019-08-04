package codingchallenge.collections;

import codingchallenge.domain.Team;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Collection;
import java.util.List;

public interface TeamRepository extends MongoRepository<Team, String> {
    List<Team> findByIdNotIn(Collection<String> ids);
}
