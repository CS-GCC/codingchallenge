package codingchallenge.services.impl;

import codingchallenge.collections.FileRepository;
import codingchallenge.domain.FileData;
import codingchallenge.domain.GitFile;
import codingchallenge.domain.subdomain.Language;
import codingchallenge.services.interfaces.FileService;
import com.google.common.collect.Lists;
import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Arrays;
import java.util.List;

import static org.apache.tomcat.util.codec.binary.Base64.decodeBase64;
import static org.apache.tomcat.util.codec.binary.Base64.encodeBase64String;

@Service
public class FileServiceImpl implements FileService {

    String[] java = {"pom.xml", ".DS_Store", "src/main/java/skeleton/Main" +
            ".java", "src/main/java/skeleton/answers/Question1.java", "src/main/java/skeleton/answers/Question2.java", "src/main/java/skeleton/answers/Question3.java", "src/main/java/skeleton/answers/Question4.java", "src/main/java/skeleton/answers/Question5.java", "src/main/java/skeleton/answers/Question6.java", "src/test/java/Answer.java", "src/test/java/Q1.java", "src/test/java/Q2.java", "src/test/java/Q3.java", "src/test/java/Q4.java", "src/test/java/Q5.java", "src/test/java/Q6.java", "src/test/java/TestCase.java", "src/test/java/TestCaseLint.java", "src/test/java/TestRunner.java"};

    String[] csharp = {"appsettings.json", "appsettings.Development.json",
            "Startup.cs", "Program.cs", "C_Sharp_Challenge_Skeleton.sln", "C_Sharp_Challenge_Skeleton.csproj", ".DS_Store", "Properties/launchSettings.json", "C_Sharp_Challenge_Tests/C_Sharp_Challenge_Tests.csproj", "C_Sharp_Challenge_Tests/SkeletonTests.cs", "Answers/Question1.cs", "Answers/Question2.cs", "Answers/Question3.cs", "Answers/Question4.cs", "Answers/Question5.cs", "Answers/Question6.cs"};

    String[] python = {"Pipfile", "Question1.py", "Question2.py", "Question3.py", "Question4.py", "Question5.py", "Question6.py", "vcc-skeleton.py"};

    String[] javascript = {"spec.js", "package.json", "questions/Question1.js", "questions/Question2.js", "questions/Question3.js", "questions/Question4.js", "questions/Question5.js", "questions/Question6.js"};

    private final FileRepository fileRepository;

    public FileServiceImpl(FileRepository fileRepository) {
        this.fileRepository = fileRepository;
    }


    @Override
    public List<FileData> getFolderContent(Language language) {
        List<FileData> fileData = Lists.newArrayList();
//        String baseUrl =
//                "https://raw.githubusercontent.com/kunalwagle/" + getRepoName(language) + "/master/";
//        String[] names = getObjectForLanguage(language);
        List<GitFile> files = fileRepository.findAllByLanguage(language);
        for (GitFile file : files) {
            try {
                if (file.getData() != null) {
                    byte[] data = decodeBase64(file.getData());
//                GitFile file = new GitFile(language, name, dataAsString);
//                fileRepository.insert(file);
                    fileData.add(new FileData(file.getName(), file.getName(), data));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
//        return null;
        return fileData;
    }

    @Override
    public List<FileData> getPatchContent(Language language) throws IOException {
        String[] filesToPatch = getObjectForLanguage(language);
        List<GitFile> files =
                fileRepository.findAllByLanguageAndNameIn(language,
                        Arrays.asList(filesToPatch));
        List<FileData> fileData = Lists.newArrayList();
        for (GitFile file : files) {
            try {
                if (file.getData() != null) {
                    byte[] data = decodeBase64(file.getData());
//                GitFile file = new GitFile(language, name, dataAsString);
//                fileRepository.insert(file);
                    fileData.add(new FileData(file.getName(), file.getName(), data));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
//        return null;
        return fileData;
    }

    private byte[] fetchRemoteFile(String location) throws Exception {
        URL url = new URL(location);
        InputStream is = null;
        byte[] bytes = null;
        try {
            is = url.openStream ();
            bytes = IOUtils.toByteArray(is);
        } catch (IOException e) {
            //handle errors
        }
        finally {
            if (is != null) is.close();
        }
        return bytes;
    }

    private String getRepoName(Language language) {
        switch (language) {
            case JAVA:
                return "2019-Java-Skeleton";
            case CSHARP:
                return "CSharp-Skeleton-2019";
            case PYTHON:
                return "Python-Skeleton-2019";
            case JAVASCRIPT:
                return "javascript-skeleton-2019";
            default:
                return null;
        }
    }

    private String[] getObjectForLanguage(Language language) {
        switch (language) {
            case JAVA:
                return java;
            case CSHARP:
                return csharp;
            case PYTHON:
                return python;
            case JAVASCRIPT:
                return javascript;
            default:
                return null;
        }
    }



}
