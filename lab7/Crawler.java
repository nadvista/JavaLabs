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
    private boolean stopped = false;
    private boolean freezed = false;
    private ICrawlerEventsHandler eventsHandler;

    public Crawler(ICrawlerEventsHandler handler) {
        eventsHandler = handler;
    }

    @Override
    public void run() {
        while (!stopped) {

            var currentPair = UrlsContainer.getFreeElement();
            if (currentPair == null) {
                if (!isFreezed())
                    Freese();
                continue;
            }
            System.out.println(String.format("%s in depth %s", currentPair.getUrl(), currentPair.getDepth()));
            var newDepth = currentPair.getDepth() + 1;
            var foundUrls = findUrls(currentPair.getUrl());

            for (var url : foundUrls) {
                UrlsContainer.addUnchecked(foundUrls, newDepth);
            }
        }
    }

    public void Stop() {
        stopped = true;
    }

    public void Resume() {
        freezed = false;
        if (eventsHandler != null)
            eventsHandler.CrawlerResumed(this);
    }

    public void Freese() {
        freezed = true;
        if (eventsHandler != null)
            eventsHandler.CrawlerFreezed(this);
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
            WebScanner.CrawlerError();
        }
        return urls;
    }

    public boolean isStopped() {
        return stopped;
    }

    public boolean isFreezed() {
        return freezed;
    }
}