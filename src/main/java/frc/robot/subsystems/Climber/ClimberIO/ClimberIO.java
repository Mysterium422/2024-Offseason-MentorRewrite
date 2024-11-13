package frc.robot.subsystems.Climber.ClimberIO;

import org.littletonrobotics.junction.AutoLog;

import frc.lib.g3.MotorIOInputsAutoLogged;

public interface ClimberIO {

    @AutoLog
    public class ClimberIOInputs {
        public double leftPosition;
        public double rightPosition;
        public transient MotorIOInputsAutoLogged left = new MotorIOInputsAutoLogged();
        public transient MotorIOInputsAutoLogged right = new MotorIOInputsAutoLogged();
    }

    public void setPower(double rightPower, double leftPower);

    public void updateInputs(ClimberIOInputs inputs);
    
}
