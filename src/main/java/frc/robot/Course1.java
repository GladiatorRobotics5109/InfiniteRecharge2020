package main.java.frc.robot;
    import edu.wpi.first.wpilibj.*;
    import edu.wpi.first.wpilibj.GenericHID.Hand;
    import edu.wpi.first.wpilibj.GenericHID.RumbleType;
    import edu.wpi.first.networktables.*;
    import edu.wpi.first.wpilibj.smartdashboard.*;
    import edu.wpi.first.wpilibj.drive.*;
    import edu.wpi.first.wpilibj.SpeedControllerGroup.*; 
    //region_Imports

  //regular imports
    import edu.wpi.first.wpilibj.*;
    import edu.wpi.first.wpilibj.GenericHID.Hand;
    import edu.wpi.first.wpilibj.GenericHID.RumbleType;
    import edu.wpi.first.networktables.*;
    import edu.wpi.first.wpilibj.smartdashboard.*;
    import edu.wpi.first.wpilibj.drive.*;
    import edu.wpi.first.wpilibj.SpeedControllerGroup.*;

  //spark max/neos imports
    import com.revrobotics.*;
    import com.revrobotics.CANSparkMaxLowLevel.MotorType;

  //navx imports 
    import com.kauailabs.navx.frc.*;

  //endregion


public class Course1 {
    //region_Variables
    //commented 37 wowowoowowoww yifan is chungo
    //joysticks
      public Joystick j_Left = new Joystick(0);
      public Joystick j_Right = new Joystick(1);
      public Joystick j_Operator = new Joystick(2);
      public XboxController j_XboxController = new XboxController(4);

    //neos
      public CANSparkMax m_Left1 = new CANSparkMax(12, MotorType.kBrushless);
      public CANSparkMax m_Left2 = new CANSparkMax(13, MotorType.kBrushless);
      public CANSparkMax m_Right1 = new CANSparkMax(1, MotorType.kBrushless);
      public CANSparkMax m_Right2 = new CANSparkMax(2, MotorType.kBrushless);
      public CANSparkMax m_Intake = new CANSparkMax(6, MotorType.kBrushless); //negative power for in, positive power for out
      public CANSparkMax m_Feeder = new CANSparkMax(7, MotorType.kBrushless); //positive power for in, negative power for out
      public CANSparkMax m_Tilting = new CANSparkMax(5, MotorType.kBrushless); //positive power for up, negative power for down
      public CANSparkMax m_TopShooter = new CANSparkMax(11, MotorType.kBrushless); //positive power for out
      public CANSparkMax m_BotShooter = new CANSparkMax(10, MotorType.kBrushless); //negative power for out
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
      public CANEncoder e_BotShooter = m_BotShooter.getEncoder(); //negative when shooting ball out
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
      public CANPIDController pc_BotShooter = m_BotShooter.getPIDController();
      public CANPIDController pc_ControlPanel = m_ControlPanel.getPIDController();
      public CANPIDController pc_Climb = m_Climb.getPIDController();
      public CANPIDController pc_LeftWinch = m_LeftWinch.getPIDController();
      public CANPIDController pc_RightWinch = m_RightWinch.getPIDController();



    //neo controllers
      public SpeedControllerGroup m_Left = new SpeedControllerGroup(m_Left1, m_Left2);
      public SpeedControllerGroup m_Right = new SpeedControllerGroup(m_Right1, m_Right2);
      public DifferentialDrive m_DriveTrain = new DifferentialDrive(m_Left, m_Right); //negative power makes bot move forward, positive power makes bot move packwards

