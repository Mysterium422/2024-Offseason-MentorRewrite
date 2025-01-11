package frc.robot.utils;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.units.Units;
import edu.wpi.first.units.measure.Current;
import edu.wpi.first.units.measure.Voltage;
import edu.wpi.first.wpilibj.RobotState;
import edu.wpi.first.wpilibj.simulation.BatterySim;
import edu.wpi.first.wpilibj.simulation.RoboRioSim;
import frc.lib.VirtualSubsystem;
import frc.robot.Constants;
import frc.robot.Constants.Mode;
import org.littletonrobotics.junction.Logger;

public class BatterySimManager extends VirtualSubsystem {

  private static Current totalCurrent = Units.Amps.zero();
  private static double nominalVoltage = 13;
  private static final double batteryResistance = 0.011;
  private static double batteryCapacity = 18; // In Ah
  private static double remainingCapacity = batteryCapacity;

  private BatterySimManager() {}

  static {
    if (Constants.getMode().equals(Mode.SIM)) {
      new BatterySimManager();
    }
    RoboRioSim.setVInVoltage(nominalVoltage);
  }

  @Override
  public void periodic() {
    double loadVoltage = nominalVoltage - batteryResistance * totalCurrent.in(Units.Amps);
    RoboRioSim.setVInVoltage(loadVoltage);

    double capacityUsed = totalCurrent.in(Units.Amps) * Constants.loopTime.in(Units.Minutes) / 60;
    remainingCapacity -= capacityUsed;

    double newRestingVoltage = calculateVoltage(remainingCapacity / batteryCapacity);
    nominalVoltage = newRestingVoltage;

    BatterySim.calculateDefaultBatteryLoadedVoltage(1);

    Logger.recordOutput(getName() + "/restingVoltage", newRestingVoltage);
    Logger.recordOutput(getName() + "/loadVoltage", loadVoltage);
    Logger.recordOutput(getName() + "/remainingCapacity", remainingCapacity);
    Logger.recordOutput(getName() + "/totalCurrent", totalCurrent);

    totalCurrent = Units.Amps.of(1);
    Current rioEstimate;
    if (RobotState.isDisabled()) {
      rioEstimate = Units.Amps.of(0.4);
    } else {
      rioEstimate = Units.Amps.of(1);
    }
    totalCurrent = totalCurrent.plus(rioEstimate);
  }

  public static void addCurrent(Current current) {
    totalCurrent = totalCurrent.plus(current);
  }

  public static Voltage getBatteryVoltage() {
    return Units.Volts.of(RoboRioSim.getVInVoltage());
  }

  private double calculateVoltage(double soc) {
    soc = MathUtil.clamp(soc, 0, 1);
    if (soc >= 1) {
      return 13;
    } else if (soc > 0.5) {
      return 12 + (soc - 0.5) * 2;
    } else if (soc > 0.2) {
      return 11.5 + (soc - 0.2) * 0.5 / 0.3;
    } else if (soc > 0.05) {
      return 11.0 + (soc - 0.05) * 0.5 / 0.15;
    }
    return 10.5;
  }
}
