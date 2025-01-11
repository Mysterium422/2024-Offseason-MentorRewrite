package frc.robot.subsystems.Intake.Rollers;

import edu.wpi.first.units.measure.Angle;
import frc.lib.g3.MotorIOInputs;
import org.littletonrobotics.junction.AutoLog;

public interface RollerIO {
  @AutoLog
  public class RollerIOInputs {
    public Angle externalPosition;
    public Angle internalPosition;
  }

  public default void setPower(double power) {}
  ;

  public default void updateInputs(
      RollerIOInputs inputs, MotorIOInputs external, MotorIOInputs internal) {}
  ;
}
