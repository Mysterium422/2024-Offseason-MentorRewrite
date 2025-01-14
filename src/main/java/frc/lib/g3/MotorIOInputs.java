package frc.lib.g3;

import edu.wpi.first.units.measure.Current;
import edu.wpi.first.units.measure.Voltage;
import org.littletonrobotics.junction.AutoLog;

@AutoLog
public class MotorIOInputs {
  public double output;
  public Voltage outputVoltage;
  public boolean isOn;
  public double velocityRPM;
  public double tempFahrenheit;
  public Current currentAmps;
}
