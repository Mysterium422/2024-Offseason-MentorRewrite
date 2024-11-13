package frc.robot.sensors.Limelight.LimelightIO;

import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.units.Units;
import edu.wpi.first.units.measure.Angle;
import edu.wpi.first.units.measure.Distance;
import frc.lib.LimelightHelpers.LimelightTarget_Fiducial;

public class LimelightIOFiducials extends LimelightIO {
  public LimelightIOFiducials(String name) {
    super(name);
  }

  public LimelightTarget_Fiducial[] getAllTargets() {
    return getRawResults().targets_Fiducials;
  }

  public LimelightTarget_Fiducial getTarget(int fiducialID) {
    LimelightTarget_Fiducial[] targets = getAllTargets();
    for (LimelightTarget_Fiducial target : targets) {
      if ((int) target.fiducialID == fiducialID) {
        return target;
      }
    }
    return null;
  }

  public boolean hasTarget(int fiducialID) {
    return getTarget(fiducialID) != null;
  }

  public Angle getHorizontalOffset(int fiducialID) {
    LimelightTarget_Fiducial target = getTarget(fiducialID);
    if (target == null) {
      return Units.Degrees.of(0);
    }
    return Units.Degrees.of(target.tx);
  }

  public Distance getDistance(int fiducialID) {
    LimelightTarget_Fiducial target = getTarget(fiducialID);
    if (target == null) {
      return Units.Meters.of(0);
    }
    return Units.Meters.of(
        target.getCameraPose_TargetSpace2D().getTranslation().getDistance(new Translation2d()));
  }
}
