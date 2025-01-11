package frc.robot.subsystems.Climber.ClimberIO;

import edu.wpi.first.units.measure.Distance;
import frc.lib.g3.MotorIOInputsAutoLogged;
import org.littletonrobotics.junction.AutoLog;

public interface ClimberIO {

  @AutoLog
  public class ClimberIOInputs {
    public Distance leftPosition;
    public Distance rightPosition;
  }

  public default void setPower(double rightPower, double leftPower) {}

  public default void updateInputs(
      ClimberIOInputs inputs, MotorIOInputsAutoLogged left, MotorIOInputsAutoLogged right) {}
}
