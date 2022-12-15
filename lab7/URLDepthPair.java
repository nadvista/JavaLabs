public class URLDepthPair {
    private String _url;
    private int _depth;

    public URLDepthPair(String url, int depth) {
        this._url = url;
        this._depth = depth;
    }

    public String getUrl() {
        return _url;
    }

    public int getDepth() {
        return _depth;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((_url == null) ? 0 : _url.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        URLDepthPair other = (URLDepthPair) obj;
        if (_url == null) {
            if (other._url != null)
                return false;
        } else if (!_url.equals(other._url))
            return false;
        return true;
    }

    
}