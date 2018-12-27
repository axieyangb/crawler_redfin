package com.jerryxie.redfin.worker;

import java.io.IOException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import javax.swing.text.html.parser.Element;

import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.jerryxie.redfin.service.RegionService;

@Component
@Scope("prototype")
public class RegionIDInfoFatcher implements Runnable {

    @Autowired
    RegionService regionService;
    private Logger logger = Logger.getLogger(RegionIDInfoFatcher.class);
    Set<String> countySet = new HashSet<>();

    public RegionIDInfoFatcher() {

        countySet.add("San-Jose");
        countySet.add("Santa-Clara");
        countySet.add("Sunnyvale");
        countySet.add("Mountain-View");
        countySet.add("Milpitas");
    }

    @Override
    public void run() {
        Document doc;
        try {
            doc = Jsoup.connect("https://www.redfin.com/sitemap/345/CA/Santa-Clara-County")
                    .cookie("RF_UNBLOCK_ID", "ymGZPMO4").validateTLSCertificates(false).get();
            countySet.forEach(county -> {
                Elements elems = doc.getElementsByAttributeValueContaining("href", county);
                for (Iterator<org.jsoup.nodes.Element> itr = elems.iterator(); itr.hasNext();) {
                    org.jsoup.nodes.Element element = itr.next();
                    if (element.attr("href").startsWith("/sitemap")) {
                        saveRegionCodes(element.attr("href"));
                    }

                }
                ;
            });
        } catch (IOException e) {
            logger.error(e);
        }

    }

    private void saveRegionCodes(String url) {
        Document doc;
        try {
            if (StringUtils.isEmpty(url)) {
                logger.info("Empty URL");
                return;
            }
            // logger.info(String.format("Fetching the country for %s", url));
            doc = Jsoup.connect("https://www.redfin.com" + url).cookie("RF_UNBLOCK_ID", "ymGZPMO4").timeout(10 * 1000)
                    .validateTLSCertificates(false).get();
            Elements elems = doc.getElementsByAttributeValueContaining("href", "/neighborhood/");
            for (Iterator<org.jsoup.nodes.Element> itr = elems.iterator(); itr.hasNext();) {
                org.jsoup.nodes.Element elem = itr.next();
                String[] tokens = elem.attr("href").split("/");
                regionService.insertCountyData(tokens[2], tokens[4], tokens[5]);
            }
            logger.info(String.format("Fetching the county for %s, found %d sub county", url, elems.size()));
        } catch (IOException e) {
            logger.error(e);
        }

    }

}
