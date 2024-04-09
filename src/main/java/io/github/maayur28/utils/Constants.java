package io.github.maayur28.utils;

public final class Constants {
    private Constants() {
    }

    public static final String CONST_REQUEST_ID = "requestId";
    static final String[] HEADERS_TO_TRY = {
            "X-Forwarded-For",
            "Proxy-Client-IP",
            "WL-Proxy-Client-IP",
            "HTTP_CLIENT_IP",
            "HTTP_X_FORWARDED_FOR"
    };
    public static final String CONST_AMAZON = "AMAZON";
    public static final String CONST_FLIPKART = "FLIPKART";
    public static final String CONST_AMAZON_URL_INITIAL = "https://www.amazon.in/dp/";
    public static final String CONST_FLIPKART_URL_INITIAL = "https://www.flipkart.com";
    public static final String CONST_URL_PARAM = "&proxy_country=IN&browser=false&url=";
    public static final String CONST_SCRAP_URI = "https://api.scrapingant.com/v2/general?x-api-key=";
    public static final String CONST_ADVANCED_SCRAP_URI = "https://api.scrapingant.com/v2/extended?x-api-key=";
}
