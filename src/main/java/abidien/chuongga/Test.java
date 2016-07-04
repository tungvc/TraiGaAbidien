package abidien.chuongga;

import abidien.models.UserEntity;
import abidien.services.HibernateUtil;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ABIDIEN on 27/06/2016.
 */
public class Test {
    int id;
    String name;

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public Test(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public static List<UserEntity> getTests() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        String hql = "from UserEntity";
        Query query = session.createQuery(hql);
        List<UserEntity> rs = query.list();
        session.close();
//        List<Test> rs = new ArrayList<>();
//        for (int i = 1; i < 3; i++) {
//            rs.add(new Test(i, String.valueOf(i)));
//        }
        return rs;
    }
}
