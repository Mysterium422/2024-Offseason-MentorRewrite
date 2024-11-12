package frc.robot.sensors.Limelight.LimelightIO;

import frc.lib.LimelightHelpers.LimelightTarget_Detector;
import frc.lib.LimelightHelpers.LimelightTarget_Fiducial;

public class LimelightIODetector extends LimelightIO {
    public LimelightIODetector(String name) {
        super(name);
    }
    
    public LimelightTarget_Detector[] getTargets() {
        return getRawResults().targets_Detector;
    }

    public LimelightTarget_Detector getTarget() {
        return getTargets()[0];
    }
}
