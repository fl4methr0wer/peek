package pl.lodz.uni.core;

public interface SystemUsageService {
    double getProcessorUsageInPercents();
    double getProcessorTemp();
    double getRamUsageInPercents();
    double getRamInstalledInGigabytes();
    double getRamUsedInGigabytes();
    int[] getFansSpeedInRPM();
    int getAmountOfFans();
}


