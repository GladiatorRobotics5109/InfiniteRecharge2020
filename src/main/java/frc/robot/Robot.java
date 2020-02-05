  
package frc.robot;

//navx imports
import com.kauailabs.navx.frc.AHRS;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.DriverStation;

//spark maxes/neos imports
import com.revrobotics.CANSparkMax;
import com.revrobotics.ControlType;
import com.revrobotics.CANEncoder;
import com.revrobotics.CANPIDController;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

//regular imports
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.networktables.*;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
//vision imports
import edu.wpi.first.wpilibj.*;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;


//Robot start
public class Robot extends TimedRobot {

  //network tables stuff
  NetworkTableInstance inst = NetworkTableInstance.getDefault();
  edu.wpi.first.networktables.NetworkTable visiontable;
  edu.wpi.first.networktables.NetworkTable chameleonvision;
  double yaw;

   
 //region_Variables
 
   //joysticks
   public Joystick j_Left = new Joystick(0);
   public Joystick j_Right = new Joystick(1);
   public Joystick j_Operator = new Joystick(2);

 //neos
   public CANSparkMax m_Left1 = new CANSparkMax(12, MotorType.kBrushless);
   public CANSparkMax m_Left2 = new CANSparkMax(13, MotorType.kBrushless);
   public CANSparkMax m_Right1 = new CANSparkMax(1, MotorType.kBrushless);
   public CANSparkMax m_Right2 = new CANSparkMax(2, MotorType.kBrushless);
   public CANSparkMax m_Intake = new CANSparkMax(6, MotorType.kBrushless); //negative power for in, positive power for out
   public CANSparkMax m_Feeder = new CANSparkMax(7, MotorType.kBrushless); //positive power for in, negative power for out
   public CANSparkMax m_Tilting = new CANSparkMax(5, MotorType.kBrushless); //positive power for up, negative power for down
   public CANSparkMax m_TopShooter = new CANSparkMax(11, MotorType.kBrushless); //positive power for out
   public CANSparkMax m_BottomShooter = new CANSparkMax(10, MotorType.kBrushless); //negative power for out
   public CANSparkMax m_ControlPanel = new CANSparkMax(8, MotorType.kBrushless); //when facing robot's control panel wheel from front of bot, positive power spins ccw and negative power spins cw
   public CANSparkMax m_Climb = new CANSparkMax(3, MotorType.kBrushless);
   public CANSparkMax m_LeftWinch = new CANSparkMax(9, MotorType.kBrushless);
   public CANSparkMax m_RightWinch = new CANSparkMax(4, MotorType.kBrushless);

 //neo encoders
   public CANEncoder e_Left1 = m_Left1.getEncoder(); //positive forward for Left
   public CANEncoder e_Left2 = m_Left2.getEncoder();
   public CANEncoder e_Right1 = m_Right1.getEncoder(); //negative forward for right
   public CANEncoder e_Right2 = m_Right2.getEncoder();
   public CANEncoder e_Intake = m_Intake.getEncoder(); //negative when intaking
   public CANEncoder e_Feeder = m_Feeder.getEncoder(); //positive when intaking
   public CANEncoder e_Tilting = m_Tilting.getEncoder(); //negative when leaning back
   public CANEncoder e_TopShooter = m_TopShooter.getEncoder(); //positive when shooting ball out
   public CANEncoder e_BottomShooter = m_BottomShooter.getEncoder(); //negative when shooting ball out
   public CANEncoder e_ControlPanel = m_ControlPanel.getEncoder(); //positive when ccw, negative when cw
   public CANEncoder e_Climb = m_Climb.getEncoder();
   public CANEncoder e_LeftWinch = m_LeftWinch.getEncoder();
   public CANEncoder e_RightWinch = m_RightWinch.getEncoder();

