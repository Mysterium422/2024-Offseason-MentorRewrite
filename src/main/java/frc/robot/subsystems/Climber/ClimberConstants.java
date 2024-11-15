package frc.robot.subsystems.Climber;

import edu.wpi.first.units.Units;
import edu.wpi.first.units.measure.Distance;

public final class ClimberConstants {
    private ClimberConstants() {}

    public static final int portLeftClimberID = 50;
    public static final int portRightClimberID = 51;

    public static final double gearing = 25;
    public static final Distance maxHeight = Units.Meters.of(1);
    public static final Distance pulleyRadius = Units.Inches.of(2);
}
