package frc.robot.sensors.Limelight.LimelightIO;

import frc.lib.LimelightHelpers;
import frc.lib.LimelightHelpers.LimelightResults;

public abstract class LimelightIO<T> {
  private final String name;

  protected LimelightIO(String name) {
    this.name = name;
  }

  protected LimelightResults getRawResults() {
    return LimelightHelpers.getLatestResults(name);
  }

  public abstract T[] getAllTargets();

  public T getFirstTarget() {
    T[] targets = getAllTargets();
    if (targets == null || targets.length == 0) return null;
    return targets[0];
  }
}
