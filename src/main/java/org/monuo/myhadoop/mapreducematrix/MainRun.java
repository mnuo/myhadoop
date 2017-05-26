/**
 * MainRun.java created at 2017年5月20日 下午6:46:19
 */
package org.monuo.myhadoop.mapreducematrix;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import org.apache.hadoop.mapred.JobConf;

/**
 * @author saxon
 */
public class MainRun {
	public static final String HDFS = "hdfs://192.168.159.132:9000/";
	public static final Pattern DELIMITER = Pattern.compile("[\t,]");
	
	public static void martrixMultiply(){
		Map<String, String> path = new HashMap<String, String>();
		path.put("m1", "d:/tmp/martrix/m1.csv");
		path.put("m2", "d:/tmp/martrix/m2.csv");
		path.put("input", HDFS + "/user/hdfs/martrix");
		path.put("input1", HDFS + "/user/hdfs/martrix/m1");
		path.put("input2", HDFS + "/user/hdfs/martrix/m2");
		path.put("output", HDFS + "/user/hdfs/martrix/output");
		
		try {
			MartrixMultiply.run(path);//启动程序
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.exit(0);
	}
	public static JobConf config(){
		JobConf conf = new JobConf(MainRun.class);
		conf.setJobName("MartrixMultiply");
		return conf;
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		martrixMultiply();
	}
}
