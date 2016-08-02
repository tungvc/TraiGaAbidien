package abidien.models;

import java.util.List;

/**
 * Created by ABIDIEN on 02/08/2016.
 */
public class ReportResponse {
    List<Double> pointChart;
    List<String> header;
    List<List<String>> data;

    public ReportResponse(List<Double> pointChart, List<String> header, List<List<String>> data) {
        this.pointChart = pointChart;
        this.header = header;
        this.data = data;
    }

    public List<Double> getPointChart() {
        return pointChart;
    }

    public List<String> getHeader() {
        return header;
    }

    public List<List<String>> getData() {
        return data;
    }
}
