package frc.lib;

import org.littletonrobotics.junction.LogTable;
import org.littletonrobotics.junction.inputs.LoggableInputs;

import com.revrobotics.spark.SparkMax;

import edu.wpi.first.units.Units;
import lombok.Getter;

public class SparkMaxIO extends SparkMax implements LoggableInputs {
    @Getter private SparkMaxIOInputsAutoLogged inputs = new SparkMaxIOInputsAutoLogged();

    public SparkMaxIO(int deviceId, MotorType type) {
        super(deviceId, type);
    }

    public void updateInputs() {
        inputs.output = getAppliedOutput();
        inputs.outputVoltage = getBusVoltage() * inputs.output;
        inputs.currentAmps = getOutputCurrent();
        inputs.isOn = Math.abs(inputs.output) > 0.01;
        inputs.tempFahrenheit = Units.Celsius.of(getMotorTemperature()).in(Units.Fahrenheit);
        inputs.velocityRPM = getEncoder().getVelocity();
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
