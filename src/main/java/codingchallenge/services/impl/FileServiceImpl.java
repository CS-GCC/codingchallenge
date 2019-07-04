package codingchallenge.services.impl;

import codingchallenge.domain.subdomain.Language;
import codingchallenge.services.interfaces.FileService;
import org.springframework.stereotype.Service;

@Service
public class FileServiceImpl implements FileService {

    @Override
    public byte[] getFolderContent(Language language) {
        return new byte[0];
    }

}
