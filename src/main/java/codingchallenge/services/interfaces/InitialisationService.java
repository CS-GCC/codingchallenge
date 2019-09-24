package codingchallenge.services.interfaces;

import codingchallenge.domain.Contestant;

public interface InitialisationService {

    void completeInitialisation(Contestant contestant, String code);

}
