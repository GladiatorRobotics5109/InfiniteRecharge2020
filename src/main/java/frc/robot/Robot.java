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
      public CANSparkMax m_Intake = new CANSparkMax(6, MotorType.kBrushless);
      public CANSparkMax m_Feeder = new CANSparkMax(7, MotorType.kBrushless);
      public CANSparkMax m_Tilting = new CANSparkMax(5, MotorType.kBrushless);
      public CANSparkMax m_TopShooter = new CANSparkMax(10, MotorType.kBrushless);
      public CANSparkMax m_BotShooter = new CANSparkMax(11, MotorType.kBrushless);
      public CANSparkMax m_ControlPanel = new CANSparkMax(8, MotorType.kBrushless);
      public CANSparkMax m_Climb = new CANSparkMax(3, MotorType.kBrushless);
      public CANSparkMax m_LeftWinch = new CANSparkMax(9, MotorType.kBrushless);
      public CANSparkMax m_RightWinch = new CANSparkMax(4, MotorType.kBrushless);

      public boolean inversion = true;

    //neo encoders
      public CANEncoder e_Left1 = m_Left1.getEncoder();
      public CANEncoder e_Left2 = m_Left2.getEncoder();
      public CANEncoder e_Right1 = m_Right1.getEncoder();
      public CANEncoder e_Right2 = m_Right2.getEncoder();
      public CANEncoder e_Intake = m_Intake.getEncoder();
      public CANEncoder e_Feeder = m_Feeder.getEncoder();
      public CANEncoder e_Tilting = m_Tilting.getEncoder();
      public CANEncoder e_TopShooter = m_TopShooter.getEncoder();
      public CANEncoder e_BotShooter = m_BotShooter.getEncoder();
      public CANEncoder e_ControlPanel = m_ControlPanel.getEncoder();
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
      public DifferentialDrive m_DriveTrain = new DifferentialDrive(m_Left, m_Right);

    //navx variables
      public AHRS navX = new AHRS(SPI.Port.kMXP);
      public float imu_Yaw;

    //vision variables
      NetworkTableInstance ntwrkInst = NetworkTableInstance.getDefault();
      NetworkTable visionTable;
      NetworkTable chameleonVision;




  //endregion
 
  @Override
  public void robotInit() {
    m_Left.setInverted(inversion);
    m_Right.setInverted(inversion);
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
    m_DriveTrain.tankDrive(j_Left.getY(), j_Right.getY());
  }


  @Override
  public void testPeriodic() {
    
  }

  //region_Methods




  //endregion

}
