package frc.robot.sensors.Limelight.LimelightIO;

import frc.lib.LimelightHelpers;
import frc.lib.LimelightHelpers.LimelightResults;

public abstract class LimelightIO {
  private final String name;

  protected LimelightIO(String name) {
    this.name = name;
  }

  protected LimelightResults getRawResults() {
    return LimelightHelpers.getLatestResults(name);
  }
}