 //neo pidcontrollers
   public CANPIDController pc_Left1 = m_Left1.getPIDController();
   public CANPIDController pc_Left2 = m_Left2.getPIDController();
   public CANPIDController pc_Right1 = m_Right1.getPIDController();
   public CANPIDController pc_Right2 = m_Right2.getPIDController();
   public CANPIDController pc_Intake = m_Intake.getPIDController();
   public CANPIDController pc_Feeder = m_Feeder.getPIDController();
   public CANPIDController pc_Tilting = m_Tilting.getPIDController();
   public CANPIDController pc_TopShooter = m_TopShooter.getPIDController();
   public CANPIDController pc_BottomShooter = m_BottomShooter.getPIDController();
   public CANPIDController pc_ControlPanel = m_ControlPanel.getPIDController();
   public CANPIDController pc_Climb = m_Climb.getPIDController();
   public CANPIDController pc_LeftWinch = m_LeftWinch.getPIDController();
   public CANPIDController pc_RightWinch = m_RightWinch.getPIDController();

 //neo controllers
   public SpeedControllerGroup m_Left = new SpeedControllerGroup(m_Left1, m_Left2);
   public SpeedControllerGroup m_Right = new SpeedControllerGroup(m_Right1, m_Right2);
   public DifferentialDrive m_DriveTrain = new DifferentialDrive(m_Left, m_Right); //negative power makes bot move forward, positive power makes bot move backwards

 //solenoids
   public Solenoid s_LeftIntake = new Solenoid(4);
   public Solenoid s_RightIntake = new Solenoid(5);
   public Solenoid s_ControlPanel = new Solenoid(6);
   public Solenoid s_Additional = new Solenoid(7);

 //navx variables
   public AHRS navX = new AHRS(SPI.Port.kMXP);
   public float imu_Yaw;

 //vision variables
   public NetworkTableInstance ntwrkInst = NetworkTableInstance.getDefault();
   public NetworkTable visionTable;
   public NetworkTable chameleonVision;

 //logic variables

   //gear switching
     public boolean lowGear = false;
     public boolean switchGears = false;

   //feeder moving
     public double finalEncoderCounts = 0;
     public boolean move1Position = false;

   //intake variables
     public boolean intakeExtended = false;
    
//endregion
@Override
public void robotInit() {
 m_Left.setInverted(true);
 m_Right.setInverted(false);

 //region_CameraEnabling
   new Thread(() -> {
     UsbCamera camera = CameraServer.getInstance().startAutomaticCapture();
     camera.setResolution(1280, 720);

     CvSink cvSink = CameraServer.getInstance().getVideo();
     CvSource outputStream = CameraServer.getInstance().putVideo("Main Camera", 1280, 720);

     Mat source = new Mat();
     Mat output = new Mat();

     while(!Thread.interrupted()) {
       if (cvSink.grabFrame(source) == 0) {
         continue;
       }
       Imgproc.cvtColor(source, output, Imgproc.COLOR_BGR2GRAY);
       outputStream.putFrame(output);
     }
   }).start();
 //end region

 //region_SetPidCoefficients
   pc_BottomShooter.setP(.1);
   pc_BottomShooter.setD(1e-5);
   pc_BottomShooter.setFF(.5);
   pc_BottomShooter.setOutputRange(-1, 1);
   pc_TopShooter.setP(.1);
   pc_TopShooter.setD(1e-5);
   pc_TopShooter.setFF(.5);
   pc_TopShooter.setOutputRange(-1, 1);
   pc_Feeder.setP(.1);
   pc_Feeder.setI(1e-4);
   pc_Feeder.setD(1);
   pc_Feeder.setOutputRange(-1, 1);
   pc_ControlPanel.setP(.1);
   pc_ControlPanel.setI(1e-4);
   pc_ControlPanel.setD(1);
   pc_ControlPanel.setOutputRange(-1, 1);
   pc_Climb.setP(.1);
   pc_Climb.setI(1e-4);
   pc_Climb.setD(1);
   pc_Climb.setOutputRange(-1, 1);
 //end region
  }
  // robotInit End

  public void autonomousInit() {

    leftEncoder1.setPosition(0);
    leftEncoder2.setPosition(0);
    rightEncoder1.setPosition(0);
    rightEncoder2.setPosition(0);

  }

