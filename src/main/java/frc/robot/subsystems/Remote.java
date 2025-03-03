package frc.robot.subsystems;

import static frc.robot.Constants.ControllerConstants.driverJoystick;
import static frc.robot.Constants.ControllerConstants.driverJoystickDef;
import static frc.robot.Constants.ControllerConstants.operatorJoystick;
import static frc.robot.Constants.ControllerConstants.operatorJoystickDef;


import java.util.Map;

import edu.wpi.first.networktables.GenericEntry;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Constants;
import frc.robot.Constants.ControllerConstants;
import frc.robot.Constants.SubsystemConstants.RemoteButtons;
import frc.robot.Constants.SubsystemConstants.RemoteOperatorButtons;
//import frc.robot.Constants.SubsystemConstants.RemoteButtons;
//import frc.robot.Constants.SubsystemConstants.RemoteOperatorButtons;

enum DriveMode {

}

public final class Remote {
    //Intake kol hareketleri
    public static enum IntakeArmMode {
        Idle,
        Up,
        Down,
    }
    //Intake tekerlek hareket
    public static enum IntakeWheelMode {
        Idle,
        In,
        Shoot,
    }

    private static Remote single_instance = null;

    static IntakeArmMode input_armIntake = IntakeArmMode.Idle;
    static IntakeWheelMode input_wheelIntake = IntakeWheelMode.Idle;
    
    
    // Constructor
    // Here we will be creating private constructor
    // restricted to this class itself
    private Remote() {
        
    }

    // Static method
    // Static method to create instance of Singleton class
    public static synchronized Remote getInstance()
    {
        if (single_instance == null) single_instance = new Remote();
        return single_instance;
    }

    public static IntakeArmMode getIntakeArmMode(){
        return input_armIntake;
    }

    public static IntakeWheelMode getIntakeWheelMode(){
        return input_wheelIntake;
    }

    
    // pos. direction: 0 ~ 1
    // neg. direction: 0 ~ -1
    /* 
    private static double getBoosterValue() {
        if (!driverJoystickDef.isConnected()) return 0;
        double value = (1 - Constants.ControllerConstants.driverJoystick.getRawAxis(3))/2;
        if (value != 0) return getBoosterDirectionInternal() ? value : -value;
        else return getBoosterDirectionInternal() ? 0.01 : -0.01;
    }

    public static double getDriverBoosterValue() {
        return driverJoystickDef.isConnected() ? getBoosterValue() : 0.;
    }


    // true: default (positive)
    // false: reversed (negative)
    private static boolean getBoosterDirectionInternal() {
        return !Constants.ControllerConstants.driverJoystickDef.getRawButton(Constants.SubsystemConstants.RemoteButtons.ShooterRevert);
    }

   */

  
    

    //private static boolean takeoverEnabled = false; 

    public static void mainloop() {
        
        //Input -> intake arm command
        if(driverJoystickDef.isConnected()){
            if(operatorJoystickDef.getRawButton(RemoteOperatorButtons.intakeUp)){
                input_armIntake=IntakeArmMode.Up;
            }
            else if(operatorJoystickDef.getRawButton(RemoteOperatorButtons.intakeDown)){
                input_armIntake=IntakeArmMode.Down;
            }
            else{input_armIntake=IntakeArmMode.Idle;}
        }
        //input -> intake wheel command
        if(driverJoystickDef.isConnected()){
            if(operatorJoystickDef.getRawButton(RemoteOperatorButtons.intakeAlgeaIn)){
                input_wheelIntake=IntakeWheelMode.In;
            }
            else if(operatorJoystickDef.getRawButton(RemoteOperatorButtons.intakeAlgeaShoot)){
                input_wheelIntake=IntakeWheelMode.Shoot;
            }
            else{input_wheelIntake=IntakeWheelMode.Idle;}
        }

    }
}