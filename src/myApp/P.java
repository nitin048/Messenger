package myApp;

import java.net.InetAddress;

public class P {
	public static void main(String arg[]) {
		
		try {
			InetAddress ip = InetAddress.getLocalHost();
			String ipAddress = (ip.getHostAddress()).trim();
			int count = 0, j = 0;
			char[] gate = new char[ipAddress.length()];
			for (int i = 0; i < ipAddress.length(); i++) {
				gate[i] = ipAddress.charAt(i);
				if (ipAddress.charAt(i) == '.') {
					count++;
					j = i;
				}
				if (count == 3) {
					break;
				}

			}
			String ns = new String(gate);
			ns = ns.substring(0, j + 1);
			String last = ipAddress.substring(j + 1, ipAddress.length());
			for (int i = (Integer.parseInt(last)); i < (Integer.parseInt(last) + 40); i++) {
				String t = (ns + i);
				InetAddress inet = InetAddress.getByName(t);
				if (inet.isReachable(500))
					System.out.println(inet.getHostAddress().trim());
			}
			
			
			
		} catch (Exception e4) {
		}
	}
}
