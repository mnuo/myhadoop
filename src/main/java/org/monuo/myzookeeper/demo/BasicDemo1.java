/**
 * BasicDemo1.java created at 2017年5月25日 下午4:34:40
 */
package org.monuo.myzookeeper.demo;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.ZooKeeper;

/**
 * @author saxon
 */
public class BasicDemo1 {
	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		//创建一个与服务器的连接
		ZooKeeper zk = new ZooKeeper("192.168.159.132:2181", 60000, new Watcher(){
			//监控所有触发事件
			public void process(WatchedEvent event) {
				System.out.println("EVENT: " + event.getType());
			}
		});
		
		//查看根节点:
		System.out.println("ls / => " + zk.getChildren("/", true));
		
		//创建一个目录节点
		if(zk.exists("/node", true) == null){
			zk.create("/node", "conan".getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
			System.out.println("create /node conan");
			//查看node节点数据
			System.out.println("ls /node => " + new String(zk.getData("/node", false, null)));
			//查看根节点
			System.out.println("ls / => " + zk.getChildren("/", true));
		}
		//创建一个子节点目录
		if(zk.exists("/node/sub1", true) == null){
			zk.create("/node/sub1", "sub1".getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
			System.out.println("create /node/sub1 sub1");
			//查看/node节点
			System.out.println("ls /node => " + zk.getChildren("/node", true));
		}
		//修改节点
		if(zk.exists("/node", true) != null){
			zk.setData("/node", "change".getBytes(), -1);
			//查看node节点数据
			System.out.println("ls /node => " + new String(zk.getData("/node", false, null)));
		}
		//删除节点
		if(zk.exists("/node/sub1", true) != null){
			zk.delete("/node/sub1", -1);
			zk.delete("/node", -1);
			//查看根节点
			System.out.println("ls / => " + zk.getChildren("/", true));
		}
		//关闭连接
		zk.close();
	}

}
