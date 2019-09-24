package codingchallenge.collections;

import codingchallenge.domain.Team;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface TeamRepository extends MongoRepository<Team, String> {
    List<Team> findByIdNotIn(Collection<String> ids);
    Optional<Team> findByName(String name);
}
