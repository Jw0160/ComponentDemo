package com.common.common_base.URL;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @author nowind_lee@qq.com
 * @version 0.5
 */
public class URLParser {

    private String host;

    private Integer port;

    private String protocol;

    // use LinkedHashMap to keep the order of items
    private LinkedHashMap<String, List<String>> params = new LinkedHashMap<String, List<String>>();

    private String path;

    private String userInfo;

    private String query;

    private String charset;

    private boolean hasDomain = true;

    public String orignUrl = "";

    public String routeAction = "";

    public URLParser(String url) throws MalformedURLException {
        this(url, "utf-8");
    }


    /**
     * http://user:password@host:port/aaa/bbb;xxx=xxx?eee=ggg&fff=ddd&fff=lll
     *
     * @throws MalformedURLException
     */
    public URLParser(final String url, final String charset) throws MalformedURLException {
        checkNull(url, "url");
        orignUrl = url;
        if (charset != null && !Charset.isSupported(charset)) {
            throw new IllegalArgumentException("charset is not supported: " + charset);
        }
        URI lUri = null;
        if (url.matches("\\w+[:][/][/].*")) {
//            LogUtil.d("hasDomian == true && url == " + url);
            hasDomain = true;
            try {
                lUri = new URI(url);
            } catch (URISyntaxException pE1) {
                try {
                    lUri = new URI("http://www.mao-wo.com/");
                } catch (URISyntaxException pE) {
                    pE.printStackTrace();
                }
            }

        } else {

            hasDomain = false;
            try {
                lUri = new URI("http://" + url);
            } catch (URISyntaxException pE) {
                try {
                    lUri = new URI("http://www.mao-wo.com/");
                } catch (URISyntaxException pE1) {
                    pE1.printStackTrace();
                }
            }
        }

        this.charset = charset;


        if (lUri != null) {
            if (hasDomain) {

                this.protocol = String.valueOf(lUri.getScheme());
                this.host = lUri.getHost();
                this.port = lUri.getPort();
                if (this.port != null && this.port == -1) {
                    this.port = null;
                }
                this.path = lUri.getPath();
                this.userInfo = lUri.getUserInfo();
            } else {
                this.path = url.startsWith("/") ? lUri.getPath() : lUri.getPath().substring(1);
            }
            this.query = lUri.getQuery();
            this.params = parseQueryString(substringAfter(url, "?"));
        }

        if (query != null && orignUrl.contains("?")){
            try {
                routeAction = orignUrl.split("\\?")[0];
            } catch (Exception e) {
                e.printStackTrace();
            }
        }else{
            routeAction = orignUrl;
        }

    }

    public void addParam(String name, String value) {
        addParams(name, Arrays.asList(encode(value)));
    }

    public void addParams(String name, List<String> values) {
        List<String> list = getOrCreate(params, name);
        for (String value : values) {
            list.add(encode(value));
        }
    }

    public void removeParams(String name) {
        if (name == null) {
            return;
        }
        this.params.remove(name);
    }

    public void updateParams(String name, String... values) {
        checkNull(name, "name");
        if (values.length == 0) {
            throw new IllegalArgumentException("values should not be empty");
        }
        List<String> list = getOrCreate(params, name);
        list.clear();
        for (String value : values) {
            list.add(encode(value));
        }
    }

    public List<String> getRawParams(String name) {
        checkNull(name, "name");
        return this.params.get(name);
    }

    public String getRawParam(String name) {
        List<String> params = getRawParams(name);
        return params == null ? null : params.get(0);
    }

    public String getParam(String name) throws UnsupportedEncodingException {
        String value = getRawParam(name);
        return value == null ? null : decode(value);
    }

    public String getParamNoDecode(String name) throws UnsupportedEncodingException {
        String value = getRawParam(name);
        return value == null ? null : value;
    }

    public List<String> getParams(String name) {
        List<String> rawParams = getRawParams(name);
        if (rawParams == null) {
            return null;
        }
        List<String> params = new ArrayList<String>();
        for (String value : rawParams) {
            params.add(encode(value));
        }
        return params;
    }

    public Map<String, String> getSimple() {
        Map<String, String> map = new HashMap<String, String>();
        for (String name : this.params.keySet()) {
            String value = this.params.get(name).get(0);
            map.put(name, encode(value));
        }
        return map;
    }

    public String getHost() {
        return host;
    }

    public Integer getPort() {
        return port;
    }

