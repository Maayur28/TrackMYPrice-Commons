package io.github.maayur28.utils;

import java.util.regex.Pattern;

public final class Constants {
    private Constants() {
    }

    public static final String CONST_REQUEST_ID = "requestId";
    public static final String IP_ADDRESS = "ipAddress";
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
    public static final String CONST_PROCESSED_PRODUCT_COUNT = "PROCESSED_PRODUCT_COUNT";
    public static final String CONST_PRIORITY_INDICES = "PRIORITY_INDICES_";
    public static final String CONST_BROADCAST_MESSAGE_ROUTING_KEY = "broadcastMessage";
    public static final String CONST_SEND_MESSAGE_ROUTING_KEY = "sendMessage";
    public static final Pattern DOMAIN_PATTERN = Pattern.compile("://([^/]+)");
    public static final String CONST_SCRAP_API_URL = "https://api.scrapingant.com/v2/usage?x-api-key=";
    public static final String CONST_DEALS_OF_THE_DAY = "DEALSOFTHEDAY";
    public static final String CONST_SEARCH_URL = "https://www.trackmyprice.in/dashboard/search?url=";
    public static final String CONST_AWS_SQS_URL = "https://sqs.ap-south-1.amazonaws.com/";
    public static final String CONST_AWS_DYNAMODB_ENDPOINT = "https://dynamodb.ap-south-1.amazonaws.com";
    public static final String CONST_TMYP_URL = "https://tmyp.in";
    public static final String CONST_DEFAULT_TIME_ZONE = "Asia/Kolkata";
    public static final String CONST_RABBIT_MQ_NOTIFICATION_EXCHANGE = "notification";
    public static final String CONST_RABBIT_MQ_FEED_SCHEDULER_EXCHANGE = "feedscheduler";
    public static final String CONST_TOTAL_PRODUCT_COUNT = "TOTAL_PRODUCT_COUNT";
}
