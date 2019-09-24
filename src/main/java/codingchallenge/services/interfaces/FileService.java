package codingchallenge.services.interfaces;

import codingchallenge.domain.FileData;
import codingchallenge.domain.subdomain.Language;

import java.io.IOException;
import java.util.List;

public interface FileService {

    List<FileData> getFolderContent(Language language) throws IOException;

}