    public String getProtocol() {
        return protocol;
    }

    public String getPath() {
        return path;
    }

    public String getUserInfo() {
        return userInfo;
    }

    public String getCharset() {
        return charset;
    }

    public String getQuery() {
        return query;
    }

    public String getRouteAction() {
        return routeAction;
    }

    public void setRouteAction(String routeAction) {
        this.routeAction = routeAction;
    }

    public String createQueryString() {
        if (this.params.isEmpty()) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        for (String name : this.params.keySet()) {
            List<String> values = this.params.get(name);
            for (String value : values) {
                if (sb.length() > 0) {
                    sb.append("&");
                }
                sb.append(name).append("=").append(encode(value));
            }
        }
        return sb.toString();
    }


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        if (this.protocol != null) {
            sb.append(this.protocol).append("://");
        }
        if (this.userInfo != null) {
            sb.append(this.userInfo).append("@");
        }
        if (this.host != null) {
            sb.append(host);
        }
        if (this.port != null) {
            sb.append(":").append(this.port);
        }
        sb.append(this.path);
        String query = createQueryString();
        if (query.trim().length() > 0) {
            sb.append("?").append(query);
        }

        return sb.toString();
    }

    public String decode(String value) {
        checkNull(value, "value to decode");
        try {
            return charset == null ? value : URLDecoder.decode(value, charset);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

    public String encode(String value) {
        checkNull(value, "value to encode");
        try {
            return charset == null ? value : URLEncoder.encode(value, charset);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

    private static List<String> getOrCreate(Map<String, List<String>> map, String name) {
        checkNull(name, "name");
        List<String> list = map.get(name);
        if (list == null) {
            list = new ArrayList<String>();
            map.put(name, list);
        }
        return list;
    }

    private static void checkNull(Object value, String fieldName) {
        if (value == null) {
            throw new IllegalArgumentException(fieldName + " should not be null");
        }
    }

    public static LinkedHashMap<String, List<String>> parseQueryString(String query) {
        LinkedHashMap<String, List<String>> params = new LinkedHashMap<String, List<String>>();
        if (isBlank(query)) {
            return params;
        }
        String[] items = query.split("&");
        for (String item : items) {
            String name = substringBefore(item, "=");
            String value = substringAfter(item, "=");
            List<String> values = getOrCreate(params, name);
            values.add(value);
        }
        return params;
    }


    private static boolean isBlank(String str) {
        return str == null || str.trim().length() == 0;
    }

    private static String substringBefore(String str, String sep) {
        int index = str.indexOf(sep);
        return index == -1 ? "" : str.substring(0, index);
    }

    private static String substringAfter(String str, String sep) {
        int index = str.indexOf(sep);
        return index == -1 ? "" : str.substring(index + 1);
    }


    /**
     * Usage.
     */
    public static void main(String[] args) throws Exception {
		String url = "ftp://www.test.com/aaa/bbb;xxx=xxx?eee=111&fff=222&fff=333";
//        String url = "maowo://apps.mw/goods/show?goodsId=15092302821994496";

        URLParser parser = new URLParser(url);

        // get basic infomation
        System.out.println(parser.getHost());
        System.out.println(parser.getPort());
        System.out.println(parser.getProtocol());
        System.out.println(parser.getPath());
        System.out.println(parser.getQuery());
        System.out.println(parser.getUserInfo());
        System.out.println(parser.getCharset());

        // get paramsa
        System.out.println(parser.getParam("eee"));
        System.out.println(parser.getParam("fff"));
        System.out.println(parser.getParams("fff"));
        System.out.println("goodsId=" + parser.getParams("goodsId"));

        // update params
        parser.removeParams("eee");
        parser.addParam("ggg", "444");
        parser.updateParams("fff", "555");

        System.out.println("parser.getQuery() == " + parser.getQuery());

        LinkedHashMap<String, List<String>> stringListLinkedHashMap = parser.parseQueryString(parser.getQuery());

        Iterator<Map.Entry<String, List<String>>> iterator= stringListLinkedHashMap.entrySet().iterator();

        while(iterator.hasNext())
        {
            Map.Entry entry = iterator.next();
            List<String> value = (List<String>) entry.getValue();
            for (int i = 0; i < value.size(); i++) {
                System.out.println(entry.getKey()+":"+ value.get(i));
            }
        }

        // create query string
        System.out.println(parser.createQueryString());

        // full url
        System.out.println(parser.toString());
    }

}
