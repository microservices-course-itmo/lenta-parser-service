package com.wine.to.up.lenta.service.components;

import com.wine.to.up.commonlib.metrics.CommonMetricsCollector;
import io.micrometer.core.instrument.Metrics;
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

    public LentaServiceMetricsCollector() {
        super(SERVICE_NAME);
    }

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
}
