package test;

import abidien.common.HttpUtils;
import abidien.common.SecurityUtils;
import abidien.handler.RestServlet;
import abidien.autopost.models.DomainEntity;
import org.apache.commons.lang.RandomStringUtils;
import org.junit.Assert;
import org.junit.Test;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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

    @Test
    public void encode_decode() {
        for (int i = 193 * 10 * 1000 * 1000; i <= Integer.MAX_VALUE; i++) {
            String encode = SecurityUtils.encode(i);
            if (SecurityUtils.decode(encode) != i) {
                System.out.println(i);
                return;
            }
        }
    }

    @Test
    public void random_string() {
        System.out.println(RandomStringUtils.randomAlphanumeric(5));
    }

    @Test
    public void getContent() {
        //System.out.println(HttpUtils.execute("http://mychannel71.com/kisah-inspiratif-khoirul-anwar-tukang-ngarit-penemu-teknologi-4g-lte"));
        String x = "1234</head>1214";
        int index = x.indexOf("</head>");
        System.out.println(x.substring(index + 7));

        List names = new ArrayList();
        names.forEach(System.out::println);

    }

    @Test
    public void testLambdaException() {
        Map<String, String> a = new HashMap();
        List<String> list = new ArrayList<String>(a.values());
        Stream<String> stream = list.stream();
        Stream<String> stringStream = stream.filter(s -> s != null);
        List<String> collect = stringStream.collect(Collectors.toList());
        collect.sort((x1, x2) -> x1.length() - x2.length());
        System.out.println(collect);
    }

    @Test
    public void shuffleArray() {
        List<Integer> solution = new ArrayList<>();
        for (int i = 1; i <= 6; i++)
        {
            solution.add(i);
        }
        Collections.shuffle(solution);
        solution.forEach(System.out::println);
    }
}
