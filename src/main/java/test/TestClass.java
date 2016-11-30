package test;

import abidien.handler.RestServlet;
import abidien.models.DomainEntity;
import org.junit.Test;

/**
 * Created by ABIDIEN on 29/11/2016.
 */
public class TestClass {

    @Test
    public void abc() {
        new RestServlet<DomainEntity>(null) {
            @Override
            public DomainEntity factory() {
                return new DomainEntity(null, 0);
            }
        }.save(null, null);
    }
}
