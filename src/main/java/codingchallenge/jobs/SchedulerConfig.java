//package codingchallenge.jobs;
//
//import codingchallenge.api.interfaces.Git;
//import codingchallenge.api.interfaces.Travis;
//import codingchallenge.collections.ContestantRepository;
//import codingchallenge.collections.GitRepository;
//import codingchallenge.collections.TravisRepository;
//import codingchallenge.domain.Contestant;
//import codingchallenge.domain.GitRepo;
//import codingchallenge.domain.TravisUUID;
//import codingchallenge.services.ServiceProperties;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.scheduling.annotation.SchedulingConfigurer;
//import org.springframework.scheduling.config.ScheduledTaskRegistrar;
//
//import java.io.IOException;
//import java.text.ParseException;
//import java.text.SimpleDateFormat;
//import java.util.Date;
//import java.util.List;
//import java.util.Optional;
//
//@Configuration
//public class SchedulerConfig implements SchedulingConfigurer {
//
//    private ServiceProperties serviceProperties;
//    private ContestantRepository contestantRepository;
//    private TravisRepository travisRepository;
//    private GitRepository gitRepository;
//    private Git git;
//    private Travis travis;
//    private Logger logger = LoggerFactory.getLogger(SchedulerConfig.class);
//
//    @Autowired
//    public SchedulerConfig(ServiceProperties serviceProperties,
//                           ContestantRepository contestantRepository, TravisRepository travisRepository, GitRepository gitRepository, Git git, Travis travis) {
//        this.serviceProperties = serviceProperties;
//        this.contestantRepository = contestantRepository;
//        this.travisRepository = travisRepository;
//        this.gitRepository = gitRepository;
//        this.git = git;
//        this.travis = travis;
//    }
//
//    @Override
//    public void configureTasks(ScheduledTaskRegistrar scheduledTaskRegistrar) {
//        scheduledTaskRegistrar.addTriggerTask(
//                this::collaboratorJob,
//                triggerContext -> {
//                    String start = serviceProperties.getStartDate();
//                    try {
//                        Date date =
//                                new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").parse(start);
//                        if (new Date().before(date)) {
//                            logger.info("Will run job at " + date.toString());
//                            return date;
//                        }
//                        return null;
//                    } catch (ParseException e) {
//                        return null;
//                    }
//                }
//        );
//    }
//
//    private void collaboratorJob() {
//        List<Contestant> contestants =
//                contestantRepository.findAll();
//        for (Contestant contestant : contestants) {
//            Optional<TravisUUID> travisUUIDOptional =
//                    travisRepository.findByContestantId(contestant.getId());
//            if (travisUUIDOptional.isPresent()) {
//                try {
//                    String username = contestant.getGitUsername();
//                    List<GitRepo> gitRepos =
//                            gitRepository.findGitRepoByUsername(username);
//                    for (GitRepo repo : gitRepos) {
//                        try {
//                            git.addCollaborator(repo, username);
//                        } catch (IOException e) {
//                            e.printStackTrace();
//                        }
//                        travis.activateTravis(repo.getRepoName());
//                        travis.setEnvVariable(repo.getRepoName(),
//                                travisUUIDOptional.get().getUuid().toString());
//                    }
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//    }
//
//}