    //tuning variables
      public double kP_Left1, kI_Left1, kD_Left1, kIz_Left1, kFF_Left1;
      public double kP_Left2, kI_Left2, kD_Left2, kIz_Left2, kFF_Left2; //gay
      public double kP_Right1, kI_Right1, kD_Right1, kIz_Right1, kFF_Right1;
      public double kP_Right2, kI_Right2, kD_Right2, kIz_Right2, kFF_Right2;
      public double kP_Feeder, kI_Feeder, kD_Feeder, kIz_Feeder, kFF_Feeder;
      public double kP_Tilting, kI_Tilting, kD_Tilting, kIz_Tilting, kFF_Tilting;
      public double kP_TopShooter, kI_TopShooter, kD_TopShooter, kIz_TopShooter, kFF_TopShooter;
      public double kP_BotShooter, kI_BotShooter, kD_BotShooter, kIz_BotShooter, kFF_BotShooter;
      public double kP_ControlPanel, kI_ControlPanel, kD_ControlPanel, kIz_ControlPanel, kFF_ControlPanel;
      public double kP_Climb, kI_Climb, kD_Climb, kIz_Climb, kFF_Climb;

    //solenoid variables
      public Solenoid s_LeftIntake = new Solenoid(7);
      public Solenoid s_RightIntake = new Solenoid(5);
      public Solenoid s_ControlPanel = new Solenoid(4);

    //navx variables
      public AHRS navX = new AHRS(SPI.Port.kMXP);
      public float imu_Yaw;

    //vision variables
      public NetworkTableInstance ntwrkInst = NetworkTableInstance.getDefault();
      public NetworkTable visionTable;
      public NetworkTable chameleonVision;
      public double chameleon_Yaw;
      public double chameleon_Pitch;
      public NetworkTable controlPanelVision;
      public double areaRed;
      public double areaGreen;
      public double areaBlue;
      public double areaYel;

    //sensors
      public DigitalInput interruptSensor = new DigitalInput(1);
      public Counter lidarSensor = new Counter(9);
      final double off  = 10; //offset for sensor. test with tape measure
      public double dist;

    //logic variables

      //gear switching
        public boolean lowGear=true;
        public boolean switchGears;
      
      //intake booleans
        public boolean intakeExtended = false;

      //ball counting variables
        public boolean oldBallBoolean = false;
        public boolean newBallBoolean = false;
        public boolean ballDebounceBoolean = false;
        public int ballCounter = 0;

      //shooting booleans
        public boolean readyToFeed = false;

      //controlpanel variables
        public int targetColor;
        public int currentColor;
        public int revolutionCount = 0;
        public boolean sawColor = true; 
        public double controlPanelConstant = 6.9;
        public boolean controlPanelExtended = false;
        public boolean extendControlPanel;

      //gamedata
        public String gameData;

      //climb variables
        public boolean climbMode = false;
        public boolean extendClimbMode = false;
        public boolean switchClimbMode;
        public boolean extendClimber;

      //variables for auto phase
        public int autoCase;
        public int autoCounter = 0;
  //endregion
  @Override
  public void autonomousInit() {
    m_Feeder.setIdleMode(CANSparkMax.IdleMode.kBrake);
  }

