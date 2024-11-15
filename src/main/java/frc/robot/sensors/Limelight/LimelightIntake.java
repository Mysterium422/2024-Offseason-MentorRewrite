package frc.robot.sensors.Limelight;

import edu.wpi.first.units.Units;
import edu.wpi.first.units.measure.Angle;
import edu.wpi.first.wpilibj.Timer;
import frc.lib.VirtualSubsystem;
import frc.robot.sensors.Limelight.LimelightIO.LimelightIODetector;
import org.littletonrobotics.junction.Logger;

public class LimelightIntake extends VirtualSubsystem {
  private final LimelightIODetector limelightIO;

  private double lastKnownTx;
  private double lastKnownTxTime;

  public LimelightIntake(LimelightIODetector limelightIO) {
    this.limelightIO = limelightIO;
  }

  @Override
  public void periodic() {
    double tx = limelightIO.getFirstTarget().tx;
    if (tx != 0) {
      lastKnownTx = tx;
      lastKnownTxTime = Timer.getFPGATimestamp();
    }

    Logger.recordOutput(getName() + "/isValidTarget", isValidTarget());
    Logger.recordOutput(getName() + "/horizontalOffset", getHorizontalOffset());
    Logger.recordOutput(getName() + "/lastDetectionTime", lastKnownTxTime);
  }

  public boolean isValidTarget() {
    return Timer.getFPGATimestamp() - lastKnownTxTime > 0.5;
  }

  public Angle getHorizontalOffset() {
    return Units.Degrees.of(lastKnownTx);
  }
}
