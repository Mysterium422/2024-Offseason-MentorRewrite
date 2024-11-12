package frc.lib;

import org.littletonrobotics.junction.LogTable;
import org.littletonrobotics.junction.inputs.LoggableInputs;

import edu.wpi.first.math.Matrix;
import edu.wpi.first.math.numbers.N1;
import edu.wpi.first.math.numbers.N2;
import edu.wpi.first.math.system.LinearSystem;
import edu.wpi.first.math.system.plant.DCMotor;
import edu.wpi.first.math.system.plant.LinearSystemId;
import edu.wpi.first.units.Units;
import edu.wpi.first.wpilibj.simulation.DCMotorSim;
import lombok.Getter;

public class DCMotorSimIO extends DCMotorSim implements LoggableInputs {
    @Getter private DCMotorSimIOInputsAutoLogged inputs = new DCMotorSimIOInputsAutoLogged();

    public DCMotorSimIO(LinearSystem<N2, N1, N2> plant, DCMotor gearbox, double... measurementStdDevs) {
        super(plant, gearbox, measurementStdDevs);
    }

    public DCMotorSimIO(DCMotor gearbox, double gearing, double jKgMetersSquared, double... measurementStdDevs) {
        this(LinearSystemId.createDCMotorSystem(gearbox, gearing, jKgMetersSquared), gearbox, measurementStdDevs);
    }

    public void updateInputs() {
        inputs.currentAmps = getCurrentDrawAmps();
        inputs.velocityRPM = getAngularVelocityRPM();
        inputs.isOn = Math.abs(inputs.velocityRPM) > 0.01;
    }

    @Override
    public void toLog(LogTable table) {
        inputs.toLog(table);
    }

    @Override
    public void fromLog(LogTable table) {
        inputs.fromLog(table);
    }
}
