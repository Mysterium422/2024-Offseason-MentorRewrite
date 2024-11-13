package frc.robot.oi;

import edu.wpi.first.wpilibj2.command.button.Trigger;
import frc.robot.Constants;
import lombok.Getter;

public class TwoDriverControlsImpl extends Controls {

    @Getter private final CommandXboxControllerOI driver;
    @Getter private final CommandXboxControllerOI operator;

    public TwoDriverControlsImpl(int driverPort, int operatorPort) {
        driver = new CommandXboxControllerOI(driverPort);
        operator = new CommandXboxControllerOI(operatorPort);
    }

    @Override
    public double getDriveForward() {
        return deadzone(-driver.getLeftY(), driver.getLeftX(), driver.getRightX(), Constants.JOYSTICK_THRESHOLD);
    }

    @Override
    public double getDriveLeft() {
        return deadzone(-driver.getLeftX(), driver.getLeftY(), driver.getRightX(), Constants.JOYSTICK_THRESHOLD);
    }

    @Override
    public double getDriveRotate() {
        return deadzone(-driver.getRightX(), driver.getLeftX(), driver.getLeftY(), Constants.JOYSTICK_THRESHOLD);
    }

    @Override
    public double getClimbLeftOnly() {
        return Constants.deadzone(-operator.getLeftY());
    }

    @Override
    public double getClimbRightOnly() {
        return Constants.deadzone(-operator.getRightY());
    }

    @Override
    public Trigger climbUp() {
        return operator.povUp();
    }

    @Override
    public Trigger climbDown() {
        return operator.povDown();
    }

    @Override
    public Trigger resetHeading() {
        return driver.a();
    }

    @Override
    public Trigger brake() {
        return driver.x();
    }

    @Override
    public Trigger alignSpeaker() {
        return driver.y();
    }

    @Override
    public Trigger boost() {
        return driver.leftTrigger();
    }

    @Override
    public Trigger alignZero() {
        return driver.b();
    }

    @Override
    public Trigger intake() {
        return operator.rightBumper();
    }

    @Override
    public Trigger vomit() {
        return operator.leftTrigger();
    }

    @Override
    public Trigger shoot() {
        return operator.rightTrigger();
    }

    @Override
    public Trigger subwooferMode() {
        return operator.a();
    }

    @Override
    public Trigger ampMode() {
        return operator.x();
    }

    @Override
    public Trigger autoAimMode() {
        return operator.y();
    }

    @Override
    public Trigger idleMode() {
        return operator.b();
    }

}
