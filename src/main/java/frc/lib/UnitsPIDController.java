package frc.lib;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.units.Unit;

public class UnitsPIDController<I extends Unit, O extends Unit>  extends PIDController {

    public UnitsPIDController(double kp, double ki, double kd) {
        super(kp, ki, kd);
    }

    public UnitsPIDController(double kp, double ki, double kd, double period) {
        super(kp, ki, kd, period);
    }
}
