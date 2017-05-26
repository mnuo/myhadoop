/**
 * WordCount.java created at 2017年5月18日 下午4:21:09
 */
package org.monuo.myhadoop.mapreduce;

import java.io.IOException;
import java.util.Iterator;
import java.util.StringTokenizer;

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
 * 单词统计
 * @author saxon
 */
public class WordCount {
	/**
	 * Mapper 类
	 * @author saxon
	 */
	public static class WordCountMapper extends MapReduceBase implements Mapper<Object, Text, Text, IntWritable>{
		private final static IntWritable one = new IntWritable(1);
		private Text word = new Text();
		/**
		 * map 方法完成工作就是读取文件,将文件中每个单词作为key键,值设置为1,然后将此键值对设置为map的输出,即reduce的输入
		 */
		public void map(Object key, Text value, OutputCollector<Text, IntWritable> output, Reporter reporter)
				throws IOException {
			/**
			 * StringTokenizer: 字符串分隔解析类型.
			 * 1, StringTokenizer(String str): 构造一个用来解析str的StringTokenizer对象.java默认的分隔符是空格,制表符(\t),换行符(\n),回车键(\r)
			 * 2, StringTokenizer(String str, String delim):  构造一个用来解析str的StringTokenizer对象,并提供一个指定的分隔符
			 * 3, StringTokenizer(String str, String delim, boolean returnDelim): 构造一个用来解析str的StringTokenizer对象,并指定一个分隔符,同时指定是否返回分隔符
			 */
			StringTokenizer itr = new StringTokenizer(value.toString());
			while (itr.hasMoreTokens()) {
				word.set(itr.nextToken());
				output.collect(word, one);
			}
		}
	}
	/**
	 * Reduce类
	 * @author saxon
	 */
	public static class WordCountReduce extends MapReduceBase implements Reducer<Text, IntWritable, Text, IntWritable>{
		private IntWritable result = new IntWritable();
		/**
		 * reduce的输入即是Map的输出,将相同键的单词的值进行统计累加,即可得出单词的统计个数,最后把单词作为键的,单词的个数作为值,输出到设置的文件中保存
		 */
		public void reduce(Text key, Iterator<IntWritable> values, OutputCollector<Text, IntWritable> output,
				Reporter reporter) throws IOException {
			int sum = 0;
			while(values.hasNext()){
				sum += values.next().get();
			}
			result.set(sum);
			output.collect(key, result);
		}
		
	}
	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		System.setProperty("hadoop.home.dir", "D:/appservers/hadoop-2.7.3");
		String input = "hdfs://192.168.159.132:9000/words.txt";//数据输入路径
		String output = "hdfs://192.168.159.132:9000/out";//输出路径设置为HDFS根目录下的out文件夹下,该目录不应该存在,否则出错
		
		JobConf jobConf = new JobConf(WordCount.class);
		jobConf.setJobName("WordCount");
//		jobConf.addResource("classpath:/core-site.xml");
//		jobConf.addResource("classpath:/mapreduce-site.xml");
//		jobConf.addResource("classpath:/hdfs-site.xml");
		
		jobConf.setOutputKeyClass(Text.class);//对应单词字符串
		jobConf.setOutputValueClass(IntWritable.class);//对应单词的统计个数 int类型
		
		jobConf.setMapperClass(WordCountMapper.class);//设置mapper类
		//设置合并函数,合并函数的输出作为reduce的输入,提高性能,能有效降低map和reduce之间的数据传输量但是合并函数不能滥用,需要集合具体业务。
		//由于本次应用是统计单子个数,所以使用合并函数不会对结果或者业务逻辑结果产生任何影响。例如:我们统计单词出现的平均值的业务逻辑时,就不能使用合并函数。此时使用,会影响最终结果
		jobConf.setCombinerClass(WordCountReduce.class);
		jobConf.setReducerClass(WordCountReduce.class);//设置reduce类
		
		//设置输入格式,TextInputFormat是默认的输入格式,这里可以不写。它产生的键类型是LongWritable(代表文件中开始的偏移量),值类型是Text(文本类型)
		jobConf.setInputFormat(TextInputFormat.class);
		//设置输出格式,TextOutputFormat是默认的输出格式,每条记录写为文本行,它的键和值可以是任意类型,输出回调用toString(),输入字符串写入文本中,默认键和值使用制表符分隔
		jobConf.setOutputFormat(TextOutputFormat.class);
		
		FileInputFormat.setInputPaths(jobConf, new Path(input));//设置输入文件的路径
		FileOutputFormat.setOutputPath(jobConf, new Path(output));//设置输出数据文件路径(该路径不能存在,否则报错)
		
		JobClient.runJob(jobConf);//启动MapReduce
		
		System.exit(0);
	}

}
