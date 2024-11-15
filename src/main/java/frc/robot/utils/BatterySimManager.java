package frc.robot.utils;

import java.util.ArrayList;

import org.littletonrobotics.junction.Logger;

import edu.wpi.first.units.Units;
import edu.wpi.first.units.measure.Current;
import edu.wpi.first.units.measure.Voltage;
import edu.wpi.first.wpilibj.simulation.BatterySim;
import edu.wpi.first.wpilibj.simulation.RoboRioSim;
import frc.lib.VirtualSubsystem;
import frc.robot.Constants;
import frc.robot.Constants.Mode;

public class BatterySimManager extends VirtualSubsystem {

    private static final ArrayList<Current> currents = new ArrayList<>();
    private static BatterySimManager instance;

    private BatterySimManager() {}

    static {
        if (Constants.getMode().equals(Mode.SIM)) {
            instance = new BatterySimManager();
        }
    }

    @Override
    public void periodic() {
        double[] currentsArray = currents.stream()
            .mapToDouble(current -> current.in(Units.Amps))
            .toArray();
        
        double newVoltage = BatterySim.calculateDefaultBatteryLoadedVoltage(currentsArray);
        RoboRioSim.setVInVoltage(newVoltage);
        Logger.recordOutput("SimBatteryVoltage", newVoltage);
        currents.clear();
    }

    public static void addCurrent(Current current) {
        currents.add(current);
    }

    public static Voltage getBatteryVoltage() {
        return Units.Volts.of(RoboRioSim.getVInVoltage());
    }


}
