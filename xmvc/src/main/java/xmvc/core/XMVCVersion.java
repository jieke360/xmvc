package xmvc.core;
public class XMVCVersion {
	public static String getVersion() {
		Package pkg = XMVCVersion.class.getPackage();
		return (pkg != null ? pkg.getImplementationVersion() : null);
	}
}
