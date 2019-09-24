package codingchallenge.api.interfaces;

public interface Travis {

    void activateTravis(String repo);
    void deactivateTravis(String repo);
    void setEnvVariable(String repo, String uuid);

}
