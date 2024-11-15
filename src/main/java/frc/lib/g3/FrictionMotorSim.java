package frc.lib.g3;

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
import frc.robot.utils.BatterySimManager;
import lombok.Getter;

public class FrictionMotorSim {
    private final UnitDCMotor motor;
    @Getter private AngularVelocity velocity; // Current velocity (rad/s)
    @Getter private Angle position; // Current position (rad)

    private final MomentOfInertia moi;
    private final double frictionStatic; // Static friction torque (Nm)
    private final double frictionDynamic; // Dynamic friction coefficient (Nm/(rad/s))
    private final boolean brakeMode;

    @Getter private Voltage inputVoltage = Units.Volts.of(0);
    @Getter private Voltage outputVoltage = Units.Volts.of(0);
    @Getter private Current current = Units.Amps.of(0);


    public FrictionMotorSim(DCMotor motor, MomentOfInertia moi, boolean brakeMode) {
        this(motor, moi, motor.stallTorqueNewtonMeters * 0.02, 0.001, brakeMode);
    }

    public FrictionMotorSim(DCMotor motor, MomentOfInertia moi, double frictionStatic, double frictionDynamic, boolean brakeMode) {
        this.motor = UnitDCMotor.unitize(motor);
        this.moi = moi;
        this.frictionStatic = frictionStatic;
        this.frictionDynamic = frictionDynamic;
        this.velocity = Units.RadiansPerSecond.of(0);
        this.position = Units.Radians.of(0);
        this.brakeMode = brakeMode;
    }

    public void set(double appliedInput) {
        setVoltage(BatterySimManager.getBatteryVoltage().times(appliedInput));
    }

    public void setVoltage(Voltage input) {
        inputVoltage = input;
    }

    public void update(Time dt) {
        // Simmed Battery Voltage
        Voltage batteryVoltage = BatterySimManager.getBatteryVoltage();

        // Get Clamped Input Voltage (Signed)
        outputVoltage = UnitUtil.clamp(inputVoltage, batteryVoltage.unaryMinus(), batteryVoltage);
        // Calculate Motor Current (Signed)
        current = motor.getCurrent(velocity, outputVoltage);
        BatterySimManager.addCurrent(UnitUtil.abs(current));

        // Calculate Motor Torque (Signed)
        Torque motorTorque = motor.getTorque(current);

        // Calculate Friction Torque (Signed)
        Torque frictionTorque = calculateFrictionTorque(motorTorque);
        Torque netTorque = motorTorque.minus(frictionTorque);
        AngularAcceleration acceleration = Units.RadiansPerSecondPerSecond.of(netTorque.in(Units.NewtonMeters) / moi.in(Units.KilogramSquareMeters));
        velocity = velocity.plus(acceleration.times(dt));
        position = position.plus(velocity.times(dt));
    }

    private Torque calculateFrictionTorque(Torque motorTorque) {
        if (Math.abs(velocity.in(Units.RadiansPerSecond)) < 1e-1) {
            velocity = Units.RadiansPerSecond.of(0);
            Torque staticFrictionTorque = Units.NewtonMeters.of(frictionStatic);
            if (UnitUtil.abs(motorTorque).lte(UnitUtil.abs(staticFrictionTorque))) return motorTorque;
            return staticFrictionTorque;
        }

        if (Math.abs(velocity.in(Units.RadiansPerSecond)) < 3) {
            if (brakeMode) {
                return Units.NewtonMeters.of(frictionDynamic * 10 * velocity.in(Units.RadiansPerSecond));
            }
            return Units.NewtonMeters.of(frictionDynamic * 3 * velocity.in(Units.RadiansPerSecond));
        }

        if (Math.abs(inputVoltage.in(Units.Volts)) < 1e-4) {
            if (brakeMode) {
                return Units.NewtonMeters.of(frictionDynamic * 10 * velocity.in(Units.RadiansPerSecond));
            }
            return Units.NewtonMeters.of(frictionDynamic * 3 * velocity.in(Units.RadiansPerSecond));    
        }

        return Units.NewtonMeters.of(frictionDynamic * velocity.in(Units.RadiansPerSecond));
    }
    
    public void updateInputs(MotorIOInputs inputs) {
        inputs.currentAmps = UnitUtil.abs(getCurrent());
        inputs.outputVoltage = getOutputVoltage();
        inputs.output = inputs.outputVoltage.divide(BatterySimManager.getBatteryVoltage()).baseUnitMagnitude();
        inputs.velocityRPM = getVelocity().in(Units.RPM);
        inputs.isOn = Math.abs(inputs.velocityRPM) > 0.01;
    }
}
