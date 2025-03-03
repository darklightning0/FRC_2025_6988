package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.DemandType;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix6.configs.Slot0Configs;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Preferences;
import frc.robot.Constants;
import frc.robot.util.SimplePID;

public class Elevator {
    private static TalonSRX innerMotor = new TalonSRX(Constants.SubsystemConstants.TalonIDs.SRX.Elevator_Inner);
    private static TalonSRX outerMotor = new TalonSRX(Constants.SubsystemConstants.TalonIDs.SRX.Elevator_Outer);

    // private static SimplePID innerPid = new SimplePID("elevatorInner", 12);
    // private static SimplePID outerPid = new SimplePID("elevatorOuter", 12);

    static double getPrefDouble(String key, double backup) {
        return Preferences.getDouble("elevPid_" + key, backup);
    }
    static void setPrefDouble(String key, double value) {
        Preferences.setDouble("elevPid_" + key, value);
    }

    public Elevator() {
        
    }

    public static void config() {
        innerMotor.setNeutralMode(NeutralMode.Coast);

        outerMotor.setNeutralMode(NeutralMode.Coast);
        outerMotor.config_kP(0, getPrefDouble("outerP", 0.01));
        outerMotor.config_kI(0, getPrefDouble("outerI", 0.00));
        outerMotor.config_kD(0, getPrefDouble("outerD", 0.00));
        outerMotor.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, Constants.SubsystemConstants.AMTEncoderIDs.Elevator_Outer, 10);
        // outerMotor.setSensorPhase(true);
    }

    public static void mainloop() {
        double innerTargetPos = 0;
        double outerTargetPos = 0.43;

        // double innerVoltage = innerPid.getSignal(innerCurrentPos);
        // innerMotor.set(ControlMode.PercentOutput, 0, DemandType.Neutral, innerVoltage);

        outerMotor.set(ControlMode.Position, outerTargetPos);
    }
}
