import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class UrlsFinder {

    public static final String REGEX_PATTERN = "<a\\s*href\\s*=\\s*\"(?<url>http:\\/\\/\\S*)\"";
    public static final String REGEX_GROUP_NAME = "url";
    private Pattern _pattern = Pattern.compile(REGEX_PATTERN);

    public List<String> findUrls(String startUrl) throws IOException {
        var urls = new ArrayList<String>();

        URL oracle = new URL(startUrl);
        BufferedReader in = new BufferedReader(new InputStreamReader(oracle.openStream()));

        String inputLine;
        while ((inputLine = in.readLine()) != null)
        {
            var matcher = _pattern.matcher(inputLine);

            while (matcher.find()) {
                var matchUrl = matcher.group(REGEX_GROUP_NAME);
                if (matchUrl.endsWith("/"))
                    matchUrl = matchUrl.substring(0, matchUrl.length() - 1);
                urls.add(matchUrl);
        }
        }
        in.close();
        return urls;
    }
}
