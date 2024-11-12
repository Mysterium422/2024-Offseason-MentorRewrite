package frc.robot.sensors.Limelight;

import org.littletonrobotics.junction.AutoLogOutput;
import org.littletonrobotics.junction.Logger;

import edu.wpi.first.units.measure.Angle;
import edu.wpi.first.units.measure.Distance;
import frc.lib.VirtualSubsystem;
import frc.robot.FieldConstants.Tags;
import frc.robot.sensors.Limelight.LimelightIO.LimelightIOFiducials;
import lombok.Getter;
import lombok.extern.java.Log;

public class LimelightShooter extends VirtualSubsystem {
    private final LimelightIOFiducials limelightIO;

    public LimelightShooter(LimelightIOFiducials limelightIO) {
        this.limelightIO = limelightIO;
    }

    @Override
    public void periodic() {

    }

    @AutoLogOutput(key = "{name}/hasSpeakerTarget")
    public boolean hasSpeakerTarget() {
        return limelightIO.hasTarget(Tags.SPEAKER_CENTER.getId());
    }

    @AutoLogOutput(key = "{name}/horizontalOffsetToSpeaker")
    public Angle getHorizontalOffsetToSpeaker() {
        return limelightIO.getHorizontalOffset(Tags.SPEAKER_CENTER.getId());
    }

    @AutoLogOutput(key = "{name}/distanceToSpeaker")
    public Distance getDistanceToSpeaker() {
        return limelightIO.getDistance(Tags.SPEAKER_CENTER.getId());
    }

}
