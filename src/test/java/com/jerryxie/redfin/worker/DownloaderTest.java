package com.jerryxie.redfin.worker;

import java.util.List;
import java.util.concurrent.CountDownLatch;

import org.junit.Test;
import org.openqa.selenium.WebDriver;

import com.jerryxie.redfin.configurations.DriverConfig;
import com.jerryxie.redfin.configurations.DriverFactory;
import com.jerryxie.redfin.domain.Home;

public class DownloaderTest {

    @Test
    public void test() {
        DriverFactory factory = new DriverConfig().getWebDriverFactory();
        WebDriver driver = factory.getNextAvailableDriver();
        HomeInfoFatcher downloader = new HomeInfoFatcher("95132");
        String url = downloader.getSourceApi(driver);
        System.out.println(url);
        factory.shutdown();
    }

    @Test
    public void testParseCSV() {
        String url = "https://www.redfin.com/stingray/api/gis-csv?al=1&market=sanfrancisco&min_stories=1&num_homes=1000&ord=redfin-recommended-asc&page_number=1&region_id=39437&region_type=2&sf=1,2,3,5,6,7&sold_within_days=365&status=9&uipt=1,2,3,4,5,6&v=8";
        CSVRemoteFileParser downloader = new CSVRemoteFileParser(url, new CountDownLatch(1));
        List<Home> homes = downloader.downloadAndParseCSVFile(url);
        System.out.println(homes);
    }

}
