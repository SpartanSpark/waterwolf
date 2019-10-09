package main;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class GoogleSearch {

    private static Matcher matcher;
    private static final String DOMAIN_NAME_PATTERN
            = "([a-zA-Z0-9]([a-zA-Z0-9\\-]{0,61}[a-zA-Z0-9])?\\.)+[a-zA-Z]{2,15}";
    private static Pattern patrn = Pattern.compile(DOMAIN_NAME_PATTERN);

    public static String getDomainName(String url) {

        String domainName = "";
        matcher = patrn.matcher(url);

        if (matcher.find()) {
            domainName = matcher.group(0).toLowerCase().trim();
        }

        return domainName;
    }

    public static Document query(String query) throws IOException {

        String url = "https://www.google.com/search?q=" + query + "&num=10";

        Document doc = Jsoup
                .connect(url)
                .userAgent("Jsoup client")
                .timeout(5000).get();

        return doc;
    }
}