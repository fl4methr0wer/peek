package pl.lodz.uni.core.service;

import oshi.SystemInfo;
import oshi.hardware.GlobalMemory;
import oshi.hardware.HardwareAbstractionLayer;
import pl.lodz.uni.core.controller.Reporter;

public class MemoryService implements IMemoryService, Reporter {

    private final SystemInfo systemInfo;
    private final HardwareAbstractionLayer hardware;
    private final long BYTES_IN_GIGABYTE = 1024 * 1024 * 1024;

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
        return (double) memory.getTotal() / BYTES_IN_GIGABYTE;
    }

    @Override
    public double getRamUsedInGigabytes() {
        GlobalMemory memory = hardware.getMemory();
        long totalMemory = memory.getTotal();
        long availableMemory = memory.getAvailable();
        return (double) (totalMemory - availableMemory) / BYTES_IN_GIGABYTE;
    }

    @Override
    public String report() {
        GlobalMemory memory = hardware.getMemory();

        StringBuilder reportBuilder = new StringBuilder();
        reportBuilder.append("Memory Service Report:\n");
        reportBuilder.append("Total RAM: ").append(memory.getTotal() /BYTES_IN_GIGABYTE).append(" GB\n");
        reportBuilder.append("Available RAM: ").append(memory.getAvailable() / BYTES_IN_GIGABYTE).append(" GB\n");
        reportBuilder.append("Used RAM: ").append((memory.getTotal() - memory.getAvailable()) / BYTES_IN_GIGABYTE).append(" GB\n");
        reportBuilder.append("RAM Usage: ").append(String.format("%.2f %%\n", getRamUsageInPercents()));

        return reportBuilder.toString();
    }
}
