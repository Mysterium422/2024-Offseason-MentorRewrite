package frc.robot.subsystems.Climber.ClimberIO;

import edu.wpi.first.math.system.plant.DCMotor;
import edu.wpi.first.wpilibj.RobotController;
import edu.wpi.first.wpilibj.Timer;
import frc.lib.g3.DCMotorSimIO;

public class ClimberIOSim implements ClimberIO {
    private final DCMotorSimIO leftClimber = new DCMotorSimIO(DCMotor.getNEO(1), 1.0/15, 0);
    private final DCMotorSimIO rightClimber = new DCMotorSimIO(DCMotor.getNEO(1), 1.0/15, 0);
    private double previousTime = -1;

    @Override
    public void setPower(double rightPower, double leftPower) {
        leftClimber.setInputVoltage(leftPower * RobotController.getBatteryVoltage());
        rightClimber.setInputVoltage(rightPower * RobotController.getBatteryVoltage());
    }

    @Override
    public void updateInputs(ClimberIOInputs inputs) {
        if (previousTime < 0) {
            leftClimber.update(0.02);
        } else {
            leftClimber.update(Timer.getFPGATimestamp() - previousTime);
        }

        previousTime = Timer.getFPGATimestamp();

    }
    
}
