package pl.lodz.uni.core.service;

import oshi.SystemInfo;
import oshi.hardware.HardwareAbstractionLayer;
import oshi.hardware.Sensors;
import pl.lodz.uni.core.controller.Reporter;

public class FanService implements IFanService, Reporter {

    private final SystemInfo systemInfo;
    private final HardwareAbstractionLayer hardware;

    public FanService() {
        systemInfo = new SystemInfo();
        hardware = systemInfo.getHardware();
    }

    @Override
    public int[] getFansSpeedInRPM() {
        Sensors sensors = hardware.getSensors();
        int[] fanSpeeds = sensors.getFanSpeeds();
        return fanSpeeds != null ? fanSpeeds : new int[0]; // Return an empty array if fan speeds are not available
    }

    @Override
    public int getAmountOfFans() {
        Sensors sensors = hardware.getSensors();
        int[] fanSpeeds = sensors.getFanSpeeds();
        return fanSpeeds != null ? fanSpeeds.length : 0;
    }

    @Override
    public String report() {
        int amountOfFans = getAmountOfFans();
        int[] fanSpeeds = getFansSpeedInRPM();

        StringBuilder reportBuilder = new StringBuilder();
        reportBuilder.append("Fan Service Report:\n");
        for (int i = 0; i < amountOfFans; i++) {
            reportBuilder.append("Fan ").append(i + 1).append(" Speed: ").append(fanSpeeds[i]).append(" RPM\n");
        }
        return reportBuilder.toString();
    }
}
