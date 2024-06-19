package pl.lodz.uni.core.service;

import oshi.SystemInfo;
import oshi.hardware.GlobalMemory;
import oshi.hardware.HardwareAbstractionLayer;

public class MemoryService implements IMemoryService {

    private final SystemInfo systemInfo;
    private final HardwareAbstractionLayer hardware;

    public MemoryService() {
        systemInfo = new SystemInfo();
        hardware = systemInfo.getHardware();
    }

    @Override
    public double getRamUsageInPercents() {
        GlobalMemory memory = hardware.getMemory();
        long totalMemory = memory.getTotal();
        long availableMemory = memory.getAvailable();
        return ((double) (totalMemory - availableMemory) / totalMemory) * 100;
    }

    @Override
    public double getRamInstalledInGigabytes() {
        GlobalMemory memory = hardware.getMemory();
        return (double) memory.getTotal() / (1024 * 1024 * 1024);
    }

    @Override
    public double getRamUsedInGigabytes() {
        GlobalMemory memory = hardware.getMemory();
        long totalMemory = memory.getTotal();
        long availableMemory = memory.getAvailable();
        return (double) (totalMemory - availableMemory) / (1024 * 1024 * 1024);
    }
}
