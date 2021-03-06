/**
 * MartrixMultiply.java created at 2017年5月20日 下午8:51:53
 */
package org.monuo.myhadoop.mapreducematrix;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.JobConf;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.FileSplit;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;
import org.monuo.myhadoop.dao.HdfsDao;

/**
 * @author saxon
 */
public class MartrixMultiply {
	public static class SparseMartrixMapper extends Mapper<LongWritable, Text, Text, Text>{
		private String flag;//m1 or m2
		
		private int rowNum = 2;//矩阵A的行数
		private int colNum = 2;//矩阵B的列数
		private int rowIndexA = 1; //矩阵A的当前行数
		private int rowIndexB = 1; //矩阵B的当前行数
		
		@Override
		protected void setup(Context context) throws IOException, InterruptedException {
			FileSplit split = (FileSplit) context.getInputSplit();
			flag = split.getPath().getName();
		}
		@Override
		protected void map(LongWritable key, Text value, Context context)
				throws IOException, InterruptedException {
			String[] tokens = MainRun.DELIMITER.split(value.toString());
			if(flag.equals("m1")){
				for(int i = 1; i <= rowNum; i ++){
					Text k = new Text(rowIndexA + "," + i);
					for (int j = 1; j <= tokens.length; j++) {
						Text v = new Text("A:" + j + "," + tokens[j-1]);
						context.write(k, v);
						System.out.println(k.toString() + " " + v.toString());
					}
				}
				rowIndexA++;
			}else{
				for (int i = 1; i <= tokens.length; i++) {
                   for (int j = 1; j <= colNum; j++) {
						Text k = new Text(i + "," + j);
						Text v = new Text("B:" + rowIndexB + "," + tokens[j-1]);
						context.write(k, v);
						System.out.println(k.toString() + " " + v.toString());
					}
                }
				rowIndexB ++;
			}
		}
	}
	public static class SparseMartrixReducer extends Reducer<Text, Text, Text, IntWritable>{
		@Override
		protected void reduce(Text key, Iterable<Text> values, Context context)
				throws IOException, InterruptedException {
			Map<String, String> mapA = new HashMap<String, String>();
			Map<String, String> mapB = new HashMap<String, String>();
			System.out.println(key.toString());
			for(Text line : values){
				String val = line.toString();
				System.out.println("(" + val + ")");
				
				if(val.startsWith("A:")){
					String [] kv = MainRun.DELIMITER.split(val.substring(2));
					mapA.put(kv[0], kv[1]);
					//System.out.println("A:" + kv[0] + "," + kv[1]);
				}else if(val.startsWith("B:")){
					String[] kv = MainRun.DELIMITER.split(val.substring(2));
					mapB.put(kv[0], kv[1]);
					//System.out.println("B:" + kv[0] + "," + kv[1]);
				}
			}
			
			int result = 0;
			Iterator<String> iter = mapA.keySet().iterator();
			while(iter.hasNext()){
				String mapk = iter.next();
				//String bVal = mapB.containsKey(mapk) ? mapB.get(mapk) : "0";
				result += Integer.parseInt(mapA.get(mapk)) * Integer.parseInt(mapB.get(mapk));
			}
			context.write(key, new IntWritable(result));
			System.out.println();
			
			System.out.println("C:" + key.toString() + "," + result);
		}
	}
	/**
	 * @param args
	 * @throws IOException 
	 * @throws InterruptedException 
	 * @throws ClassNotFoundException 
	 */
	public static void run(Map<String, String> path) throws IOException, ClassNotFoundException, InterruptedException {
		JobConf conf = MainRun.config();
		
		String input = path.get("input");
		String input1 = path.get("input1");
		String input2 = path.get("input2");
		String output = path.get("output");
		
		HdfsDao hdfs = new HdfsDao(MainRun.HDFS, conf);
		hdfs.rmr(input);
		hdfs.mkdirs(input);
		hdfs.copyFile(path.get("m1"), input1);
		
		hdfs.copyFile(path.get("m2"), input2);
		
		Job job = Job.getInstance(conf);
		job.setJarByClass(MartrixMultiply.class);
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(Text.class);
		
		job.setMapperClass(SparseMartrixMapper.class);
		job.setReducerClass(SparseMartrixReducer.class);
		
		job.setInputFormatClass(TextInputFormat.class);
		job.setOutputFormatClass(TextOutputFormat.class);
		
		FileInputFormat.setInputPaths(job, new Path(input1), new Path(input2));
		FileOutputFormat.setOutputPath(job, new Path(output));
		
		job.waitForCompletion(true);
	}

}
