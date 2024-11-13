package frc.lib.g3;

import org.littletonrobotics.junction.LogTable;
import org.littletonrobotics.junction.inputs.LoggableInputs;

import edu.wpi.first.math.numbers.N1;
import edu.wpi.first.math.numbers.N2;
import edu.wpi.first.math.system.LinearSystem;
import edu.wpi.first.math.system.plant.DCMotor;
import edu.wpi.first.math.system.plant.LinearSystemId;
import edu.wpi.first.wpilibj.RobotController;
import edu.wpi.first.wpilibj.simulation.DCMotorSim;

public class DCMotorSimIO extends DCMotorSim {
    public DCMotorSimIO(LinearSystem<N2, N1, N2> plant, DCMotor gearbox, double... measurementStdDevs) {
        super(plant, gearbox, measurementStdDevs);
    }

    public DCMotorSimIO(DCMotor gearbox, double gearing, double jKgMetersSquared, double... measurementStdDevs) {
        this(LinearSystemId.createDCMotorSystem(gearbox, gearing, jKgMetersSquared), gearbox, measurementStdDevs);
    }

    public void updateInputs(MotorIOInputsAutoLogged inputs) {
        inputs.outputVoltage = getInputVoltage();
        inputs.output = inputs.outputVoltage / RobotController.getBatteryVoltage();
        inputs.currentAmps = getCurrentDrawAmps();
        inputs.velocityRPM = getAngularVelocityRPM();
        inputs.isOn = Math.abs(inputs.velocityRPM) > 0.01;
    }
}
