//region_Copyright

  /*----------------------------------------------------------------------------*/
  /* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
  /* Open Source Software - may be modified and shared by FRC teams. The code   */
  /* must be accompanied by the FIRST BSD license file in the root directory of */
  /* the project.                                                               */
  /*----------------------------------------------------------------------------*/

//endregion

package frc.robot;

//region_Imports

  //regular imports
    import edu.wpi.first.wpilibj.*;
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

public class Robot extends TimedRobot {

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

    //navx variables
      public AHRS navX = new AHRS(SPI.Port.kMXP);
      public float imu_Yaw;

    //vision variables
      public NetworkTableInstance ntwrkInst = NetworkTableInstance.getDefault();
      public NetworkTable visionTable;
      public NetworkTable chameleonVision;

    //logic variables

      //gear switching
        public boolean lowGear;
        public boolean switchGears;
      

  //endregion
 
  @Override
  public void robotInit() {
    m_Left.setInverted(true);
    m_Right.setInverted(false);
  }


  @Override
  public void autonomousInit() {

  }

 
  @Override
  public void autonomousPeriodic() {
 
  }


  @Override
  public void teleopInit() {
    
  }


  @Override
  public void teleopPeriodic() {
    joystickControl();
    gearSwitching();
    SmartDashboard.putNumber("right joy", j_Right.getY());
    SmartDashboard.putNumber("left joy", j_Left.getY());

    SmartDashboard.putNumber("right encoder 1 ", e_Right1.getPosition());
    SmartDashboard.putNumber("right encoder 2 ", e_Right2.getPosition());
    SmartDashboard.putNumber("left encoder 1 ", e_Left1.getPosition());
    SmartDashboard.putNumber("left encoder 2 ", e_Left2.getPosition());

  }


  @Override
  public void testPeriodic() {

  }

  //region_Methods
    
    public void joystickControl(){ //method for implementing our lowgear/highgear modes into our driver controls
      if(lowGear){
        m_DriveTrain.tankDrive(j_Left.getY()/2, -j_Right.getY()/2);
      }
      else{
        m_DriveTrain.tankDrive(j_Left.getY(), -j_Right.getY());
      }
    }

    public void gearSwitching(){ //method for switching our bot to lowgear(less sensitive) or highgear(speedyboi)
      if(j_Right.getRawButton(1) && switchGears){
        if(lowGear){
          lowGear = false;
          switchGears = false;
        }
        else{
          lowGear = true;
          switchGears = false;
        }
      }
      else if(!j_Right.getRawButton(1)){
        switchGears = true;
      }
    }
    



  //endregion

}
