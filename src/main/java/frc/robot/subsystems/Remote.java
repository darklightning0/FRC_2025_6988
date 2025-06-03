package frc.robot.subsystems;

import static frc.robot.Constants.ControllerConstants.operatorJoystick;
import static frc.robot.Constants.ControllerConstants.operatorJoystickDef;

import edu.wpi.first.wpilibj.Encoder;
import frc.robot.Constants;
import frc.robot.Constants.SubsystemConstants.RemoteOperatorButtons;
import frc.robot.util.DistanceControl;
import frc.robot.util.Util;

// import frc.robot.Constants.SubsystemConstants.RemoteButtons;
// import frc.robot.Constants.SubsystemConstants.RemoteOperatorButtons;
// import frc.robot.util.UltrasonicSensor;

enum DriveMode {

}

public class Remote {
    // Intake kol hareketleri
    public static enum IntakeArmMode {
        Idle,
        Up,
        Down,
    }

    // Intake tekerlek hareket
    public static enum IntakeWheelMode {
        Idle,
        In,
        Shoot,
    }

    public static enum ShooterMode {

        Idle,
        Shoot,
        Reverse,

    }

    IntakeArmMode input_armIntake = IntakeArmMode.Idle;
    IntakeWheelMode input_wheelIntake = IntakeWheelMode.Idle;
    ShooterMode shooter_mode = ShooterMode.Idle;
    // static UltrasonicSensor ultrasonicSensor = new UltrasonicSensor(0, 0);
    // ElevatorMode input_elevatorMode = ElevatorMode.Idle;
    public double input_innerElevatorTarget = 0;
    public double input_outerElevatorTarget = 0;
    boolean elevator_manual = false;

    boolean elevator_manualHome = false;
    boolean elevator_manualHomePressed = false;

    public DistanceControl innerElevatorProgressControl = new DistanceControl(0.0, 0.98);
    public DistanceControl outerElevatorProgressControl = new DistanceControl(0.0, 0.99);

    // Constructor
    // Here we will be creating private constructor
    // restricted to this class itself
    public Remote() {

    }

    public IntakeArmMode getIntakeArmMode() {
        return input_armIntake;
    }

    public ShooterMode getShooterMode() {
        return shooter_mode;
    }

    public IntakeWheelMode getIntakeWheelMode() {
        return input_wheelIntake;
    }

    public double getInnerElevatorTarget() {
        return input_innerElevatorTarget;
    }
    public double getOuterElevatorTarget() {
        return input_outerElevatorTarget;
    }

    public boolean getElevatorManual() {
        return elevator_manual;
    }

    public void resetTargets() {
        input_innerElevatorTarget = 0;
		input_outerElevatorTarget = 0;
        innerElevatorProgressControl.resetWithValue(0);
        outerElevatorProgressControl.resetWithValue(0);
    }

    public boolean getHomeButtonPressed() {
        return elevator_manualHomePressed;
    }

    public boolean getHomeButton() {
        return elevator_manualHome;
    }

    // pos. direction: 0 ~ 1
    // neg. direction: 0 ~ -1
    /*
     * private static double getBoosterValue() {
     * if (!driverJoystickDef.isConnected()) return 0;
     * double value = (1 -
     * Constants.ControllerConstants.driverJoystick.getRawAxis(3))/2;
     * if (value != 0) return getBoosterDirectionInternal() ? value : -value;
     * else return getBoosterDirectionInternal() ? 0.01 : -0.01;
     * }
     * 
     * public static double getDriverBoosterValue() {
     * return driverJoystickDef.isConnected() ? getBoosterValue() : 0.;
     * }
     * 
     * 
     * // true: default (positive)
     * // false: reversed (negative)
     * private static boolean getBoosterDirectionInternal() {
     * return
     * !Constants.ControllerConstants.driverJoystickDef.getRawButton(Constants.
     * SubsystemConstants.RemoteButtons.ShooterRevert);
     * }
     * 
     */

    // private static boolean takeoverEnabled = false;

    Encoder innerEncoder = new Encoder(6, 7);

    public void mainloop() {
        
        // Elevator Manual Toggle
        if (operatorJoystickDef.getRightBumperButtonPressed()) {
            elevator_manual = !elevator_manual;
            innerElevatorProgressControl.resetWithValue(input_innerElevatorTarget);
            outerElevatorProgressControl.resetWithValue(input_outerElevatorTarget);
        }


        elevator_manualHomePressed = operatorJoystickDef.getRawButtonPressed(RemoteOperatorButtons.home);
        elevator_manualHome = operatorJoystickDef.getRawButton(RemoteOperatorButtons.home);

        if (elevator_manualHomePressed) {
            resetTargets();
        }



        // Elevator
        if (elevator_manual) {
            double innerElevatorVelocity = Util.deadband(operatorJoystick.getRightY(), 0.12) * -0.3;
            input_innerElevatorTarget = innerElevatorProgressControl.mainloop(innerElevatorVelocity);
            double outerElevatorVelocity = Util.deadband(operatorJoystick.getLeftY(), 0.12) * -0.3;
            input_outerElevatorTarget = outerElevatorProgressControl.mainloop(outerElevatorVelocity);
        } else {
            if (operatorJoystickDef.isConnected()) {
                if (operatorJoystickDef.getAButton()) {
                    input_innerElevatorTarget = Constants.ReefLayers.L1;
                    input_outerElevatorTarget = 0;
                } else if (operatorJoystickDef.getBButton()) {
                    input_innerElevatorTarget = Constants.ReefLayers.L2;
                    input_outerElevatorTarget = 0;
                } else if (operatorJoystickDef.getXButton()) {
                    input_innerElevatorTarget = Constants.ReefLayers.L3;
                    input_outerElevatorTarget = 0;
                } else if (operatorJoystickDef.getYButton()) {
                    input_innerElevatorTarget = Constants.ReefLayers.L4;
                    input_outerElevatorTarget = 0;
                }
            }
        }


        // Shooter

         if (operatorJoystickDef.getRightTriggerAxis() > 0.1) {
            shooter_mode = ShooterMode.Shoot;
        } else if (operatorJoystickDef.getLeftTriggerAxis() > 0.1) {
            shooter_mode = ShooterMode.Reverse;
        } else {
            shooter_mode = ShooterMode.Idle;
        } 

        // Input -> intake arm command
        if (operatorJoystickDef.isConnected()) {
            if (operatorJoystickDef.getPOV() == 0) {
                input_armIntake = IntakeArmMode.Up;
            } else if (operatorJoystickDef.getPOV() == 180) {
                input_armIntake = IntakeArmMode.Down;
            } else {
                input_armIntake = IntakeArmMode.Idle;
            }
        }
        // input -> intake wheel command
        if (operatorJoystickDef.isConnected()) {
            if (operatorJoystickDef.getPOV() == 270) {
                input_wheelIntake = IntakeWheelMode.In;
            } else if (operatorJoystickDef.getPOV() == 90) {
                input_wheelIntake = IntakeWheelMode.Shoot;
            } else {
                input_wheelIntake = IntakeWheelMode.Idle;
            }
        }

    }
}
