package frc.robot.oi;

import edu.wpi.first.wpilibj2.command.button.Trigger;
import frc.robot.Constants;
import lombok.Getter;

public class OneDriverControlsImpl extends Controls {

  @Getter private final CommandXboxControllerOI driver;

  public OneDriverControlsImpl(int driverPort) {
    driver = new CommandXboxControllerOI(driverPort);
  }

  @Override
  public double getDriveForward() {
    return deadzone(
        -driver.getLeftY(), driver.getLeftX(), driver.getRightX(), Constants.JOYSTICK_THRESHOLD);
  }

  @Override
  public double getDriveLeft() {
    return deadzone(
        -driver.getLeftX(), driver.getLeftY(), driver.getRightX(), Constants.JOYSTICK_THRESHOLD);
  }

  @Override
  public double getDriveRotate() {
    return deadzone(
        -driver.getRightX(), driver.getLeftX(), driver.getLeftY(), Constants.JOYSTICK_THRESHOLD);
  }

  @Override
  public double getClimbLeftOnly() {
    double driverRightY = driver.getRightY();
    if (Math.abs(driverRightY) < 0.9) return 0;
    if (driver.getRightX() > 0.05) {
      return 0;
    }
    return -driverRightY;
  }

  @Override
  public double getClimbRightOnly() {
    double driverRightY = driver.getRightY();
    if (Math.abs(driverRightY) < 0.9) return 0;
    if (driver.getRightX() < -0.05) {
      return 0;
    }
    return -driverRightY;
  }

  @Override
  public Trigger climbUp() {
    return driver.a();
  }

  @Override
  public Trigger climbDown() {
    return EmptyTrigger();
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
    return driver.rightBumper();
  }

  @Override
  public Trigger vomit() {
    return driver.leftBumper();
  }

  @Override
  public Trigger shoot() {
    return driver.rightTrigger();
  }

  @Override
  public Trigger subwooferMode() {
    return driver.povLeft();
  }

  @Override
  public Trigger ampMode() {
    return driver.povDown();
  }

  @Override
  public Trigger autoAimMode() {
    return driver.povUp();
  }

  @Override
  public Trigger idleMode() {
    return driver.povRight();
  }
}
