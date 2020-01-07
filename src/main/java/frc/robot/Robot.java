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
      public CANSparkMax m_Left1 = new CANSparkMax(1, MotorType.kBrushless);
      public CANSparkMax m_Left2 = new CANSparkMax(2, MotorType.kBrushless);
      public CANSparkMax m_Right1 = new CANSparkMax(3, MotorType.kBrushless);
      public CANSparkMax m_Right2 = new CANSparkMax(4, MotorType.kBrushless);
      public CANSparkMax m_LeftElevator = new CANSparkMax(5, MotorType.kBrushless);
      public CANSparkMax m_RightElevator = new CANSparkMax(6, MotorType.kBrushless);

    //neo encoders
      public CANEncoder e_Left1 = m_Left1.getEncoder();
      public CANEncoder e_Left2 = m_Left2.getEncoder();
      public CANEncoder e_Right1 = m_Right1.getEncoder();
      public CANEncoder e_Right2 = m_Right2.getEncoder();
      public CANEncoder e_LeftElevator = m_LeftElevator.getEncoder();
      public CANEncoder e_RightElevator = m_RightElevator.getEncoder();

    //neo pidcontrollers
      public CANPIDController pc_Left1 = m_Left1.getPIDController();
      public CANPIDController pc_Left2 = m_Left2.getPIDController();
      public CANPIDController pc_Right1 = m_Right1.getPIDController();
      public CANPIDController pc_Right2 = m_Right2.getPIDController();
      public CANPIDController pc_LeftElevator = m_Left1.getPIDController();
      public CANPIDController pc_RightElevator = m_Left1.getPIDController();

    //neo controllers







  //endregion
 
  @Override
  public void robotInit() {
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

  }


  @Override
  public void testPeriodic() {
  }
}
