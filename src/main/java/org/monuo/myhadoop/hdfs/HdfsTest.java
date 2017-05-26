/**
 * HdfsTest.java created at 2017��5��17�� ����4:20:15
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
		
		// �г�hdfs��/user/fkong/Ŀ¼�µ������ļ���Ŀ¼  
        FileStatus[] statuses = fs.listStatus(new Path("/user/kconfig"));  
        for (FileStatus status : statuses) {  
            System.out.println(status);  
        }
        
        // ��hdfs��/user/fkongĿ¼�´���һ���ļ�����д��һ���ı�  
        FSDataOutputStream os = fs.create(new Path("/user/kconfig/test.log"));  
        os.write("Hello World!".getBytes());  
        os.flush();  
        os.close();
        
        // ��ʾ��hdfs��/user/fkong��ָ���ļ�������  
        InputStream is = fs.open(new Path("/user/kconfig/test.log"));  
        IOUtils.copyBytes(is, System.out, 1024, true);  
	}
}