  public void autonomousPeriodic() {
    if (leftJoystick.getRawButton(1)) {
      double setPoint = 0;
      /**
       * Note that left is set to the negative setpoint, because it's facing backwards
       */

      leftPidController1.setReference(-setPoint, ControlType.kVelocity);
      rightPidController1.setReference(setPoint, ControlType.kVelocity);
      leftMotor2.follow(leftMotor1);
      rightMotor2.follow(rightMotor1);

      SmartDashboard.putNumber("SetPoint", setPoint);
      SmartDashboard.putNumber("Left Encoder Position1", leftEncoder1.getPosition());
      SmartDashboard.putNumber("Left Encoder Position2", leftEncoder2.getPosition());
      SmartDashboard.putNumber("Right Encoder Position1", rightEncoder1.getPosition());
      SmartDashboard.putNumber("Right Encoder Position2", rightEncoder1.getPosition());
      SmartDashboard.putNumber("Left Encoder Velocity1", leftEncoder1.getVelocity());
      SmartDashboard.putNumber("Right Encoder Velocity1", rightEncoder1.getVelocity());
      SmartDashboard.putNumber("Left Encoder Velocity2", leftEncoder2.getVelocity());
      SmartDashboard.putNumber("Right Encoder Velocity2", rightEncoder2.getVelocity());

    } else {

      if (leftJoystick.getRawButton(11) && changeDrive) {
        if (squaredInput) {
          squaredInput = false;
          changeDrive = false;
        } else {
          squaredInput = true;
          changeDrive = false;
        }
      } else if (!leftJoystick.getRawButton(11)) {
        changeDrive = true;
      }

      m_myRobot.arcadeDrive(leftJoystick.getY(), -leftJoystick.getZ() * .5, squaredInput);

      /**
       * lets get some information up on the dash
       */
      SmartDashboard.putBoolean("squaredInput", squaredInput);

      /**
       * Just for tuning, let's print the joystick value as well. This will help
       * establish our feed forward value
       */

    }

  }

  public void testInit() {

    leftEncoder1.setPosition(0);
    leftEncoder2.setPosition(0);
    rightEncoder1.setPosition(0);
    rightEncoder2.setPosition(0);

  }

  public void testPeriodic() {
  
    chameleonvision = inst.getTable("chameleon-vision");
    visiontable = chameleonvision.getSubTable("VisionTable");
    yaw = visiontable.getEntry("yaw").getDouble(0);
    SmartDashboard.putNumber("YAW", yaw);
    tuningConstant = -28;
    if (xbox.getAButton() == true){
      System.out.println("ooga");
    }

    if (leftJoystick.getRawButton(1)) {
    SmartDashboard.putBoolean("button", true);
      if (yaw > .5) {
        SmartDashboard.putBoolean("turnR", true);
        leftMotor2.follow(leftMotor1);
        rightMotor2.follow(rightMotor1);

        leftPidController1.setReference(yaw * tuningConstant , ControlType.kVelocity);
        rightPidController1.setReference(yaw * tuningConstant , ControlType.kVelocity);
      }
      
      else if (yaw < -.5) {
        SmartDashboard.putBoolean("turnL", true);
        leftMotor2.follow(leftMotor1);
        rightMotor2.follow(rightMotor1);

        leftPidController1.setReference(yaw * tuningConstant , ControlType.kVelocity);
        rightPidController1.setReference(yaw * tuningConstant , ControlType.kVelocity);
      }

      else{
        SmartDashboard.putBoolean("stop", true);
        leftMotor2.follow(leftMotor1);
        rightMotor2.follow(rightMotor1);

        leftPidController1.setReference(0, ControlType.kVelocity);
        rightPidController1.setReference(0, ControlType.kVelocity);
      }
    }
     else {
      SmartDashboard.putBoolean("button", false);
      SmartDashboard.putBoolean("turnR", false);
      SmartDashboard.putBoolean("turnL", false);
      SmartDashboard.putBoolean("stop", false);
      SmartDashboard.putString("TEST:", "NOTWORKING");
      leftPidController2.setReference(0, ControlType.kVelocity);
      rightPidController2.setReference(0, ControlType.kVelocity);
      leftPidController1.setReference(0, ControlType.kVelocity);
      rightPidController1.setReference(0, ControlType.kVelocity);
    }
    

System.out.println(yaw);
  }
}
//.........