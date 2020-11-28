package Nvidia;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class Program {

	public static void main(String[] args) {

		System.out.println("run");
		File fileBeep = new File("C:/Users/Public/lib/beep.wav");
		File fileBeepStartEnd = new File("C:/Users/Public/lib/beepStartEnd.wav");
//		File fileBeep = new File("C:/Users/Lichay/git/JavaAutoSearch/AutoSearch/src/app/beep.wav");
//		File fileBeepStartEnd = new File("C:/Users/Lichay/git/JavaAutoSearch/AutoSearch/src/app/beepStartEnd.wav");
		Clip clipBeep = null;
		Clip clipBeepStartEnd = null;

		if (fileBeep.exists() && fileBeepStartEnd.exists()) {

			try {

				AudioInputStream audioInputStreamBeep = AudioSystem.getAudioInputStream(fileBeep);
				AudioInputStream audioInputStreamBeepStartEnd = AudioSystem.getAudioInputStream(fileBeepStartEnd);
				clipBeep = AudioSystem.getClip();
				clipBeepStartEnd = AudioSystem.getClip();
				clipBeep.open(audioInputStreamBeep);
				clipBeepStartEnd.open(audioInputStreamBeepStartEnd);

			} catch (Exception e) {
				e.printStackTrace();
			}

		}

		clipBeepStartEnd.start();

		HttpURLConnection connection1 = null;
		HttpURLConnection connection2 = null;
		URL url1 = null;
		URL url2 = null;

		try {
			url1 = new URL("https://www.newegg.com/global/il-en/p/pl?d=rtx+3070&N=600494828%20101613484");
			url2 = new URL("https://www.newegg.com/global/il-en/p/pl?d=RTX+3080&N=601357261%20101613484");
		} catch (MalformedURLException e1) {
			clipBeepStartEnd.setMicrosecondPosition(0);
			clipBeepStartEnd.start();
			e1.printStackTrace();
		}

		while (true) {

			try {
				TimeUnit.SECONDS.sleep(30);

				boolean isValid = false;

				isValid = isValid(url1, connection1);
				if (isValid) {
					clipBeep.setMicrosecondPosition(0);
					clipBeep.start();
				}

				isValid = isValid(url2, connection2);
				if (isValid) {
					clipBeep.setMicrosecondPosition(0);
					clipBeep.start();
				}

			} catch (Exception e) {
				clipBeepStartEnd.setMicrosecondPosition(0);
				clipBeepStartEnd.start();
				e.printStackTrace();
			}

		}

	}

	private static boolean isValid(URL url, HttpURLConnection connection) {

		BufferedReader reader;
		String line;
		StringBuffer responseContant = new StringBuffer();

		try {

			connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("GET");
			connection.setConnectTimeout(4000);
			connection.setReadTimeout(4000);

			int status = connection.getResponseCode();

			if (status > 299) {
				reader = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
				while ((line = reader.readLine()) != null)
					responseContant.append(line);
				reader.close();
			} else {
				reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
				while ((line = reader.readLine()) != null)
					responseContant.append(line);
				reader.close();
				String value = responseContant.toString();
				if (value.contains(Stock.ADD_TO_CART.getMessage()) || value.contains(Stock.OFFERS.getMessage())) {
					return true;
				}
			}

		} catch (Exception e) {
			e.printStackTrace();

		}
		return false;
	}

}
