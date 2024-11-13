package frc.robot.subsystems.Climber;

import org.littletonrobotics.junction.Logger;

import edu.wpi.first.wpilibj.smartdashboard.Mechanism2d;
import edu.wpi.first.wpilibj.smartdashboard.MechanismLigament2d;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.lib.RobotTime;
import frc.robot.subsystems.Climber.ClimberIO.ClimberIO;
import frc.robot.subsystems.Climber.ClimberIO.ClimberIOInputsAutoLogged;

public class Climber extends SubsystemBase {

    private ClimberIO climberIO;
    private ClimberIOInputsAutoLogged inputs = new ClimberIOInputsAutoLogged();
    private double leftPowerDesired;
    private double rightPowerDesired;
    private Mechanism2d viz = new Mechanism2d(4, 10);
    private MechanismLigament2d vizLeft = viz.getRoot("LeftClimber", 1, 0).append(new MechanismLigament2d("LeftHeight", 0, 0));
    private MechanismLigament2d vizRight = viz.getRoot("RightClimber", 3, 0).append(new MechanismLigament2d("RightHeight", 0, 0));

    public Climber(ClimberIO climberIO) {
        super("Climber");
        this.climberIO = climberIO;

    }

    @Override
    public void periodic() {
        double timestamp = RobotTime.getTimestampSeconds();
        climberIO.updateInputs(inputs);
        Logger.processInputs(getName(), inputs);

        vizLeft.setLength(inputs.leftPosition / 15);
        vizRight.setLength(inputs.rightPosition / 15);

        Logger.recordOutput(getName() + "/leftPowerDesired", leftPowerDesired);
        Logger.recordOutput(getName() + "/rightPowerDesired", rightPowerDesired);
        Logger.recordOutput(getName() + "/viz", viz);
        Logger.recordOutput(getName() + "/latencyPeriodicSec", RobotTime.getTimestampSeconds() - timestamp);


    }

    public void setPower(double rightPower, double leftPower) {
        climberIO.setPower(rightPower, leftPower);
        leftPowerDesired = leftPower;
        rightPowerDesired = rightPower;
    }
    
}
