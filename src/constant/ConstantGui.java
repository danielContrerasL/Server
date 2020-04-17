package constant;

import java.awt.Dimension;
import java.awt.Toolkit;

public class ConstantGui {
	public static final Dimension SIZE_WINDOW = Toolkit.getDefaultToolkit().getScreenSize();
	public static final int MY_WIDTH = (int) (SIZE_WINDOW.width * 0.35);
	public static final int MY_HEIGTH = (int) (SIZE_WINDOW.height * 0.8);
	
	public static final String WINDOW_NAME = "My Server";
	public static final String DF_PATH = "/robot.jpg";
	
}