  @Override
  public void autonomousPeriodic() {
    SmartDashboard.putNumber("AutoCase", 1);
    autoCase = (int)SmartDashboard.getNumber("AutoCase", 1);

    switch (autoCase){
      case 1:


        if(autoCounter == 0){
          driveStraight(12.6, 500);
        }
        else if(autoCounter == 1){
          Clockwise(360);
        }
        else if(autoCounter == 2){
          leftTurn(33.7);
        }
        else if(autoCounter == 3){
          driveStraight(9, 500);
        }
        else if(autoCounter == 4){
          CounterClockwise(360);
        }
        else if(autoCounter == 5){
          rightTurn(45);
        }
        else if(autoCounter == 6){
          driveStraight(7, 500);
        }
        else if(autoCounter == 7){
          CounterClockwise(270);
        }
        else if(autoCounter == 8){
          driveStraight(25, 500);
        }
        
        break;
      case 2:
        break;
      case 3:
        break;
      default:


    }

    SmartDashboard.putNumber("autocounter", autoCounter);
  }
    public void driveStraight(double feet, double speed){
        double encoderFeet = feet * 6.095233693;
        if(e_Left1.getPosition() < encoderFeet || e_Left2.getPosition() < encoderFeet || e_Right1.getPosition() > -encoderFeet || e_Right2.getPosition() > -encoderFeet){
          pc_Left1.setReference(speed, ControlType.kVelocity);
          pc_Left2.setReference(speed, ControlType.kVelocity);
          pc_Right1.setReference(-speed, ControlType.kVelocity);
          pc_Right2.setReference(-speed, ControlType.kVelocity);
        }
        else{
          m_DriveTrain.stopMotor();
          e_Right1.setPosition(0);
          e_Right2.setPosition(0);
          e_Left1.setPosition(0);
          e_Left2.setPosition(0);
          autoCounter ++;
        }
      }
    public void Clockwise(double degrees){
      double innerDistance = (49*PI*(degrees/360))/5
      double outerDistance = (95*PI*/(degrees/360))/5

      if(e_Left1.getPosition() < innerDistance || e_Left2.getPosition() < innerDistance || e_Right1.getPosition() > -outerDistance || e_Right2.getPosition() > -outerDistance){
        pc_Left1.setReference(speed, ControlType.kVelocity);
        pc_Left2.setReference(speed, ControlType.kVelocity);
        pc_Right1.setReference(-speed, ControlType.kVelocity);
        pc_Right2.setReference(-speed, ControlType.kVelocity);
      }
      else{
        m_DriveTrain.stopMotor();
        e_Right1.setPosition(0);
        e_Right2.setPosition(0);
        e_Left1.setPosition(0);
        e_Left2.setPosition(0);
        autoCounter ++;
      }
    
    }
    public void CounterClockwise (double degrees){
      double innerDistance = (49*PI*(degrees/360))/5
      double outerDistance = (95*PI*/(degrees/360))/5

      if(e_Left1.getPosition() < innerDistance || e_Left2.getPosition() < innerDistance || e_Right1.getPosition() > -outerDistance || e_Right2.getPosition() > -outerDistance){
        pc_Left1.setReference(-speed, ControlType.kVelocity);
        pc_Left2.setReference(-speed, ControlType.kVelocity);
        pc_Right1.setReference(speed, ControlType.kVelocity);
        pc_Right2.setReference(speed, ControlType.kVelocity);
      }
      else{
        m_DriveTrain.stopMotor();
        e_Right1.setPosition(0);
        e_Right2.setPosition(0);
        e_Left1.setPosition(0);
        e_Left2.setPosition(0);
        autoCounter ++;
      }
    }

    public void rightTurn(double targetAngle){
      double actualYaw = navX.getYaw() % 360;

        if (Math.abs(actualYaw - targetAngle) < 8){
            pc_Left1.setReference(0, ControlType.kVelocity);
            pc_Left2.setReference(0, ControlType.kVelocity);
            pc_Right1.setReference(0, ControlType.kVelocity);
            pc_Right2.setReference(0, ControlType.kVelocity);
            e_Right1.setPosition(0);
            e_Right2.setPosition(0);
            e_Left1.setPosition(0);
            e_Left2.setPosition(0);
            autoCounter ++;
        }
        else{
            m_Left.set(.4);
            m_Right.set(.4);
        }
    }

    public void leftTurn(double targetAngle){
        double actualYaw = navX.getYaw() % 360;
        if (Math.abs(actualYaw - targetAngle) < 8){
          pc_Left1.setReference(0, ControlType.kVelocity);
          pc_Left2.setReference(0, ControlType.kVelocity);
          pc_Right1.setReference(0, ControlType.kVelocity);
          pc_Right2.setReference(0, ControlType.kVelocity);
          e_Right1.setPosition(0);
          e_Right2.setPosition(0);
          e_Left1.setPosition(0);
          e_Left2.setPosition(0);
          autoCounter ++;
        }
        else{
          pc_Left1.setReference(-1000, ControlType.kVelocity);
          pc_Left2.setReference(-1000, ControlType.kVelocity);
          pc_Right1.setReference(-1000, ControlType.kVelocity);
          pc_Right2.setReference(-1000, ControlType.kVelocity);
        }
    }

}