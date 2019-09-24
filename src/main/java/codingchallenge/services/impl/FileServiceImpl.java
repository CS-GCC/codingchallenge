package codingchallenge.services.impl;

import codingchallenge.domain.FileData;
import codingchallenge.domain.subdomain.Language;
import codingchallenge.services.interfaces.FileService;
import com.google.common.collect.Lists;
import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class FileServiceImpl implements FileService {

    @Override
    public List<FileData> getFolderContent(Language language) {
        try (
            Stream<Path> paths =
                    Files.walk(Paths.get(Objects.requireNonNull(getClass().getClassLoader().getResource(
                            language.toString().toLowerCase())).toURI()))) {
            return paths
                    .filter(Files::isRegularFile)
                    .map(this::getFileData)
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList());
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }
        return Lists.newArrayList();
    }

    private FileData getFileData(Path path) {
        try {
            return new FileData(path.getFileName().toString(),
                    IOUtils.toByteArray(path.toUri()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
