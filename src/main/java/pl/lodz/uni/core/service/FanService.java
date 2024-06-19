package pl.lodz.uni.core.service;

import oshi.SystemInfo;
import oshi.hardware.HardwareAbstractionLayer;
import oshi.hardware.Sensors;

public class FanService implements IFanService {

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

}
