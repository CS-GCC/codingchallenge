package codingchallenge.services.impl;

import codingchallenge.collections.ContestantRepository;
import codingchallenge.collections.TravisRepository;
import codingchallenge.domain.Contestant;
import codingchallenge.domain.TravisUUID;
import codingchallenge.exceptions.ContestantNotFoundException;
import codingchallenge.services.interfaces.ContestantService;
import codingchallenge.services.interfaces.InitialisationService;
import com.google.common.collect.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ContestantServiceImpl implements ContestantService {

    private final ContestantRepository contestantRepository;
    private final TravisRepository travisRepository;
    private final InitialisationService initialisationService;

    private final Logger logger =
            LoggerFactory.getLogger(ContestantServiceImpl.class);

    @Autowired
    public ContestantServiceImpl(ContestantRepository contestantRepository,
                                 TravisRepository travisRepository,
                                 InitialisationService initialisationService) {
        this.contestantRepository = contestantRepository;
        this.travisRepository = travisRepository;
        this.initialisationService = initialisationService;
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

    @Override
    public Contestant addContestant(Contestant contestant, boolean addToTeam,
                                    String travisUUID) {
        contestant = contestantRepository.insert(contestant);
        travisRepository.insert(new TravisUUID(contestant.getId(),
                UUID.fromString(travisUUID)));
        initialisationService.completeInitialisation(contestant,
                travisUUID);
        return contestant;
    }

}
