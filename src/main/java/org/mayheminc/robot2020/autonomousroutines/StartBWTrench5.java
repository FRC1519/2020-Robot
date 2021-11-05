/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.mayheminc.robot2020.autonomousroutines;

import edu.wpi.first.wpilibj2.command.*;

public class StartBWTrench5 extends SequentialCommandGroup {
        /**
         * Add your docs here.
         */
        public StartBWTrench5() {
                addCommands(new StartBWTrench(50)); // use extraDistance of 40 inches for control panel
        }
}
