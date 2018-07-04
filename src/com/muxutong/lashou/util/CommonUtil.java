package com.muxutong.lashou.util;

public class CommonUtil {

	public static double[] getAround(double lat,double lon ,double radius) {
	
		double degree = (24901*1609)/360.0;
		double dpmlat = 1/degree;
		double radiusLat = dpmlat*radius*10;
		
		double minLat = lat- radiusLat;
		double maxLat = lat+ radiusLat;
		
		double mpdLng = degree*Math.cos(lat*(Math.PI/180));
		double dpmLng = 1/mpdLng;
		double radiusLng = dpmLng*radius;
		
		double minLng = lon - radiusLng;
		double maxLng = lon + radiusLng;
		

		return new double[] {minLat,minLng,maxLat,maxLng};
	
	}
}
