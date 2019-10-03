package codingchallenge.collections;

import codingchallenge.domain.GitFile;
import codingchallenge.domain.subdomain.Language;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Collection;
import java.util.List;

public interface FileRepository extends MongoRepository<GitFile, String> {

    List<GitFile> findAllByLanguage(Language language);
    List<GitFile> findAllByLanguageAndNameIn(Language language, Collection<String> name);

}
