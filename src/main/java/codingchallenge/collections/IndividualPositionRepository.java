package codingchallenge.collections;

import codingchallenge.domain.subdomain.IndividualPosition;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface IndividualPositionRepository extends MongoRepository<IndividualPosition, String> {
    List<IndividualPosition> findAllByLeaderboardIdAndPositionGreaterThanEqualAndPosLessThanOrderByPosAsc(String leaderboardId, int position, int position2);
    Optional<IndividualPosition> findByLeaderboardIdAndContestantId(String leaderboardId, String contestantId);
    List<IndividualPosition> findAllByLeaderboardId(String leaderboardId);
    List<IndividualPosition> findAllByLeaderboardIdAndTeamIdOrderByTotalDesc(String leaderboardId, String teamId);
    List<IndividualPosition> findAllByLeaderboardIdAndGlobalIdIsIn(String leaderboardId, Collection<String> globalId);
    List<IndividualPosition> findAllByContestantId(String contestantId);
    List<IndividualPosition> findAllByLeaderboardIdAndContestantIdIn(String leaderboardId, Collection<String> contestantId);

    void deleteAllByIdIn(List<String> individualIds);

    List<IndividualPosition> findAllByTimestampBefore(Date date);

    List<IndividualPosition> findAllByTeamId(String id);
}
