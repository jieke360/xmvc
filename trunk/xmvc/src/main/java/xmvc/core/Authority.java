package xmvc.core;

public class Authority {
	public static boolean hasAuthority(URLMapping url){
		return true;
	}

	public static boolean hasAuthority(Processor p) {
		return true;
	}
}
