package frc.robot.oi;

import edu.wpi.first.wpilibj2.command.button.Trigger;

public abstract class Controls {

    public double getDriveForward() {
        return 0;
    }

    public double getDriveLeft() {
        return 0;
    }

    public double getDriveRotate() {
        return 0;
    }

    public double getClimbLeftOnly() {
        return 0;
    }

    public double getClimbRightOnly() {
        return 0;
    }

    public Trigger climbUp() {
        return EmptyTrigger();
    }

    public Trigger climbDown() {
        return EmptyTrigger();
    }

    public Trigger resetHeading() {
        return EmptyTrigger();
    }

    public Trigger brake() {
        return EmptyTrigger();
    }

    public Trigger alignSpeaker() {
        return EmptyTrigger();
    }

    public Trigger boost() {
        return EmptyTrigger();
    }

    public Trigger alignZero() {
        return EmptyTrigger();
    }

    public Trigger intake() {
        return EmptyTrigger();
    }

    public Trigger vomit() {
        return EmptyTrigger();
    }

    public Trigger shoot() {
        return EmptyTrigger();
    }

    public Trigger subwooferMode() {
        return EmptyTrigger();
    }

    public Trigger ampMode() {
        return EmptyTrigger();
    }

    public Trigger autoAimMode() {
        return EmptyTrigger();
    }

    public Trigger idleMode() {
        return EmptyTrigger();
    }

    protected Trigger EmptyTrigger() {
        return new Trigger(() -> false);
    }

    protected double deadzone(double a, double b, double c, double zone) {
        if (Math.sqrt(Math.pow(a, 2) + Math.pow(b, 2) + Math.pow(c, 2)) > zone) {
            return a * Math.abs(a);
        } else {
            return 0;
        }
    }
}
