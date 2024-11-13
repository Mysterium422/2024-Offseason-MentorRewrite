package frc.lib.g3;

import com.revrobotics.spark.SparkMax;
import edu.wpi.first.units.Units;

public class SparkMaxIO extends SparkMax {
  public SparkMaxIO(int deviceId, MotorType type) {
    super(deviceId, type);
  }

  public void updateInputs(MotorIOInputsAutoLogged inputs) {
    inputs.output = getAppliedOutput();
    inputs.outputVoltage = getBusVoltage() * inputs.output;
    inputs.currentAmps = getOutputCurrent();
    inputs.isOn = Math.abs(inputs.output) > 0.01;
    inputs.tempFahrenheit = Units.Celsius.of(getMotorTemperature()).in(Units.Fahrenheit);
    inputs.velocityRPM = getEncoder().getVelocity();
  }
}
