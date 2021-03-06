package abidien.services.adsense.api;

import abidien.models.ReportResponse;
import com.google.api.services.adsense.AdSense;
import com.google.api.services.adsense.AdSense.Accounts.Reports.Generate;
import com.google.api.services.adsense.model.AdsenseReportsGenerateResponse;

import java.math.RoundingMode;
import java.text.*;
import java.util.*;

/**
 * This example retrieves a report, using a filter for a specified ad client.
 *
 * Tags: accounts.reports.generate
 *
 * @author sgomes@google.com (Sérgio Gomes)
 *
 */
public class GenerateReport {
  /**
   * Runs this sample.
   * @param adsense AdSense service object on which to run the requests.
   * @param accountId the ID for the account to be used.
   * @param adClientId the ad client ID on which to run the report.
   * @throws Exception
   * startDate, endDate format "YYYY-MM-DD"
   */
  public static ReportResponse run(AdSense adsense, String accountId, String adClientId, String startDate, String endDate) throws Exception {
    if (startDate == null || startDate.isEmpty()) startDate = "today-7d";
    if (endDate == null || endDate.isEmpty()) endDate = "today-1d";
    Generate request = adsense.accounts().reports().generate(accountId, startDate, endDate);

    // Specify the desired ad client using a filter.
    request.setFilter(Arrays.asList("AD_CLIENT_ID==" + escapeFilterParameter(adClientId)));

    request.setMetric(Arrays.asList("PAGE_VIEWS", "AD_REQUESTS", /*"AD_REQUESTS_COVERAGE", */"CLICKS",
        "AD_REQUESTS_CTR", "COST_PER_CLICK", "PAGE_VIEWS_RPM", "EARNINGS"));
    request.setDimension(Arrays.asList("DATE"));

    // Sort by ascending date.
    request.setSort(Arrays.asList("+DATE"));

    // Run report.
    AdsenseReportsGenerateResponse response = request.execute();
    List<Double> pointChart = new ArrayList<>();
    List<List<String>> rows = fillMissingDates(response, pointChart);

    List<String> headers = new ArrayList<>();
    if (rows != null && !rows.isEmpty()) {
      // Display headers.
      for (AdsenseReportsGenerateResponse.Headers header : response.getHeaders()) {
        headers.add(header.getName());
        //System.out.printf("%25s", header.getName());
      }
      String points = "";
      for (Double p: pointChart)
        points += p.intValue() + ",";
      return new ReportResponse(points.substring(0, points.length() - 2), headers, rows);
//      System.out.println();
//
//      // Display results.
//      for (List<String> row : rows) {
//        for (String column : row) {
//          System.out.printf("%25s", column);
//        }
//        System.out.println();
//        }
//
//      System.out.println();
    } else {
      System.out.println("No rows returned.");
    }

    System.out.println();
    return null;
  }

  /**
   * Escape special characters for a parameter being used in a filter.
   * @param parameter the parameter to be escaped.
   * @return the escaped parameter.
   */
  public static String escapeFilterParameter(String parameter) {
    return parameter.replace("\\", "\\\\").replace(",", "\\,");
  }

