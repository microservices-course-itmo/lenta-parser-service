package com.wine.to.up.lenta.service.components;

import com.wine.to.up.commonlib.metrics.CommonMetricsCollector;
import io.micrometer.core.instrument.Metrics;
import io.prometheus.client.Counter;
import io.prometheus.client.Summary;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * This Class expose methods for recording specific metrics
 * It changes metrics of Micrometer and Prometheus simultaneously
 * Micrometer's metrics exposed at /actuator/prometheus
 * Prometheus' metrics exposed at /metrics-prometheus
 *
 */
@Component
public class LentaServiceMetricsCollector extends CommonMetricsCollector {

    private static final String SERVICE_NAME = "lenta_parser_service";

    private static final String PARSE_SITE_JSON = "parse_site_json";
    private static final String PARSE_SITE_CSV = "parse_site_csv";
    private static final String PRODUCT_LIST_JOB = "product_list_job";
    private static final String PARSING_COMPLETE_COUNTER = "parsing_complete_total";
    private static final String PARSING_STARTED_COUNTER = "parsing_started_total";
    private static final String WINES_PUBLISHED_TO_KAFKA_COUNT = "wines_published_to_kafka_count";
    private static final String WINE_PAGE_FETCHING_DURATION = "wine_page_fetching_duration_seconds";
    private static final String WINE_PAGE_PARSING_DURATION_SUMMARY = "wine_page_parsing_duration_seconds";
    private static final String WINE_DETAILS_PARSING_DURATION_SUMMARY = "wine_details_parsing_duration_seconds";
    private static final String WINE_DETAILS_FETCHING_DURATION_SUMMARY = "wine_details_fetching_duration_seconds";

    private static final String PARSING_COMPLETE_STATUS_TAG = "status";


    public LentaServiceMetricsCollector() {
        super(SERVICE_NAME);
    }

    private static final Counter prometheusParsingStartedCounter = Counter.build()
            .name(PARSING_STARTED_COUNTER)
            .help("Total number of parsing processes ever started")
            .register();

    private static final Counter prometheusParsingCompleteCounter = Counter.build()
            .name(PARSING_COMPLETE_COUNTER)
            .help("Total number of parsing processes ever completed")
            .labelNames(PARSING_COMPLETE_STATUS_TAG)
            .register();

    private static final Summary productExportList = Summary.build()
            .name(PRODUCT_LIST_JOB)
            .help("ExportProductListJob execution time")
            .register();

    private static final Summary parseSiteSummary = Summary.build()
            .name(PARSE_SITE_JSON)
            .help("/json execution time")
            .register();

    private static final Summary parseSiteCsvSummary = Summary.build()
            .name(PARSE_SITE_CSV)
            .help("/csv execution time")
            .register();

    private static final Summary prometheusWinePageFetchingDurationSummary = Summary.build()
            .name(WINE_PAGE_FETCHING_DURATION)
            .help("wine page fetching execution time")
            .register();

    private static final Summary winePageParsingDurationSummary = Summary.build()
            .name(WINE_PAGE_PARSING_DURATION_SUMMARY)
            .help("wine page parsing execution time")
            .register();

    private static final Summary wineDetailsFetchingDurationSummary = Summary.build()
            .name(WINE_DETAILS_FETCHING_DURATION_SUMMARY)
            .help("wine details fetching execution time")
            .register();

    private static final Summary wineDetailsParsingDurationSummary = Summary.build()
            .name(WINE_DETAILS_PARSING_DURATION_SUMMARY)
            .help("wine details parsing execution time")
            .register();

    public void parseSiteCsv(long time) {
        Metrics.timer(PARSE_SITE_CSV).record(time, TimeUnit.MILLISECONDS);
        parseSiteCsvSummary.observe(time);
    }

    public void productListJob(long time) {
        Metrics.timer(PRODUCT_LIST_JOB).record(time, TimeUnit.MILLISECONDS);
        productExportList.observe(time);
    }

    public void parseSiteJson(long time) {
        Metrics.timer(PARSE_SITE_JSON).record(time, TimeUnit.MILLISECONDS);
        parseSiteSummary.observe(time);
    }

    public void timeWinePageFetchingDuration(long time, String cityName) {
        long milliTime = TimeUnit.NANOSECONDS.toMillis(time);
        prometheusWinePageFetchingDurationSummary.observe(milliTime);
        Metrics.summary(WINE_PAGE_FETCHING_DURATION, cityName).record(milliTime);
    }

    public void parsingPageDuration(long time, String cityName){
        long milliTime = TimeUnit.NANOSECONDS.toMillis(time);
        winePageParsingDurationSummary.labels("city", cityName).observe(milliTime);
        Metrics.summary(WINE_PAGE_PARSING_DURATION_SUMMARY, cityName).record(milliTime);

    }

    public void fetchingDetailsDuration(long time, String cityName) {
        long milliTime = TimeUnit.NANOSECONDS.toMillis(time);
        wineDetailsFetchingDurationSummary.labels("city", cityName).observe(milliTime);
        Metrics.summary(WINE_DETAILS_FETCHING_DURATION_SUMMARY, cityName).record(milliTime);

    }

    public void parsingDetailsDuration(long time, String cityName){
        long milliTime = TimeUnit.NANOSECONDS.toMillis(time);
        wineDetailsParsingDurationSummary.labels("city", cityName).observe(milliTime);
        Metrics.summary(WINE_DETAILS_PARSING_DURATION_SUMMARY, cityName).record(milliTime);
    }

    public void countParsingComplete(String status, String cityName) {
        Metrics.counter(PARSING_COMPLETE_COUNTER, PARSING_COMPLETE_STATUS_TAG, status, "city", cityName).increment();
        prometheusParsingCompleteCounter.labels(status, cityName).inc();
    }

    public void incParsingStarted(String cityName) {
        Metrics.counter(PARSING_STARTED_COUNTER, "city", cityName).increment();
        prometheusParsingStartedCounter.inc();
    }

    public void incWinesSentToKafka(int count, String cityName) {
        Metrics.counter(WINES_PUBLISHED_TO_KAFKA_COUNT, "city", cityName).increment(count);

    }

}
