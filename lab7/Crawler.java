public class Crawler implements Runnable {

    private int _maxDepth;

    public Crawler(int max_depth) {
        _maxDepth = max_depth;
    }

    @Override
    public void run() {

        URLDepthPair startUrlDepthPair = null;

        do {
            startUrlDepthPair = UrlsContainer.getFreeElement();
            if (startUrlDepthPair == null)
                return;
        } while (startUrlDepthPair.getDepth() >= _maxDepth);

        var urls = UrlsFinder.findUrls(startUrlDepthPair.getUrl());
        var newDepth = startUrlDepthPair.getDepth() + 1;
        UrlsContainer.addUnchecked(urls, newDepth);
    }
}
