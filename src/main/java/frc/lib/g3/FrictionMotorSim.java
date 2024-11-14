package frc.lib.g3;

import java.sql.Driver;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.system.plant.DCMotor;
import edu.wpi.first.units.Units;
import edu.wpi.first.units.measure.Angle;
import edu.wpi.first.units.measure.AngularAcceleration;
import edu.wpi.first.units.measure.AngularVelocity;
import edu.wpi.first.units.measure.Current;
import edu.wpi.first.units.measure.MomentOfInertia;
import edu.wpi.first.units.measure.Time;
import edu.wpi.first.units.measure.Torque;
import edu.wpi.first.units.measure.Voltage;
import edu.wpi.first.wpilibj.simulation.BatterySim;
import edu.wpi.first.wpilibj.simulation.RoboRioSim;
import frc.robot.utils.BatterySimManager;
import lombok.Getter;

public class FrictionMotorSim {
    private final DCMotor motor;
    @Getter private AngularVelocity velocity; // Current velocity (rad/s)
    @Getter private Angle position; // Current position (rad)

    private final MomentOfInertia moi;
    private final double gearing;
    private final double frictionStatic; // Static friction torque (Nm)
    private final double frictionDynamic; // Dynamic friction coefficient (Nm/(rad/s))
    private final boolean brakeMode = true;

    @Getter private Voltage inputVoltage = Units.Volts.of(0);
    @Getter private Voltage outputVoltage = Units.Volts.of(0);
    @Getter private Current current = Units.Amps.of(0);


    public FrictionMotorSim(DCMotor motor, MomentOfInertia moi, double gearing) {
        this(motor, moi, gearing, motor.stallTorqueNewtonMeters * 0.02, 0.001);
    }

    public FrictionMotorSim(DCMotor motor, MomentOfInertia moi, double gearing, double frictionStatic, double frictionDynamic) {
        this.motor = motor;
        this.moi = moi.times(gearing * gearing);
        this.gearing = gearing;
        this.frictionStatic = frictionStatic;
        this.frictionDynamic = frictionDynamic;
        this.velocity = Units.RadiansPerSecond.of(0);
        this.position = Units.Radians.of(0);
    }

    public void setVoltage(Voltage input) {
        inputVoltage = input;
    }

    public void update(Time dt) {
        outputVoltage = Units.Volts.of(MathUtil.clamp(inputVoltage.in(Units.Volts), -RoboRioSim.getVInVoltage(), RoboRioSim.getVInVoltage()));
        current = Units.Amps.of(motor.getCurrent(velocity.in(Units.RadiansPerSecond), outputVoltage.in(Units.Volts)));
        BatterySimManager.getInstance().addCurrent(current);

        Torque motorTorque = Units.NewtonMeters.of(motor.getTorque(current.in(Units.Amps)));
        Torque frictionTorque = calculateFrictionTorque(motorTorque);
        Torque netTorque = motorTorque.minus(frictionTorque);
        AngularAcceleration acceleration = Units.RadiansPerSecondPerSecond.of(netTorque.in(Units.NewtonMeters) * gearing / moi.in(Units.KilogramSquareMeters));
        velocity = velocity.plus(acceleration.times(dt));
        position = position.plus(velocity.times(dt));
    }

    private Torque calculateFrictionTorque(Torque motorTorque) {
        if (Math.abs(velocity.in(Units.RadiansPerSecond)) < 1e-1) {
            velocity = Units.RadiansPerSecond.of(0);
        }

        if (Math.abs(velocity.in(Units.RadiansPerSecond)) < 3) {
            if (brakeMode) {
                return Units.NewtonMeters.of(frictionDynamic * 10 * gearing * velocity.in(Units.RadiansPerSecond));
            }
            return Units.NewtonMeters.of(frictionDynamic * velocity.in(Units.RadiansPerSecond));
        }

        if (Math.abs(inputVoltage.in(Units.Volts)) < 1e-4) {
            if (brakeMode) {
                return Units.NewtonMeters.of(frictionDynamic * 10 * gearing * velocity.in(Units.RadiansPerSecond));
            }
            return Units.NewtonMeters.of(frictionDynamic * 3 * velocity.in(Units.RadiansPerSecond));    
        }

        return Units.NewtonMeters.of(frictionDynamic * velocity.in(Units.RadiansPerSecond));
    }
}
