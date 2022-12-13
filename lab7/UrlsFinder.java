import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class UrlsFinder {

    public static final String REGEX_PATTERN = "<a\\s*href\\s*=\\s*\"(?<url>http:\\/\\/\\S*)\"";
    public static final String REGEX_GROUP_NAME = "url";
    private static Pattern pattern = Pattern.compile(REGEX_PATTERN);

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

        }

        return urls;
    }
}
