import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class Crawler implements Runnable {

    public static final String REGEX_PATTERN = "<a\\s*href\\s*=\\s*\"(?<url>http:\\/\\/\\S*)\"";// "\\<a
                                                                                                // href\\s*=\\s*\\\"(?<url>http:\\/\\/www\\.\\S*)\\\"\\>";
    public static final String REGEX_GROUP_NAME = "url";
    private static Pattern pattern = Pattern.compile(REGEX_PATTERN);
    private boolean freezed = false;;
    private URLDepthPair currentPair;
    private boolean stopped = false;

    public void SetUrlData(URLDepthPair data) {
        currentPair = data;
    }

    public void Stop() {
        stopped = true;
    }

    @Override
    public void run() {
        while (!stopped) {

            currentPair = UrlsContainer.getFreeElement();
            if (currentPair == null) {
                if (!freezed) {
                    WebScanner.CrawlerFreezedHandler(this);
                    freezed = true;
                }
                continue;
            }

            if (freezed) {
                freezed = false;
                WebScanner.CrawlerUnfreezedHandler(this);
            }

            var pageUrls = findUrls(currentPair.getUrl());
            UrlsContainer.AddUnchecked(pageUrls, currentPair.getDepth() + 1);
            currentPair = null;
        }
    }

    public static List<String> findUrls(String startUrl) {
        var urls = new ArrayList<String>();
        BufferedReader in = null;
        String inputLine = null;

        try {
            var url = new URL(startUrl);
            in = new BufferedReader(
                    new InputStreamReader(
                            url.openStream()));

            while ((inputLine = in.readLine()) != null) {
                var matcher = pattern.matcher(inputLine);
                while (matcher.find()) {
                    var matchUrl = matcher.group(REGEX_GROUP_NAME);
                    if (matchUrl.endsWith("/"))
                        matchUrl = matchUrl.substring(0, matchUrl.length() - 1);
                    urls.add(matchUrl);
                }
            }
            in.close();
        } catch (Exception e) {
            // System.out.println(Thread.currentThread().getName());
            // System.out.println(e.getClass());
            // System.out.println(inputLine);
            // System.out.println(e.getMessage());
        }
        return urls;
    }

    public boolean isStopped() {
        return stopped;
    }
}