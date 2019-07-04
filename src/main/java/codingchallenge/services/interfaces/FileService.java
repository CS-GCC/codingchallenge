package codingchallenge.services.interfaces;

import codingchallenge.domain.subdomain.Language;

public interface FileService {

    byte[] getFolderContent(Language language);

}
