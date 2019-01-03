package codingchallenge.services.impl;

import codingchallenge.collections.ContestantRepository;
import codingchallenge.domain.Contestant;
import codingchallenge.services.interfaces.ContestantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    public Contestant getContestantById(String id) {
        Optional<Contestant> contestant = contestantRepository.findById(id);
        return contestant.orElse(null);
    }

}
