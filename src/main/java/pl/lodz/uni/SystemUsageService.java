package pl.lodz.uni;

public interface SystemUsageService {
    double getProcessorUsageInPercents();
    double getRamUsageInPercents();
    double getRamInstalledInGigabytes();
    double getRamUsedInGigabytes();
    int[] getFansSpeedInRPM();
}

