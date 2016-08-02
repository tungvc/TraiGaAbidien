package abidien.services.adsense.api;

import abidien.models.ReportResponse;
import com.google.api.services.adsense.AdSense;
import com.google.api.services.adsense.AdSense.Accounts.Reports.Generate;
import com.google.api.services.adsense.model.AdsenseReportsGenerateResponse;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
   */
  public static ReportResponse run(AdSense adsense, String accountId, String adClientId) throws Exception {
    String startDate = "today-30d";
    String endDate = "today-1d";
    Generate request = adsense.accounts().reports().generate(accountId, startDate, endDate);

    // Specify the desired ad client using a filter.
    request.setFilter(Arrays.asList("AD_CLIENT_ID==" + escapeFilterParameter(adClientId)));

    request.setMetric(Arrays.asList("PAGE_VIEWS", "AD_REQUESTS", "AD_REQUESTS_COVERAGE", "CLICKS",
        "AD_REQUESTS_CTR", "COST_PER_CLICK", "AD_REQUESTS_RPM", "EARNINGS"));
    request.setDimension(Arrays.asList("DATE"));

    // Sort by ascending date.
    request.setSort(Arrays.asList("+DATE"));

    // Run report.
    AdsenseReportsGenerateResponse response = request.execute();
    List<List<String>> rows = fillMissingDates(response);

    List<String> headers = new ArrayList<>();
    if (rows != null && !rows.isEmpty()) {
      // Display headers.
      for (AdsenseReportsGenerateResponse.Headers header : response.getHeaders()) {
        headers.add(header.getName());
        //System.out.printf("%25s", header.getName());
      }
      return new ReportResponse(null, headers, rows);
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

  public static List<List<String>> fillMissingDates(AdsenseReportsGenerateResponse response)
          throws ParseException {
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
      } else {
        List<String> newRow = new ArrayList<String>();
        newRow.add(dateFormat.format(date));
        for (int i = 1; i < response.getHeaders().size(); i++) {
          newRow.add("no data");
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

    return processedData;
  }
}
