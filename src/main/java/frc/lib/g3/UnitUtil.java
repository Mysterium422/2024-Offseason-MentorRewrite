package frc.lib.g3;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.units.Measure;
import edu.wpi.first.units.Unit;
import edu.wpi.first.units.Units;
import edu.wpi.first.units.measure.Angle;
import edu.wpi.first.units.measure.Distance;

public class UnitUtil {
    @SuppressWarnings("unchecked")
    public static <U extends Unit, M extends Measure<U>> M clamp(M value, M low, M high) {
        return (M) value.baseUnit().of(MathUtil.clamp(value.baseUnitMagnitude(), low.baseUnitMagnitude(), high.baseUnitMagnitude()));
    }

    public static Distance angleToDistance(Angle position, Distance radius) {
       return Units.Meters.of(position.times(radius).baseUnitMagnitude());
    }

    @SuppressWarnings("unchecked")
    public static <U extends Unit, M extends Measure<U>> M abs(M a) {
        return (M) a.baseUnit().of(Math.abs(a.baseUnitMagnitude()));
    }

    public static Angle reduceAngle(Angle angle) {
        return Units.Degrees.of(angle.in(Units.Degrees) % 360);
    }
}
