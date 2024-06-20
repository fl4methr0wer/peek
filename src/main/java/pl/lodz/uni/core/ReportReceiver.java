package pl.lodz.uni.core;

import pl.lodz.uni.core.controller.Reporter;

import java.util.List;

public interface ReportReceiver {
    void accept(List<Reporter> reporters);
}
