import java.util.ArrayList;

public class Crawler implements Runnable {

    private int _maxDepth;
    private ArrayList<String> _errors = new ArrayList<String>();

    public Crawler(int max_depth) {
        _maxDepth = max_depth;
    }

    public ArrayList<String> getErrors()
    {
        return _errors;
    }

    @Override
    public void run() {

        URLDepthPair startUrlDepthPair = null;

        do {
            startUrlDepthPair = UrlsContainer.getFreeElement();
            if (startUrlDepthPair == null)
                return;
        } while (startUrlDepthPair.getDepth() >= _maxDepth);
        try
        {
            var urls = UrlsFinder.findUrls(startUrlDepthPair.getUrl());
            var newDepth = startUrlDepthPair.getDepth() + 1;
            UrlsContainer.addUnchecked(urls, newDepth);
        }
        catch (Exception e)
        {
            _errors.add(e.getMessage());
        }
    }
}
