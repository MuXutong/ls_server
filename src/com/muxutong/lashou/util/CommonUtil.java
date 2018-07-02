package com.muxutong.lashou.util;

public class CommonUtil {

	public static double[] getAround(double lat,double lon ,double raidus) {
	
		double degree = (24901*1609)/360.0;
		double dpmlat = 1/degree;
		double raidusLat = dpmlat*raidus;
		
		double minLat = lat- raidusLat;
		double maxLat = lat+ raidusLat;
		
		double mpdLng = degree*Math.cos(lat*(Math.PI/180));
		double dpmLng = 1/mpdLng;
		double raidusLng = dpmLng*raidus;
		
		double minLng = lon - raidusLng;
		double maxLng = lon + raidusLng;
		

		return new double[] {minLat,minLng,maxLat,maxLng};
	
	}
}
