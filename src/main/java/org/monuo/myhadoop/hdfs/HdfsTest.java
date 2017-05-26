/**
 * HdfsTest.java created at 2017年5月17日 下午4:20:15
 */
package org.monuo.myhadoop.hdfs;

import java.io.InputStream;
import java.net.URI;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IOUtils;

/**
 * @author saxon
 */
public class HdfsTest {
	public static void main(String[] args) throws Exception{
		String uri = "hdfs://192.168.159.132:9000/";
		Configuration cfg = new Configuration();
		FileSystem fs = FileSystem.get(URI.create(uri), cfg);
		
		// 列出hdfs上/user/fkong/目录下的所有文件和目录  
        FileStatus[] statuses = fs.listStatus(new Path("/user/kconfig"));  
        for (FileStatus status : statuses) {  
            System.out.println(status);  
        }
        
        // 在hdfs的/user/fkong目录下创建一个文件，并写入一行文本  
        FSDataOutputStream os = fs.create(new Path("/user/kconfig/test.log"));  
        os.write("Hello World!".getBytes());  
        os.flush();  
        os.close();
        
        // 显示在hdfs的/user/fkong下指定文件的内容  
        InputStream is = fs.open(new Path("/user/kconfig/test.log"));  
        IOUtils.copyBytes(is, System.out, 1024, true);  
	}
}
