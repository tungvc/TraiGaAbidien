package abidien.autopost.services;

import abidien.autopost.models.FakeLinkEntity;
import abidien.autopost.models.FakeLinkResponse;
import abidien.autopost.models.ReportEntity;
import abidien.chuongga.Environment;
import abidien.services.IDataService;
import abidien.services.InmemoryDataService;
import javafx.util.Pair;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by ABIDIEN on 14/01/2017.
 */
public class ReportService extends InmemoryDataService<Pair<Integer, Integer>, ReportEntity> {
    public ReportService(Environment env) {
        super(env.getReportDataDriver());
    }

    public void updateReport(Pair<Integer, Integer> key, Integer value) {
        ReportEntity r = load(key);
        if (r == null) {
            saveOrUpdate(new ReportEntity(key.getKey(), key.getValue(), value));
        } else {
            r.setClick(r.getClick() + value);
            saveOrUpdate(r);
        }
    }

    public List<FakeLinkResponse> getReport(List<FakeLinkEntity> list) {
        return list.stream().map(f -> {
            int click = loadAll().stream().filter(r -> r.getFakeLinkId().equals(f.getId())).mapToInt(o -> o.getClick()).sum();
            return new FakeLinkResponse(f, click);
        }).collect(Collectors.toList());

    }
}
