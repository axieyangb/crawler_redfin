package com.jerryxie.redfin.worker;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.csvreader.CsvReader;
import com.jerryxie.redfin.domain.House;
import com.jerryxie.redfin.domain.Region;
import com.jerryxie.redfin.service.HouseService;
import com.jerryxie.redfin.service.RegionService;

@Component
@Scope("prototype")
public class CSVRemoteFileParser implements Runnable {

	private CountDownLatch lock;
	private Logger logger = Logger.getLogger(CSVRemoteFileParser.class);
	private Region region;
	@Autowired
	HouseService homeSerive;
	@Autowired
	RegionService regionService;

	public CSVRemoteFileParser(Region region, CountDownLatch lock) {
		this.region = region;
		this.lock = lock;
	}

	@Override
	public void run() {
		try {
			List<House> homes = downloadAndParseCSVFile(region.getRegionId());
			this.homeSerive.addHomes(homes);
			region.setLastFetchedTime(new Date(System.currentTimeMillis()));
			regionService.saveRegion(region);
		} finally {
			lock.countDown();
		}

	}

	public List<House> downloadAndParseCSVFile(String regionId) {
		List<House> homeArr = new ArrayList<>();

		if (StringUtils.isEmpty(regionId)) {
			return homeArr;
		}
		String url = composeSourceUrl(regionId);
		try (Reader reader = new InputStreamReader(
				new ByteArrayInputStream(Jsoup.connect(url).validateTLSCertificates(false).execute().bodyAsBytes()),
				"UTF-8")) {
			CsvReader csvReader = new CsvReader(reader);
			csvReader.readHeaders();

			while (csvReader.readRecord()) {
				House home = new House();
				home.setSoldDate(csvReader.get("SOLD DATE"));
				home.setPropertyType(csvReader.get("PROPERTY TYPE"));
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

	private String composeSourceUrl(String id) {
		return String.format(
				"https://www.redfin.com/stingray/api/gis-csv?al=1&market=sanfrancisco&min_stories=1&num_homes=1000&ord=redfin-recommended-asc&page_number=1&region_id=%s&region_type=2&sf=1,2,3,5,6,7&sold_within_days=365&status=9&uipt=1,2,3,4,5,6&v=8",
				id);
	}
}
