/**
 * KPIPV.java created at 2017年5月19日 上午11:56:35
 */
package org.monuo.myhadoop.kpi;

import java.io.IOException;
import java.util.Iterator;

import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.FileInputFormat;
import org.apache.hadoop.mapred.FileOutputFormat;
import org.apache.hadoop.mapred.JobClient;
import org.apache.hadoop.mapred.JobConf;
import org.apache.hadoop.mapred.MapReduceBase;
import org.apache.hadoop.mapred.Mapper;
import org.apache.hadoop.mapred.OutputCollector;
import org.apache.hadoop.mapred.Reducer;
import org.apache.hadoop.mapred.Reporter;
import org.apache.hadoop.mapred.TextInputFormat;
import org.apache.hadoop.mapred.TextOutputFormat;

/**
 * @author saxon
 */
public class KPIPV {
	/**
	 * Mapper类
	 * @author saxon
	 */
	public static class KPIPVMapper extends MapReduceBase implements Mapper<Object, Text, Text, IntWritable>{
		private IntWritable one = new IntWritable(1);
		private Text word = new Text();
		
		public void map(Object key, Text value, OutputCollector<Text, IntWritable> output, Reporter reporter)
				throws IOException {
			KPI kpi = KPI.filterPVs(value.toString());
			if(kpi.isValid()){
				word.set(kpi.getRequest());
				output.collect(word, one);
			}
		}

	}
	/**
	 * reducer 类
	 * @author saxon
	 */
	public static class KPIPVReducer extends MapReduceBase implements Reducer<Text, IntWritable, Text, IntWritable>{
		private IntWritable result = new IntWritable();
		
		public void reduce(Text key, Iterator<IntWritable> values, OutputCollector<Text, IntWritable> output,
				Reporter reporter) throws IOException {
			int sum = 0;
			while (values.hasNext()) {
				sum +=values.next().get();
			}
			result.set(sum);
			output.collect(key, result);
		}
	}
	
	public static void main(String[] args) throws Exception {
		String input = "hdfs://192.168.159.132:9000/user/hdfs/log_kpi/";
        String output = "hdfs://192.168.159.132:9000/user/hdfs/log_kpi/pv";

        JobConf conf = new JobConf(KPIPV.class);
        conf.setJobName("KPIPV");

        conf.setMapOutputKeyClass(Text.class);
        conf.setMapOutputValueClass(IntWritable.class);

        conf.setOutputKeyClass(Text.class);
        conf.setOutputValueClass(IntWritable.class);

        conf.setMapperClass(KPIPVMapper.class);
        conf.setCombinerClass(KPIPVReducer.class);
        conf.setReducerClass(KPIPVReducer.class);

        conf.setInputFormat(TextInputFormat.class);
        conf.setOutputFormat(TextOutputFormat.class);

        FileInputFormat.setInputPaths(conf, new Path(input));
        FileOutputFormat.setOutputPath(conf, new Path(output));

        JobClient.runJob(conf);
        System.exit(0);
	}
}
