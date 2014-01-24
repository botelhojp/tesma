package tesma;

import org.apache.log4j.Logger;

import tesma.form.SplashScreen;


public class Tesma {

	private static Logger log = Logger.getLogger(Tesma.class);

	public static void main(String args[]) {
		SplashScreen spaScreen = new SplashScreen();
		try {
			spaScreen.showScreen();
			spaScreen.setProgress("interface", 0);
			delay(500);
			spaScreen.setProgress("agents", 0);
			delay(500);
			spaScreen.setProgress("openjade", 0);
			java.awt.EventQueue.invokeLater(new Runnable() {
				@Override
				public void run() {
					new tesma.form.MainFrame().setVisible(true);
				}
			});
		} catch (Exception ex) {
			ex.printStackTrace();
			log.error("Erro ao inicar o cronos. \nCausa:", ex);
		} finally {
			spaScreen.close();
		}
	}

	private static void delay(int i) {
		try {
			Thread.sleep(i);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
