package frc.robot.sensors.Limelight.LimelightIO;

import frc.lib.LimelightHelpers.LimelightTarget_Detector;

public class LimelightIODetector extends LimelightIO {
    public LimelightIODetector(String name) {
        super(name);
    }
    
    public LimelightTarget_Detector[] getResults() {
        return getRawResults().targets_Detector;
    }
}
