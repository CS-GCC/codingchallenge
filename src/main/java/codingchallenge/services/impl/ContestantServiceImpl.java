package codingchallenge.services.impl;

import codingchallenge.collections.ContestantRepository;
import codingchallenge.domain.Contestant;
import codingchallenge.domain.Leaderboard;
import codingchallenge.domain.subdomain.IndividualPosition;
import codingchallenge.domain.subdomain.Position;
import codingchallenge.exceptions.ContestantNotFoundException;
import codingchallenge.services.interfaces.ContestantService;
import codingchallenge.services.interfaces.TeamService;
import com.google.common.collect.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ContestantServiceImpl implements ContestantService {

    private final ContestantRepository contestantRepository;

    private final Logger logger =
            LoggerFactory.getLogger(ContestantServiceImpl.class);

    @Autowired
    public ContestantServiceImpl(ContestantRepository contestantRepository) {
        this.contestantRepository = contestantRepository;
    }

    @Override
    public List<Contestant> addContestants(List<Contestant> contestants,
                                           boolean addToTeam) {
        contestants = contestantRepository.insert(contestants);
        return contestants;
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
    public List<Contestant> getContestantsByTeam(String id) {
        return contestantRepository.findContestantsByTeamId(id);
    }

    @Override
    public List<String> getContestantNames(List<String> contestantIds) {
        List<Contestant> contestants = getContestantsById(contestantIds);
        return contestants.stream().map(Contestant::getName).collect(Collectors.toList());
    }

    @Override
    public List<Contestant> getContestantsById(List<String> ids) {
        return Lists.newArrayList(contestantRepository.findAllById(ids));
    }

    @Override
    public long getNumberOfContestants() {
        return contestantRepository.count();
    }

    @Override
    public String getContestantIdForGlobalId(String id) {
        Optional<Contestant> contestantOptional =
                contestantRepository.findContestantByGlobalId(id);
        return contestantOptional.map(Contestant::getId).orElse(null);
    }

}
