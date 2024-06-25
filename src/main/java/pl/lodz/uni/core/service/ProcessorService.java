package pl.lodz.uni.core.service;

import oshi.SystemInfo;
import oshi.hardware.CentralProcessor;
import oshi.hardware.HardwareAbstractionLayer;
import oshi.util.Util;
import pl.lodz.uni.core.controller.Reporter;

public class ProcessorService implements IProcessorService, Reporter {

    private final SystemInfo systemInfo;
    private final HardwareAbstractionLayer hardware;

    public ProcessorService() {
        systemInfo = new SystemInfo();
        hardware = systemInfo.getHardware();
    }

    @Override
    public double getProcessorUsageInPercents() {
        CentralProcessor processor = hardware.getProcessor();
        long[] prevTicks = processor.getSystemCpuLoadTicks();
        Util.sleep(1000); // wait to get an average reading
        return processor.getSystemCpuLoadBetweenTicks(prevTicks) * 100;
    }

    @Override
    public double getProcessorTemp() {
        return hardware.getSensors().getCpuTemperature();
    }

    @Override
    public String report() {
        return String.format("Processor usage: %.2f%%", getProcessorUsageInPercents());
    }
}
