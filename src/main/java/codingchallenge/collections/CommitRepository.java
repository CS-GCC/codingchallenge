package codingchallenge.collections;

import codingchallenge.domain.Commit;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CommitRepository extends MongoRepository<Commit, String> {



}
