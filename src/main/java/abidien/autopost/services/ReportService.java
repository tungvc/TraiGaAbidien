package abidien.autopost.services;

import abidien.autopost.models.FakeLinkEntity;
import abidien.autopost.models.FakeLinkResponse;
import abidien.autopost.models.ReportEntity;
import abidien.autopost.models.ReportId;
import abidien.chuongga.Environment;
import abidien.services.HibernateUtil;
import abidien.services.IDataService;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
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
            String hqlUpdate = "INSERT INTO " + db.getModelClass().getSimpleName() + " (fakeLinkId, domainId, timeInInt, ownerId, click) VALUES (:fakeLinkId, :domainId, :timeInInt, :ownerId, :click) ON DUPLICATE KEY UPDATE click = click + :click";
            int updatedEntities = session.createNativeQuery(hqlUpdate)
                    .setParameter("fakeLinkId", report.getId().getFakeLinkId())
                    .setParameter("domainId", report.getId().getDomainId())
                    .setParameter("timeInInt", report.getId().getTimeInInt())
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
        String ids = list.stream().map(m -> m.getId().toString()).collect(Collectors.joining(","));
        String hql = String.format("select id, sum(click) from %s where id.fakeLinkId in (%s) group by id.fakeLinkId", db.getModelClass().getName(), ids);
        Query query = session.createQuery(hql);
        //query.setParameter("ids", ids);
        List<Object[]> ds = query.list();
        Map<Integer, Integer> collect = ds.stream().collect(Collectors.toMap(m -> ((ReportId) m[0]).getFakeLinkId(), m -> ((Long)m[1]).intValue()));
        session.close();
        ArrayList<FakeLinkResponse> rs = new ArrayList<>(list.size());
        for (int i = 0; i < list.size(); i++) {
            rs.add(new FakeLinkResponse(list.get(i), collect.getOrDefault(list.get(i).getId(), 0)));
        }
        return rs;
        /*return list.stream().map(f -> {
            int click = loadAll().stream().filter(r -> r.getFakeLinkId().equals(f.getId())).mapToInt(o -> o.getClick()).sum();
            return new FakeLinkResponse(f, click);
        }).collect(Collectors.toList());*/
    }

    public List<Object[]> getReportByUser(int userId) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        String hql = String.format("select timeInInt, sum(click) from %s where ownerId = %s group by timeInInt ORDER BY timeInInt DESC", db.getModelClass().getSimpleName(), userId);
        Query query = session.createNativeQuery(hql);
        List<Object[]> ds = query.list();
        List<Object[]> collect = ds.stream().map(m -> new Object[]{ReportEntity.parseIntToDate((int)m[0]), m[1]}).collect(Collectors.toList());
        session.close();
        return collect;
    }
}
