package codingchallenge.api.implementations;

import codingchallenge.api.interfaces.Travis;
import codingchallenge.services.ServiceProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;

@Service
public class TravisImpl implements Travis {

    private final String username = "kunalwagle";
    private final String token;
    private final ServiceProperties serviceProperties;
    private final String travis = "https://api.travis-ci.com/";
    private final RestTemplate restTemplate;

    @Autowired
    public TravisImpl(ServiceProperties serviceProperties, RestTemplate restTemplate) {
        this.serviceProperties = serviceProperties;
        this.restTemplate = restTemplate;
        this.token = "token " + serviceProperties.getTravisApiKey();
    }

    @Override
    public void activateTravis(String repo) {
        try {
            String url = travis + "repo/" + username + "%2F" + repo +
                    "/activate";
            travisRequest(url, createActivationEntity());
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void deactivateTravis(String repo) {
        try {
            String url = travis + "repo/" + username + "%2F" + repo +
                    "/deactivate";
            travisRequest(url, createActivationEntity());
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void setEnvVariable(String repo, String uuid) {
        try {
            String url = travis + "repo/" + username + "%2F" + repo +
                    "/env_vars";
            MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
            map.add("env_var.name", "travis_uuid");
            map.add("env_var.value", uuid);
            map.add("env_var.public", "false");
            HttpEntity<MultiValueMap<String, String>> entity =
                    new HttpEntity<>(map, getHttpHeaders());
            travisRequest(url, entity);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    private void travisRequest(String url,
                               HttpEntity<?> entity) throws URISyntaxException {
        restTemplate.exchange(new URI(url),
                HttpMethod.POST,
                entity,
                String.class);
        System.out.println(entity.getBody());
    }

    private HttpEntity<String> createActivationEntity() {
        HttpHeaders headers = getHttpHeaders();
        return new HttpEntity<>(headers);
    }

    private HttpHeaders getHttpHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", token);
        headers.add("Travis-API-Version", "3");
        return headers;
    }
}
