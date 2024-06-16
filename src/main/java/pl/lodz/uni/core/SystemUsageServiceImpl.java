package pl.lodz.uni.core;

import oshi.SystemInfo;
import oshi.hardware.CentralProcessor;
import oshi.hardware.GlobalMemory;
import oshi.hardware.HardwareAbstractionLayer;
import oshi.hardware.Sensors;
import oshi.util.Util;

public class SystemUsageServiceImpl implements SystemUsageService {

    private final SystemInfo systemInfo;
    private final HardwareAbstractionLayer hardware;

    public SystemUsageServiceImpl() {
        systemInfo = new SystemInfo();
        hardware = systemInfo.getHardware();
    }

    @Override
    public double getProcessorUsageInPercents() {
        CentralProcessor processor = hardware.getProcessor();
        long[] prevTicks = processor.getSystemCpuLoadTicks();
        Util.sleep(1000); // Wait a second to get an average reading
        long[] ticks = processor.getSystemCpuLoadTicks();
        return processor.getSystemCpuLoadBetweenTicks(prevTicks) * 100;
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
    public int[] getFansSpeedInRPM() {
        Sensors sensors = hardware.getSensors();
        int[] fanSpeeds = sensors.getFanSpeeds();
        return fanSpeeds != null ? fanSpeeds : new int[0]; // Return an empty array if fan speeds are not available
    }
}
