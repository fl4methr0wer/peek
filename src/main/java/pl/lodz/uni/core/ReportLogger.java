package pl.lodz.uni.core;

import java.util.List;

public interface ReportLogger {
    void accept(List<Reporter> reporters);
}
