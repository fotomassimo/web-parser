package web.parser.service;

import java.io.IOException;
import org.jsoup.Jsoup;

public class FromUrlContentLoader implements GenericContentLoader<String> {
    private static final String COOKIE_NAME = "_fsuid";
    private static final String COOKIE_VALUE = "71353a3d-72f1-4008-b54f-9224986b0a13";
    private static final String REFERRER_VALUE = "https://www.aboutyou.de/c/maenner/bekleidung-20290";
    private static final int TIMEOUT_RANGE_MAX_VALUE_MS = 10000;
    private static final String USER_AGENT_VALUE =
            "Mozilla/5.0 (Windows NT 6.3; Win64; x64) AppleWebKit/537.36 "
                    + "(KHTML, like Gecko) Chrome/91.0.4472.124 Safari/537.36";

    @Override
    public String getContent(String source) {
        try {
            return Jsoup.connect(source)
                    .ignoreContentType(true)
                    .userAgent(USER_AGENT_VALUE)
                    .cookie(COOKIE_NAME, COOKIE_VALUE)
                    .referrer(REFERRER_VALUE)
                    .followRedirects(true)
                    .timeout((int) (Math.random() * TIMEOUT_RANGE_MAX_VALUE_MS))
                    .execute()
                    .body();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }
}
