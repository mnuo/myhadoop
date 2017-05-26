/**
 * KPIIP.java created at 2017年5月19日 下午12:31:28
 */
package org.monuo.myhadoop.kpi;

import java.io.IOException;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.MapReduceBase;
import org.apache.hadoop.mapred.Mapper;
import org.apache.hadoop.mapred.OutputCollector;
import org.apache.hadoop.mapred.Reporter;

/**
 * @author saxon
 */
public class KPIIP {
	public static class KPIIPMapper extends MapReduceBase implements Mapper<Object, Text, Text, IntWritable>{

		public void map(Object arg0, Text arg1, OutputCollector<Text, IntWritable> arg2, Reporter arg3)
				throws IOException {
			// TODO Auto-generated method stub
			
		}
		
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
