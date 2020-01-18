package org.mayheminc.util;

import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.command.InstantCommand;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * This is a class to help tune a PID.
 * Another class that extends IPidTunerObject is passed in.
 * This interface defines methods that are used to set the P-I-D values.
 * This class gets joystick buttons that define the different options.
 *  - Button 1: cycle through P-I-D values
 *  - Button 2: increase the value.
 *  - Button 3: decrease the value.
 *  - Button 4: cycle through the amount the value changes: 10, 1, .1, .01, .001, .0001
 *  This class needs to be called for updateSmartDashboard().
 * @author User
 *
 */
public class PidTuner extends InstantCommand{
	Button m_PidCycle;
	Button m_Inc;
	Button m_Dec;
	Button m_ValueCycle;
	PidTunerObject m_pidObj;
	
	enum PidCycle {P, I, D, F};
	int m_cycle;
	int m_position;
	
	// Remember the buttons and call this object when they are pressed.
	// remember the PID Object so we can get/set the PID values.
	public PidTuner(Button b1, Button b2, Button b3, Button b4, PidTunerObject obj)
	{
		setRunWhenDisabled(true);
		
		b1.whenPressed(this);
		b2.whenPressed(this);
		b3.whenPressed(this);
		b4.whenPressed(this);
		
		m_PidCycle = b1;
		m_ValueCycle = b2;
		m_Inc = b3;
		m_Dec = b4;
		
		m_pidObj = obj;	
	}
	
	// Run the 'instant command'.  Determine which command was pressed.
    protected void initialize() {
    	if( m_PidCycle.get())
    	{
    		RunCycle();
    	}
    	if( m_Inc.get())
    	{
    		RunInc();
    	}
    	if( m_Dec.get())
    	{
    		RunDec();
    	}
    	if( m_ValueCycle.get())
    	{
    		RunValue();
    	}
    }
    
    // set the P, I, or D that we are changing
	public void RunCycle()
	{
//		System.out.println("PID Tuner: RunCycle");
		m_cycle++;
		m_cycle = m_cycle % 4;
	}

	String getCycleStr()
	{
		String str[] = {"P", "I", "D", "F"};
		
		return str[m_cycle];
	}
	
	// calculate the amount to increment, get the value, apply the amount, set the new value
	public void RunInc()
	{
//		System.out.println("PID Tuner: RunInc");
		double amount = calculateAmount();
		
		double value = getValue();
		value = value + amount;
		setValue(value);		
	}
	
	// if m_position is 1, return 10, 0 returns 1, -1 returns 0.1, etc.
	double calculateAmount()
	{
		double retval = Math.pow(10,  m_position);
		return retval;
	}
	
	// based on the cycle, get the P, I, or D.
	double getValue()
	{
		switch(m_cycle)
		{
		case 0:
			return m_pidObj.getP();
		case 1:
			return m_pidObj.getI();
		case 2:
			return m_pidObj.getD();
		case 3:
			return m_pidObj.getF();
		}
		return 0.0;
	}
	
	// based on the cycle, set the P, I, or D.
	void setValue(double d)
	{
		switch(m_cycle)
		{
		case 0:
			m_pidObj.setP(d);
			break;
		case 1:
			m_pidObj.setI(d);
			break;
		case 2:
			m_pidObj.setD(d);
			break;
		case 3:
			m_pidObj.setF(d);
			break;
		}
	}
	
	// calculate the amount to decrement, get the value, apply the amount, set the new value
	public void RunDec()
	{
//		System.out.println("PID Tuner: RunDec");
		double amount = calculateAmount();
		
		double value = getValue();
		value = value - amount;
		setValue(value);		
	}
	
	// Change the amount we are decrementing or incrementing
	public void RunValue()
	{
//		System.out.println("PID Tuner: RunValue");

		m_position--;
		if( m_position < -4)
		{
			m_position = 1; // 10.1234
		}
	}
	
	public void updateSmartDashboard()
	{
		SmartDashboard.putNumber("PID Tuner P",  m_pidObj.getP());
		SmartDashboard.putNumber("PID Tuner I",  m_pidObj.getI());
		SmartDashboard.putNumber("PID Tuner D",  m_pidObj.getD());
		SmartDashboard.putNumber("PID Tuner F",  m_pidObj.getF());

		SmartDashboard.putString("PID Tuner Cycle", getCycleStr());
		SmartDashboard.putNumber("PID Tuner Amount", calculateAmount());
	}
	
}