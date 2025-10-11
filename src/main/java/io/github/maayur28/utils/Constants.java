package io.github.maayur28.utils;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

public final class Constants {
    public static final List<String> STORES = Arrays.asList("Amazon", "Flipkart");
    public static final String CONST_REQUEST_ID = "requestId";
    public static final String IP_ADDRESS = "ipAddress";
    public static final String CONST_AMAZON = "AMAZON";
    public static final String CONST_FLIPKART = "FLIPKART";
    public static final String CONST_AMAZON_URL_INITIAL = "https://www.amazon.in/dp/";
    public static final String CONST_FLIPKART_URL_INITIAL = "https://www.flipkart.com";
    public static final String CONST_URL_PARAM = "&proxy_country=IN&browser=false&url=";
    public static final String CONST_SCRAP_URI = "https://api.scrapingant.com/v2/general?x-api-key=";
    public static final String CONST_ADVANCED_SCRAP_URI = "https://api.scrapingant.com/v2/extended?x-api-key=";
    public static final String CONST_PROCESSED_PRODUCT_COUNT = "PROCESSED_PRODUCT_COUNT";
    public static final String CONST_PRIORITY_INDICES = "PRIORITY_INDICES_";
    public static final Pattern DOMAIN_PATTERN = Pattern.compile("://([^/]+)");
    public static final String CONST_SCRAP_API_URL = "https://api.scrapingant.com/v2/usage?x-api-key=";
    public static final String CONST_DEALS_OF_THE_DAY = "DEALSOFTHEDAY";
    public static final String CONST_SEARCH_URL = "https://www.trackmyprice.in/dashboard/search?url=";
    public static final String CONST_AWS_SQS_URL = "https://sqs.ap-south-1.amazonaws.com/";
    public static final String CONST_TMYP_URL = "https://tmyp.in";
    public static final String CONST_DEFAULT_TIME_ZONE = "Asia/Kolkata";
    public static final String CONST_TOTAL_PRODUCT_COUNT = "TOTAL_PRODUCT_COUNT";
    public static final String CONST_PRIORITY_THREE = "3";
    public static final String CONST_PRIORITY_TWO = "2";
    public static final String CONST_PARAMETER_PREFIX = "PARAMETER_PREFIX";
    public static final String CONST_LOOKUP_ID = "lookup_";
    static final String[] HEADERS_TO_TRY = {
            "X-Forwarded-For",
            "Proxy-Client-IP",
            "WL-Proxy-Client-IP",
            "HTTP_CLIENT_IP",
            "HTTP_X_FORWARDED_FOR"
    };
    public static final String CONST_SELF_SCRAP_FREE_LIST_TEMP_KEY = "%s:FreeSelfScrapTemp";
    public static final String CONST_FAILED_PRODUCTS_BY_RETRY_COUNTER_KEY = "failedProductsByRetryCount"; // one Redis key

    private Constants() {
    }

    public static final class RabbitMQEXCHANGE {
        public static final String CONST_RABBIT_MQ_NOTIFICATION_EXCHANGE = "notification";
        public static final String CONST_RABBIT_MQ_FEED_SERVICE_EXCHANGE = "feedservice";
        public static final String CONST_RABBIT_MQ_SCRAPPER_EXCHANGE = "scrapper";
        private RabbitMQEXCHANGE() {
        }
    }

    public static final class RoutingKeys {
        public static final String CONST_CACHE_CLEAR_ROUTING_KEY = "cacheClear";
        public static final String CONST_FEED_SCHEDULER_INDEX_ROUTING_KEY = "feedSchedulerIndex";
        public static final String CONST_PROCESS_FAILED_PRODUCT_DETAILS_ROUTING_KEY = "processFailedProductDetails";
        public static final String CONST_PROCESS_PRODUCT_DETAILS_ROUTING_KEY = "processProductDetails";
        public static final String CONST_SAVE_IP_INFO_DETAILS_ROUTING_KEY = "saveIpInfoDetails";
        public static final String CONST_SAVE_PRICE_HISTORY_ROUTING_KEY = "savePriceHistory";
        public static final String CONST_SAVE_PRODUCT_DETAILS_ROUTING_KEY = "saveProductDetails";
        public static final String CONST_BROADCAST_MESSAGE_ROUTING_KEY = "broadcastMessage";
        public static final String CONST_CONTACT_US_ROUTING_KEY = "contactUsMail";
        public static final String CONST_FORGET_PASSWORD_MAIL_ROUTING_KEY = "forgetPasswordMail";
        public static final String CONST_SEND_MESSAGE_ROUTING_KEY = "sendMessage";
        public static final String CONST_PRICE_DROPPED_NOTIFICATION_ROUTING_KEY = "sendPriceDroppedNotification";
        public static final String CONST_SIGN_UP_VERIFICATION_MAIL_ROUTING_KEY = "signUpVerificationMail";
        public static final String CONST_VERIFICATION_SUCCESS_MAIL_ROUTING_KEY = "verificationSuccessMail";
        public static final String CONST_SIGN_IN_OTP_VERIFICATION_MAIL_ROUTING_KEY = "signInOTPVerificationMail";
        public static final String CONST_SCRAP_PRODUCT_DETAILS = "scrapProductDetails";
        public static final String CONST_AMAZON_SCRAP_PRODUCT_DETAILS = "amazon_jobs";
        public static final String CONST_FLIPKART_SCRAP_PRODUCT_DETAILS = "flipkart_jobs";
        public static final String CONST_SPIN_UP_SELF_SCRAPPER_TEMP_SERVER_ROUTING_KEY = "SpinUPSelfScrapperTempServer";
        private RoutingKeys() {
        }
    }


}
