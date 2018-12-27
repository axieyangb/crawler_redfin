package com.jerryxie.redfin.worker;

import java.io.IOException;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.Test;

import com.jerryxie.redfin.domain.House;

public class JsoupTest {

    @Test
    public void test() throws IOException {
        Document doc = Jsoup.connect("https://www.redfin.com/sitemap/345/CA/Santa-Clara-County").cookie("RF_UNBLOCK_ID", "ymGZPMO4")
              .get();
        System.out.println(doc.html());
    }
    
    @Test
    public void test2() {
        CSVRemoteFileParser parser = new CSVRemoteFileParser();
        List<House> homes = parser.downloadAndParseCSVFile("496866");
        System.out.println(homes);
    }

}
