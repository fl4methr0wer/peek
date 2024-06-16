package pl.lodz.uni;

import pl.lodz.uni.core.SystemUsageServiceImpl;
import pl.lodz.uni.ui.Window;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        SystemUsageServiceImpl service = new SystemUsageServiceImpl();
        System.out.println("Processor Usage: " + service.getProcessorUsageInPercents() + " %");
        System.out.println("RAM Usage: " + service.getRamUsageInPercents() + " %");
        System.out.println("Installed RAM: " + service.getRamInstalledInGigabytes() + " GB");
        System.out.println("Used RAM: " + service.getRamUsedInGigabytes() + " GB");
        System.out.print("Fan Speeds: ");
        for (int speed : service.getFansSpeedInRPM()) {
            System.out.print(speed + " RPM ");
        }

        JFrame widow = new Window("peek");
    }
}