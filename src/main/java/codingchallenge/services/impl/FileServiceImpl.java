package codingchallenge.services.impl;

import codingchallenge.domain.FileData;
import codingchallenge.domain.subdomain.Language;
import codingchallenge.services.interfaces.FileService;
import com.google.common.collect.Lists;
import org.apache.commons.io.IOUtils;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.*;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class FileServiceImpl implements FileService {

    @Override
    public List<FileData> getFolderContent(Language language) {
        String resourceDirectory = "/" + language.toString().toLowerCase();
        URI uri = null;
        Path path = null;
        try {
            uri = getClass().getResource(resourceDirectory).toURI();

//            if (uri.getScheme().equals("jar")) {
//                FileSystem fileSystem = FileSystems.newFileSystem(uri,
//                        Collections.emptyMap());
//                path = fileSystem.getPath("/BOOT-INF/classes" + resourceDirectory);
//            } else {
                // Not running in a jar, so just use a regular filesystem path
                path = Paths.get(uri);
//            }
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        try (
                Stream<Path> paths =
                        Files.walk(Paths.get(path.toUri()))) {
            List<FileData> results = paths
                    .filter(Files::isRegularFile)
                    .map(p -> getFileData(p, language, false))
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList());
            Stream<Path> questionPaths =
                    Files.walk(Paths.get(Objects.requireNonNull(getClass().getClassLoader().getResource(
                            "questions")).toURI()));
            results.addAll(questionPaths.map(p -> getFileData(p, language,
                    true)).filter(Objects::nonNull).collect(Collectors.toList()));
            return results;
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }
        return Lists.newArrayList();
    }

    private FileData getFileData(Path path, Language language,
                                 boolean questions) {
        try {
            if (!questions) {
                return new FileData(path.getFileName().toString(),
                        getPath(path.toString(), language),
                        IOUtils.toByteArray(path.toUri()));
            }
            return new FileData(path.getFileName().toString(),
                    "questions/" + path.getFileName().toString(),
                    IOUtils.toByteArray(path.toUri()));

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private String getPath(String fullPath, Language language) {
        return fullPath.substring(fullPath.indexOf(language.toString().toLowerCase()) + language.toString().length() + 1);
    }

}
