package frc.robot.subsystems.Climber.ClimberIO;

import com.revrobotics.sim.SparkMaxSim;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.config.SparkMaxConfig;

import edu.wpi.first.math.system.plant.DCMotor;
import edu.wpi.first.units.Units;
import edu.wpi.first.units.measure.Voltage;
import edu.wpi.first.wpilibj.RobotController;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.simulation.BatterySim;
import edu.wpi.first.wpilibj.simulation.DifferentialDrivetrainSim;
import edu.wpi.first.wpilibj.simulation.PWMSim;
import edu.wpi.first.wpilibj.simulation.RoboRioSim;
import frc.lib.g3.DCMotorSimIO;
import frc.lib.g3.FrictionMotorSim;
import frc.lib.g3.MotorIOInputsAutoLogged;

public class ClimberIOSim implements ClimberIO {
  private final FrictionMotorSim leftMotorSim = new FrictionMotorSim(DCMotor.getNEO(1), Units.KilogramSquareMeters.of(0.004), 15);
  private final SparkMaxSim leftSparkMaxSim;
  private final SparkMax leftSparkMax;
  private double previousTime = -1;

  public ClimberIOSim() {
    leftSparkMax = new SparkMax(0, MotorType.kBrushless);
    leftSparkMax.createSimFaultManager();
    leftSparkMaxSim = new SparkMaxSim(leftSparkMax, DCMotor.getNEO(1));
  }

  @Override
  public void setPower(double rightPower, double leftPower) {
    leftMotorSim.setVoltage(Units.Volts.of(rightPower * RoboRioSim.getVInVoltage()));
    leftSparkMax.setVoltage(leftPower * RoboRioSim.getVInVoltage());
  }

  @Override
  public void updateInputs(
      ClimberIOInputs inputs, MotorIOInputsAutoLogged left, MotorIOInputsAutoLogged right) {
    leftMotorSim.update(Units.Milliseconds.of(20));
    leftSparkMaxSim.iterate(leftMotorSim.getVelocity().in(Units.RPM), RoboRioSim.getVInVoltage(), 0.02);

    previousTime = Timer.getFPGATimestamp();
    left.currentAmps = leftMotorSim.getCurrent().in(Units.Amps);
    left.outputVoltage = leftMotorSim.getOutputVoltage().in(Units.Volts);
    left.output = left.outputVoltage / RobotController.getBatteryVoltage();
    left.velocityRPM = leftMotorSim.getVelocity().in(Units.RPM);
    left.isOn = Math.abs(left.velocityRPM) > 0.01;

    right.currentAmps = leftSparkMaxSim.getMotorCurrent();
    right.output = leftSparkMaxSim.getAppliedOutput();
    right.outputVoltage = right.output * leftSparkMaxSim.getBusVoltage();
    right.velocityRPM = leftSparkMaxSim.getVelocity();
    right.isOn = Math.abs(right.velocityRPM) > 0.01;
    // leftClimber.updateInputs(left);
    // rightClimber.updateInputs(right);
    // inputs.leftPosition = leftClimber.getAngularPositionRotations();
    // inputs.rightPosition = rightClimber.getAngularPositionRotations();
    inputs.leftPosition = leftMotorSim.getPosition().in(Units.Radians) * Units.Inches.of(2).in(Units.Meters) / 15;
  }
}
