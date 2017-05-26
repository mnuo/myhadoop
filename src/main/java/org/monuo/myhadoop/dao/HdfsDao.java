/**
 * HdfsDao.java created at 2017年5月18日 上午10:15:08
 */
package org.monuo.myhadoop.dao;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.BlockLocation;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IOUtils;
import org.apache.hadoop.mapred.JobConf;
import org.apache.log4j.Logger;
import org.monuo.myhadoop.common.util.ResourceConsts;

/**
 * @author saxon
 */
public class HdfsDao {
	public static Logger logger = Logger.getLogger(HdfsDao.class);
	public static String HDFS = "";
	static {
		try {
			HDFS = ResourceConsts.getValue("hadoop_config", "HDFS_URI");
			System.setProperty("hadoop.home.dir", "D:/appservers/hadoop-2.7.3");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

    //hdfs路径
    private String hdfsPath;
    //Hadoop系统配置
    private Configuration conf;
	
	public HdfsDao(Configuration conf) {
        this(HDFS, conf);
    }
    public HdfsDao(String hdfs, Configuration conf) {
        this.hdfsPath = hdfs;
        this.conf = conf;
    }
    
    public static JobConf config() {
        JobConf conf = new JobConf();
        conf.setJobName("HdfsDao");
//      conf.addResource("classpath:/hadoop/core-site.xml");
//      conf.addResource("classpath:/hadoop/hdfs-site.xml");
//      conf.addResource("classpath:/hadoop/mapred-site.xml");
        return conf;
    }
	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		JobConf conf = config();
        HdfsDao hdfs = new HdfsDao(conf);
//        hdfs.mkdirs("/tmp/new/two");
//        hdfs.rmr("/tmp/new/two");
//        hdfs.copyFile("d:/mnuo/test.xml", "/tmp/new");
//        hdfs.cat("/tmp/new/test.xml");
//        hdfs.download("/tmp/new/core-site.xml", "d:/");
//        hdfs.createFile("/tmp/new/text", "Hello World!");
//        hdfs.cat("/tmp/new/text");
//        hdfs.renameFile("/tmp/new/text", "/tmp/new/text1");
        hdfs.location("/tmp/new/", "text1");
        hdfs.ls("/tmp/new");
	}
	/**
	 * 遍历文件
	 * @param folder
	 * @throws IOException 
	 */
	public void ls(String folder) throws IOException{
		Path path = new Path(folder);
		FileSystem fs = FileSystem.get(URI.create(hdfsPath), conf);
		FileStatus[] list = fs.listStatus(path);
		logger.info("ls: " + folder);
		logger.info("\n=========================");
		for(FileStatus f : list){
			logger.info("\n name: "+f.getPath()+", folder: "+f.isDirectory()+", size: "+f.getLen()+"\n");
		}
		logger.info("\n=========================");
		fs.close();
		
	}
	/**
	 * 创建目录
	 * @param folder
	 * @throws IOException 
	 */
	public void mkdirs(String folder) throws IOException{
		Path path = new Path(folder);
		FileSystem fs = FileSystem.get(URI.create(hdfsPath), conf);
		if(!fs.exists(path)){
			fs.mkdirs(path);
			logger.info(" create folder: " + folder);
		}
		fs.close();
	}
	/**
	 * 删除目录或文件
	 * @param folder
	 * @throws IOException 
	 */
	public void rmr(String folder) throws IOException{
		Path path = new Path(folder);
		FileSystem fs = FileSystem.get(URI.create(hdfsPath), conf);
		fs.deleteOnExit(path);
		logger.info("Delete: " + folder);
		fs.close();
	}
	/**
	 * 复制文件到远程HDFS
	 * @param local
	 * @param remote
	 * @throws IOException
	 */
	public void copyFile(String local, String remote) throws IOException{
		FileSystem fs = FileSystem.get(URI.create(hdfsPath), conf);
		fs.copyFromLocalFile(new Path(local), new Path(remote));
		logger.info("copy from : " + local + " to : " + remote);
		fs.close();
	}
	/**
	 * 查看文件内容
	 * @param remoteFile
	 * @throws IOException
	 */
	public String cat(String remoteFile) throws IOException{
		Path path = new Path(remoteFile);
		FileSystem fs = FileSystem.get(URI.create(hdfsPath), conf);
		FSDataInputStream fsdis = null;
		logger.info("\ncat: " + remoteFile);
		OutputStream outputStream = new ByteArrayOutputStream();
		String string = null;
		try {
			fsdis = fs.open(path);
			IOUtils.copyBytes(fsdis, outputStream, 4096, false);
			string = outputStream.toString();
		} finally {
			IOUtils.closeStream(fsdis);
			fs.close();
		}
		logger.info(string);
		return string;
	}
	/**
	 * 从远程下载文件
	 * @param remote
	 * @param local
	 * @throws IOException
	 */
	public void download(String remote, String local) throws IOException{
		Path path = new Path(remote);
		FileSystem fs = FileSystem.get(URI.create(hdfsPath), conf);
		fs.copyToLocalFile(path, new Path(local));
		logger.info("\n download: from " + remote + " to " + local);
		fs.close();
	}
	/**
	 * 创建新文件
	 * @param file
	 * @param content
	 * @throws IOException
	 */
	public void createFile(String file, String content) throws IOException{
		FileSystem fs = FileSystem.get(URI.create(hdfsPath), conf);
		byte[] buff = content.getBytes();
		FSDataOutputStream fsos = null;
		try {
			fsos = fs.create(new Path(file));
			fsos.write(buff, 0, buff.length);
		} finally {
			if(fsos != null) fsos.close();
			fs.close();
		}
		logger.info("\n create file: " + file);
	}
	/**
	 * 重命名文件
	 * @param src
	 * @param dst
	 * @throws IOException
	 */
	public void renameFile(String src, String dst) throws IOException{
		FileSystem fs = FileSystem.get(URI.create(hdfsPath), conf);
		fs.rename(new Path(src), new Path(dst));
		logger.info("rename file: from "+ src + " to " + dst);
		fs.close();
	}
	/**
	 * 返回文件位置
	 * @param folder
	 * @param file
	 * @throws IOException
	 */
	public void location(String folder, String file) throws IOException{
		FileSystem fs = FileSystem.get(URI.create(hdfsPath), conf);
		FileStatus f = fs.getFileStatus(new Path(hdfsPath + folder + file));
		BlockLocation[] list = fs.getFileBlockLocations(f, 0, f.getLen());
		logger.info("file location: " + hdfsPath + folder + file);
		for (BlockLocation bl : list) {
		    String[] hosts = bl.getHosts();
		    for (String host : hosts) {
		    	logger.info("host:" + host);
		    }
		}
		fs.close();
		
	}
}
