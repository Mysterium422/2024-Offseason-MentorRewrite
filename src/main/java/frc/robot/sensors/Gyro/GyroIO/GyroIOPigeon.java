package frc.robot.sensors.Gyro.GyroIO;

import com.ctre.phoenix6.hardware.Pigeon2;
import edu.wpi.first.units.measure.Angle;

public class GyroIOPigeon implements GyroIO {

  private final Pigeon2 m_pigeon;

  public GyroIOPigeon(int deviceId, String canbus) {
    this(new Pigeon2(deviceId, canbus));
  }

  public GyroIOPigeon(Pigeon2 pigeon) {
    m_pigeon = pigeon;
  }

  @Override
  public void resetHeading(Angle heading) {
    m_pigeon.setYaw(heading);
  }

  @Override
  public void updateInputs(GyroIOInputs inputs) {
    inputs.yaw = m_pigeon.getYaw().getValue();
  }
}
