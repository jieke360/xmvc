package info.txtfile.app.test;

import com.alanx.xmvc.core.ModelConvert;
import com.alanx.xmvc.core.modelconvertplug.IModelConvert;

public class PointConvert  extends ModelConvert implements IModelConvert{
	public Object convertObjectValue(Object arg0) {
		String str = (String) arg0;
		String[] xy = str.split(",");
		Point p = new Point();
		p.setX( Integer.parseInt(xy[0]));
		p.setY( Integer.parseInt(xy[1])) ;
		return p;
	}
}
