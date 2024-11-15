package frc.robot.sensors.Limelight.LimelightIO;

import frc.lib.LimelightHelpers.LimelightTarget_Detector;

public class LimelightIODetector extends LimelightIO<LimelightTarget_Detector> {
  public LimelightIODetector(String name) {
    super(name);
  }

  public LimelightTarget_Detector[] getAllTargets() {
    return getRawResults().targets_Detector;
  }
}
