package abidien.autopost.services;

import abidien.autopost.models.FakeLinkEntity;
import abidien.autopost.models.FakeLinkResponse;
import abidien.autopost.models.ReportEntity;
import abidien.autopost.models.ReportId;
import abidien.chuongga.Environment;
import abidien.services.HibernateUtil;
import abidien.services.IDataService;
import abidien.services.InmemoryDataService;
import javafx.util.Pair;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by ABIDIEN on 14/01/2017.
 */
public class ReportService /*extends InmemoryDataService<Pair<Integer, Integer>, ReportEntity>*/ {
    final IDataService<ReportId, ReportEntity> db;
    final ArrayList<ReportEntity> reportData = new ArrayList<>();
    //final HashMap<Integer, >

    public ReportService(Environment env) {
        db = env.getReportDataDriver();
    }


    public void updateReport(ReportEntity report) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        try{
            transaction = session.beginTransaction();
            String hqlUpdate = "INSERT INTO " + db.getModelClass().getName() + " (fakeLinkId, domainId, time, ownerId, click) VALUES (:fakeLinkId, :domainId, :time, :ownerId, :click) ON DUPLICATE KEY UPDATE click = click + :click";
            int updatedEntities = session.createQuery(hqlUpdate)
                    .setParameter("fakeLinkId", report.getId().getFakeLinkId())
                    .setParameter("domainId", report.getId().getDomainId())
                    .setParameter("time", report.getId().getTime())
                    .setParameter("ownerId", Environment.getFakeLinkService().load(report.getId().getFakeLinkId()).getOwnerId())
                    .setParameter("click", report.getClick())
                    .executeUpdate();
            transaction.commit();
        } catch (HibernateException ex) {
            //Log the exception
            transaction.rollback();
            System.err.println(ex);
        } finally {
            session.close();
        }
        /*ReportEntity r = db.load(report.getId());
        if (r == null) {
            db.saveOrUpdate(report);
        } else {
            r.setClick(r.getClick() + report.getClick());
            db.saveOrUpdate(r);
        }*/
    }

    public List<FakeLinkResponse> getReport(List<FakeLinkEntity> list) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        String hql = String.format("from %s where fakeLinkId in () group by time, fakeLinkId", db.getModelClass().getName());
        Query query = session.createQuery(hql);
        List<ReportEntity> ds = query.list();
        session.close();
        /*return list.stream().map(f -> {
            int click = loadAll().stream().filter(r -> r.getFakeLinkId().equals(f.getId())).mapToInt(o -> o.getClick()).sum();
            return new FakeLinkResponse(f, click);
        }).collect(Collectors.toList());*/
        return null;
    }
}
