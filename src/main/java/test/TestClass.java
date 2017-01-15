package test;

import abidien.common.HttpUtils;
import abidien.common.SecurityUtils;
import abidien.handler.RestServlet;
import abidien.autopost.models.DomainEntity;
import javafx.util.Pair;
import org.apache.commons.lang.RandomStringUtils;
import org.junit.Assert;
import org.junit.Test;

import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by ABIDIEN on 29/11/2016.
 */
public class TestClass {

    @Test
    public void abc() {
        new RestServlet<Integer, DomainEntity>(null) {
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

    int[][] input = {
            {2,0,0,0,0},
            {0,0,0,0,0},
            {0,0,0,3,1}
    };
    int n = 3;
    int m = 5;
    boolean[][] isVisited = new boolean[n][m];
    Point startPoint = null;
    Point endPoint = null;
    int currentStep = 0;
    int maxStep = 0;
    int output = 0;
    @Test
    public void hamilton() {
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (input[i][j] == 2)
                    startPoint = new Point(i, j);
                else if (input[i][j] == 3)
                    endPoint = new Point(i, j);
                if (input[i][j] != 1)
                    maxStep++;
            }
        }
        isVisited[(int)startPoint.getX()][(int)startPoint.getY()] = true;
        currentStep++;
        stepOn(startPoint);
        System.out.println(output);
    }

    public void stepOn(Point point) {
        for (Point nearPoint: getNearPoints(point)) {
            int x = (int)nearPoint.getX();
            int y = (int)nearPoint.getY();
            if (input[x][y] != 1 && !isVisited[x][y]) { //Nếu điểm kế bên khác 1 và chưa được ghé thăm
                currentStep++; //Ghé thăm điểm kế bên và nâng số bước lên 1
                if (currentStep < maxStep) { //Nếu chưa bước qua hết tất cả các điểm
                    if (!nearPoint.equals(endPoint)) { //Nếu không phải điểm kết thúc
                        isVisited[(int) point.getX()][(int) point.getY()] = true; //Đánh dấu điểm kế bên đã ghé thăm
                        stepOn(nearPoint); // Thực hiện thăm điểm kế bên (Tiến đến điểm kế bên)
                        isVisited[(int) point.getX()][(int) point.getY()] = false; //Hủy đánh dấu điểm vừa thăm (Đi lùi)
                    }
                } else {
                    if (nearPoint.equals(endPoint)) { //Đã bước qua hết tất cả các điểm và điểm tiếp theo là điểm kết thúc
                        output++;
                    }
                }
                currentStep--; //Giảm số bước đi 1 (Đi lùi)
            }
        }
    }

    public ArrayList<Point> getNearPoints(Point point) {
        ArrayList<Point> nearPoints = new ArrayList<>();
        int[][] steps = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
        for (int i = 0; i < 4; i++) {
            Point newPoint = point.getLocation();
            newPoint.translate(steps[i][0], steps[i][1]);
            if (newPoint.getX() >= 0 && newPoint.getX() < n
                && newPoint.getY() >= 0 && newPoint.getY() < m) {
                nearPoints.add(newPoint);
            }
        }
        return nearPoints;
    }
}
