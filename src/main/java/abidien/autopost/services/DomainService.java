package abidien.autopost.services;

import abidien.autopost.models.DomainEntity;
import abidien.chuongga.Environment;
import abidien.services.IDataService;
import abidien.services.InmemoryDataService;

import java.util.Optional;

/**
 * Created by ABIDIEN on 14/01/2017.
 */
public class DomainService extends InmemoryDataService<Integer, DomainEntity> {
    public DomainService(Environment env) {
        super(env.getDomainDataDriver());
    }

    public int getIdByDomain(String domain) {
        final String validDomain = domain.startsWith("http://") ? domain.substring(7) : domain;
        DomainEntity first = loadAll().stream().filter(x -> x.getDomain().equals(validDomain)).findFirst().orElse(null);
        if (first == null)
            return -1;
        return first.getId();
    }
}
