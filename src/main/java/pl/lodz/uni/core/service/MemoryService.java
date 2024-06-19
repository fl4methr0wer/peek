package pl.lodz.uni.core.service;

import oshi.SystemInfo;
import oshi.hardware.GlobalMemory;
import oshi.hardware.HardwareAbstractionLayer;
import pl.lodz.uni.core.Reporter;

public class MemoryService implements IMemoryService, Reporter {

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

    @Override
    public String report() {
        GlobalMemory memory = hardware.getMemory();

        StringBuilder reportBuilder = new StringBuilder();
        reportBuilder.append("Memory Service Report:\n");
        reportBuilder.append("Total RAM: ").append(memory.getTotal() / (1024 * 1024 * 1024)).append(" GB\n");
        reportBuilder.append("Available RAM: ").append(memory.getAvailable() / (1024 * 1024 * 1024)).append(" GB\n");
        reportBuilder.append("Used RAM: ").append((memory.getTotal() - memory.getAvailable()) / (1024 * 1024 * 1024)).append(" GB\n");
        reportBuilder.append("RAM Usage: ").append(String.format("%.2f %%\n", getRamUsageInPercents()));

        return reportBuilder.toString();
    }
}