  public static List<List<String>> fillMissingDates(AdsenseReportsGenerateResponse response, List<Double> pointChart)
          throws ParseException {
    Locale locale  = new Locale("en", "UK");
    String pattern = "###.##";
    DecimalFormat df = (DecimalFormat) NumberFormat.getNumberInstance(locale);
    df.applyPattern(pattern);
    DecimalFormat dfPercent = new DecimalFormat("##.##%");
    DateFormat fullDate = new SimpleDateFormat("yyyy-MM-dd");
    Date startDate = fullDate.parse(response.getStartDate());
    Date endDate = fullDate.parse(response.getEndDate());

    int currentPos = 0;

    // Check if the results fit the requirements for this method.
    if (response.getHeaders() == null && !response.getHeaders().isEmpty()) {
      throw new RuntimeException("No headers defined in report results.");
    }

    if (response.getHeaders().size() < 2 ||
            !response.getHeaders().get(0).getType().equals("DIMENSION")) {
      throw new RuntimeException("Insufficient dimensions and metrics defined.");
    }

    if (response.getHeaders().get(1).getType().equals("DIMENSION")) {
      throw new RuntimeException("Only one dimension allowed.");
    }

    DateFormat dateFormat = null;
    Date date = null;
    // Adjust output format and start date according to time period.
    if (response.getHeaders().get(0).getName().equals("DATE")) {
      dateFormat = fullDate;
      date = startDate;
    } else if (response.getHeaders().get(0).getName().equals("MONTH")) {
      dateFormat = new SimpleDateFormat("yyyy-MM");
      date = fullDate.parse(dateFormat.format(startDate) + "-01");
    } else {
      // Return existing report rows without date filling.
      return response.getRows();
    }

    List<List<String>> processedData = new ArrayList<List<String>>();

    while (date.compareTo(endDate) <= 0) {
      Date rowDate = null;
      List<String> currentRow = null;
      if (response.getRows() != null && response.getRows().size() > currentPos) {
        currentRow = response.getRows().get(currentPos);
        // Parse date on current row.
        if (response.getHeaders().get(0).getName().equals("DATE")) {
          rowDate = fullDate.parse(currentRow.get(0));
        } else if (response.getHeaders().get(0).getName().equals("MONTH")) {
          rowDate = fullDate.parse(currentRow.get(0) + "-01");
        }
      }

      // Is there an entry for this date?
      if (rowDate != null && date.equals(rowDate)) {
        processedData.add(currentRow);
        currentPos += 1;
        String value = currentRow.get(1);
        if (value.isEmpty()) value = "0";
        pointChart.add(Double.valueOf(value));
      } else {
        List<String> newRow = new ArrayList<String>();
        newRow.add(dateFormat.format(date));
        pointChart.add(0d);
        for (int i = 1; i < response.getHeaders().size(); i++) {
          newRow.add("0");
        }
        processedData.add(newRow);
      }

      // Increment date accordingly.
      Calendar calendar = Calendar.getInstance();
      calendar.setTime(date);
      if (response.getHeaders().get(0).getName().equals("DATE")) {
        calendar.add(Calendar.DATE, 1);
      } else if (response.getHeaders().get(0).getName().equals("MONTH")) {
        calendar.add(Calendar.MONTH, 1);
      }
      date = calendar.getTime();
    }

    List<String> total = new ArrayList<>();
    total.add("TOTAL");
    //calculate total
    for (int i = 1; i < response.getHeaders().size(); i++) {
      String name = response.getHeaders().get(i).getName();
      switch (name) {
        case "PAGE_VIEWS":
        case "AD_REQUESTS":
        case "CLICKS":
          int sum = 0;
          for (int j = 0; j < processedData.size(); j++) {
            String v = processedData.get(j).get(i);
            if (v.isEmpty()) v = "0";
            sum += Integer.valueOf(v);
          }
          total.add(String.valueOf(sum));
          break;
        /*case "AD_REQUESTS_COVERAGE":*/
        case "EARNINGS":
          double dSum = 0;
          for (int j = 0; j < processedData.size(); j++) {
            String v = processedData.get(j).get(i);
            if (v.isEmpty()) v = "0";
            dSum += Double.valueOf(v);
          }
          total.add(df.format(dSum));
          break;
        case "COST_PER_CLICK":
        case "AD_REQUESTS_CTR":
        case "PAGE_VIEWS_RPM":
          double avg = 0;
          for (int j = 0; j < processedData.size(); j++) {
            String v = processedData.get(j).get(i);
            if (v.isEmpty()) v = "0";
            avg += Double.valueOf(v);
          }
          total.add(df.format(avg/processedData.size()));
          break;
      }
    }
    processedData.add(0, total);
    return processedData;
  }
}
