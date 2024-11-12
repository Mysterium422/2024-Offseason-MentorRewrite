package frc.robot.sensors.Limelight;

import edu.wpi.first.units.measure.Angle;
import edu.wpi.first.units.measure.Distance;
import frc.lib.VirtualSubsystem;
import frc.robot.FieldConstants.Tags;
import frc.robot.sensors.Limelight.LimelightIO.LimelightIOFiducials;

public class LimelightShooter extends VirtualSubsystem {
    private final LimelightIOFiducials limelightIO;

    public LimelightShooter(LimelightIOFiducials limelightIO) {
        this.limelightIO = limelightIO;
    }

    @Override
    public void periodic() {

    }

    public boolean hasSpeakerTarget() {
        return limelightIO.hasTarget(Tags.SPEAKER_CENTER.getId());
    }

    public Angle getHorizontalOffsetToSpeaker() {
        return limelightIO.getHorizontalOffset(Tags.SPEAKER_CENTER.getId());
    }

    public Distance getDistanceToSpeaker() {
        return limelightIO.getDistance(Tags.SPEAKER_CENTER.getId());
    }



}
