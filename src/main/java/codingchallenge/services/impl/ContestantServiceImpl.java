package codingchallenge.services.impl;

import codingchallenge.collections.ContestantRepository;
import codingchallenge.domain.Contestant;
import codingchallenge.domain.Leaderboard;
import codingchallenge.domain.subdomain.IndividualPosition;
import codingchallenge.domain.subdomain.Position;
import codingchallenge.domain.subdomain.TimeStampPosition;
import codingchallenge.exceptions.ContestantNotFoundException;
import codingchallenge.services.interfaces.ContestantService;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ContestantServiceImpl implements ContestantService {

    private final ContestantRepository contestantRepository;

    @Autowired
    public ContestantServiceImpl(ContestantRepository contestantRepository) {
        this.contestantRepository = contestantRepository;
    }

    @Override
    public List<Contestant> addContestants(List<Contestant> contestants) {
        return contestantRepository.insert(contestants);
    }

    @Override
    public List<Contestant> getAllContestants() {
        return contestantRepository.findAll();
    }

    @Override
    public List<String> getAllContestantIds() {
        return contestantRepository.findAll().stream().map(Contestant::getId).collect(Collectors.toList());
    }

    @Override
    public Contestant getContestantById(String id) throws ContestantNotFoundException {
        Optional<Contestant> contestant = contestantRepository.findById(id);
        if (!contestant.isPresent()) {
            throw new ContestantNotFoundException(id);
        }
        return contestant.get();
    }

    @Override
    public void generateTimeStampedPositions(Leaderboard leaderboard) {
        List<Position> positions = leaderboard.getPositions();
        Date timestamp = leaderboard.getTimestamp();
        for (Position pos : positions) {
            IndividualPosition position = (IndividualPosition) pos;
            try {
                Contestant contestant =
                        getContestantById(position.getContestantId());
                List<TimeStampPosition> timeStampPositions =
                        contestant.getPositions();
                if (timeStampPositions == null) {
                    timeStampPositions = Lists.newArrayList();
                    contestant.setPositions(timeStampPositions);
                }
                timeStampPositions.add(new TimeStampPosition(position, timestamp));
                contestantRepository.save(contestant);
            } catch (ContestantNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public List<String> getContestantNames(List<String> contestantIds) {
        List<Contestant> contestants = Lists.newArrayList(
                contestantRepository.findAllById(contestantIds)
        );
        return contestants.stream().map(Contestant::getName).collect(Collectors.toList());
    }

}
