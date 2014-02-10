/*************************************************************************
	> File Name: TankClient.java
	> Author: Bslin
	> Mail:  
	> Created Time: 2014年02月10日 星期一 14时25分19秒
 ************************************************************************/

import java.awt.*;
import java.awt.event.*;

public class TankClient extends Frame {

	int x = 50, y = 50;

	Image offScreenImage = null;

	public void paint(Graphics g) {
		Color c = g.getColor();  // *
		g.setColor(Color.RED);
		g.fillOval(x, y, 30, 30);
		g.setColor(c);  // *

		y += 5;
	}

	public void update(Graphics g) {
		if(offScreenImage == null) {
			offScreenImage = this.createImage(800, 600);
		}
		Graphics goffScreen = offScreenImage.getGraphics();
		Color c = goffScreen.getColor();
		goffScreen.setColor(Color.GREEN);
		goffScreen.fillRect(0, 0, 800, 600);
		goffScreen.setColor(c);
		paint(goffScreen);  // 先画到背面的虚拟图片
		g.drawImage(offScreenImage, 0, 0, null);  // 再画到前面图片上
	}

	public void launchFrame() {
		setBounds(400, 300, 800, 600);
		setTitle("TankWar");
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
		setResizable(false);
		setBackground(Color.GREEN);
		setVisible(true);

		new Thread(new PaintThread()).start();
	}

	private class PaintThread implements Runnable {
		public void run() {
			while(true) {
				repaint();  // -> update() ->paint().
				try {
					Thread.sleep(20);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public static void main(String[] args) {
		new TankClient().launchFrame();
	}

}
