package frc.robot.sensors.Gyro;

import edu.wpi.first.units.measure.Angle;
import frc.lib.VirtualSubsystem;
import frc.robot.sensors.Gyro.GyroIO.GyroIO;
import frc.robot.sensors.Gyro.GyroIO.GyroIOInputsAutoLogged;
import org.littletonrobotics.junction.Logger;

public class Gyro extends VirtualSubsystem {
  private final GyroIO gyroIO;
  private final GyroIOInputsAutoLogged gyroIOInputs = new GyroIOInputsAutoLogged();

  public Gyro(GyroIO gyroIO) {
    super("Gyro");
    this.gyroIO = gyroIO;
  }

  public void resetHeading() {
    gyroIO.resetHeading();
  }

  public void resetHeading(Angle heading) {
    gyroIO.resetHeading(heading);
  }

  @Override
  public void periodic() {
    gyroIO.updateInputs(gyroIOInputs);
    Logger.processInputs(getName(), gyroIOInputs);
  }
}
