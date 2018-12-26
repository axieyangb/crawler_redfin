package com.jerryxie.redfin.worker;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.csvreader.CsvReader;
import com.jerryxie.redfin.domain.Home;
import com.jerryxie.redfin.service.HomeService;

@Component
@Scope("prototype")
public class CSVRemoteFileParser implements Runnable {

    private String url;
    private CountDownLatch lock;
    private Logger logger = Logger.getLogger(CSVRemoteFileParser.class);

    @Autowired
    HomeService homeSerive;

    public CSVRemoteFileParser(String url, CountDownLatch lock) {
        this.url = url;
        this.lock = lock;
    }

    @Override
    public void run() {
        try {
            List<Home> homes = downloadAndParseCSVFile(url);
            homeSerive.saveHomes(homes);
        } finally {
            lock.countDown();
        }

    }

    public List<Home> downloadAndParseCSVFile(String url) {
        List<Home> homeArr = new ArrayList<>();
        try (Reader reader = new InputStreamReader(new URL(url).openStream(), "UTF-8")) {
            CsvReader csvReader = new CsvReader(reader);
            csvReader.readHeaders();
            Home home = new Home();
            while (csvReader.readRecord()) {
                home.setSoldDate(csvReader.get("SOLD DATE"));
                home.setLocation(csvReader.get("ADDRESS"));
                home.setCity(csvReader.get("CITY"));
                home.setZip(Integer.parseInt(csvReader.get("ZIP")));
                home.setPrice(Integer.parseInt(csvReader.get("PRICE")));
                String bedsStr = csvReader.get("BEDS");
                home.setBeds(Double.parseDouble(StringUtils.isEmpty(bedsStr) ? "-1" : bedsStr));
                String bathsStr = csvReader.get("BATHS");
                home.setBaths(Double.parseDouble(StringUtils.isEmpty(bathsStr) ? "-1" : bathsStr));
                String squareFeetStr = csvReader.get("SQUARE FEET");
                home.setSquareFeet(Integer.parseInt(StringUtils.isEmpty(squareFeetStr) ? "-1" : squareFeetStr));
                String lotSizeStr = csvReader.get("LOT SIZE");
                home.setLotSize(Integer.parseInt(StringUtils.isEmpty(lotSizeStr) ? "-1" : lotSizeStr));
                String yearStr = csvReader.get("YEAR BUILT");
                home.setYearBuild(Integer.parseInt(StringUtils.isEmpty(yearStr) ? "-1" : yearStr));
                String daysOnMarketStr = csvReader.get("DAYS ON MARKET");
                home.setDaysOnMarket(Integer.parseInt(StringUtils.isEmpty(daysOnMarketStr) ? "-1" : daysOnMarketStr));
                String dollarPerSquareStr = csvReader.get("$/SQUARE FEET");
                home.setDollarPerSquareFeet(
                        Integer.parseInt(StringUtils.isEmpty(dollarPerSquareStr) ? "-1" : dollarPerSquareStr));
                String hoaStr = csvReader.get("HOA/MONTH");

                home.setHoa(Double.parseDouble(StringUtils.isEmpty(hoaStr) ? "-1" : hoaStr));
                home.setUrl(csvReader.get(
                        "URL (SEE http://www.redfin.com/buy-a-home/comparative-market-analysis FOR INFO ON PRICING)"));
                String[] tokens = home.getUrl().split("/");
                String id = tokens[tokens.length - 1];
                home.setId(id);
                home.setLongitude(Float.parseFloat(csvReader.get("LONGITUDE")));
                home.setLatitude(Float.parseFloat(csvReader.get("LATITUDE")));
                homeArr.add(home);
            }
        } catch (UnsupportedEncodingException e) {
            logger.error(e);
        } catch (MalformedURLException e) {
            logger.error(e);
        } catch (IOException e) {
            logger.error(e);
        }
        return homeArr;
    }

}
