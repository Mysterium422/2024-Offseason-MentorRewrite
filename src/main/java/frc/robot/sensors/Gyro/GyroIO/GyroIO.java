package frc.robot.sensors.Gyro.GyroIO;

import edu.wpi.first.units.Units;
import edu.wpi.first.units.measure.Angle;
import frc.robot.utils.AllianceFlipUtil;
import org.littletonrobotics.junction.AutoLog;

public interface GyroIO {
  @AutoLog
  public class GyroIOInputs {
    Angle yaw = Units.Radians.of(0);
  }

  default void resetHeading() {
    if (AllianceFlipUtil.shouldFlip()) {
      resetHeading(Units.Degrees.of(0));
    } else {
      resetHeading(Units.Degrees.of(180));
    }
  }

  public void resetHeading(Angle heading);

  public void updateInputs(GyroIOInputs inputs);
}
