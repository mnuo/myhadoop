/**
 * KPI.java created at 2017年5月19日 上午11:29:11
 */
package org.monuo.myhadoop.kpi;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

import org.apache.log4j.Logger;

/**
 * 日志文件每一行作为一条记录，该类完成每一行日志的解析
 * @author saxon
 */
public class KPI {
	public static Logger logger = Logger.getLogger(KPI.class);
	
	private String remoteAddr;//记录客户端的ip地址
	private String remoteUser;//记录客户端的名称
	private String timeLocal;//记录访问的时间和时区
	private String request;//记录请问的url和http协议
	private String status;//记录请求状态;成功是200
	private String bodyBytesSent;//记录发送给客户端文件主体内容大小
	private String httpReferer;//记录从哪个页面链接访问过来的
	private String httpUserAgent;//记录客户浏览器相关信息
	
	private boolean valid = true;//判断数据是否合法
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
        sb.append("valid:" + this.valid);
        sb.append("\nremoteAddr:" + this.remoteAddr);
        sb.append("\nremoteUser:" + this.remoteUser);
        sb.append("\ntimeLocal:" + this.timeLocal);
        sb.append("\nrequest:" + this.request);
        sb.append("\nstatus:" + this.status);
        sb.append("\nbodyBytesSent:" + this.bodyBytesSent);
        sb.append("\nhttpReferer:" + this.httpReferer);
        sb.append("\nhttpUserAgent:" + this.httpUserAgent);
        return sb.toString();
	}
	
	public static KPI parser(String line){
		logger.info(line);
		KPI kpi = new KPI();
		String[] arr = line.split(" ");
		if(arr.length > 11){
			kpi.setRemoteAddr(arr[0]);
			kpi.setRemoteUser(arr[1]);
			kpi.setTimeLocal(arr[3].substring(1));
			kpi.setRequest(arr[6]);
			kpi.setStatus(arr[8]);
			kpi.setBodyBytesSent(arr[9]);
			kpi.setHttpReferer(arr[10]);
			if(arr.length > 12){
				kpi.setHttpUserAgent(arr[11] + arr[12]);
			}else{
				kpi.setHttpUserAgent(arr[11]);
			}
			
			if(Integer.parseInt(kpi.getStatus()) >= 400){//大于400 http错误
				kpi.setValid(false);
			}
		}else{
			kpi.setValid(true);
		}
		return kpi;
	}
	/**
	 * 按page的PV分类
	 * @param line
	 * @return
	 */
	public static KPI filterPVs(String line) {
		KPI kpi = parser(line);
		Set<String> pages = new HashSet<String>();
		
		pages.add("/about");
		pages.add("/black-ip-list/");
		pages.add("/cassandra-clustor/");
		pages.add("/finance-rhive-repurchase/");
		pages.add("/hadoop-family-roadmap/");
		pages.add("/hadoop-hive-intro/");
		pages.add("/hadoop-zookeeper-intro/");
		pages.add("/hadoop-mahout-roadmap/");
		
		if(!pages.contains(kpi.getRequest())){
			kpi.setValid(false);
		}
		return kpi;
	}
	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		String line = "222.68.172.190 - - [18/Sep/2013:06:49:57 +0000] \"GET /images/my.jpg HTTP/1.1\" 200 19939 \"http://www.angularjs.cn/A00n\" \"Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/29.0.1547.66 Safari/537.36\"";
		logger.info(line);
		
		KPI kpi = new KPI();
		String[] arr = line.split(" ");
		kpi.setRemoteAddr(arr[0]);
		kpi.setRemoteUser(arr[1]);
		kpi.setTimeLocal(arr[3].substring(1));
		kpi.setRequest(arr[6]);
		kpi.setStatus(arr[8]);
		kpi.setBodyBytesSent(arr[9]);
		kpi.setHttpReferer(arr[10]);
		kpi.setHttpUserAgent(arr[11] + arr[12]);
		logger.info(kpi.toString());
		
		SimpleDateFormat df = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss", Locale.US);
		logger.info(df.format(kpi.getTimeLocalDate()));
		logger.info(kpi.getTimeLocalDateHour());
		logger.info(kpi.getHttpRefererDomain());

	}
	public Date getTimeLocalDate() throws ParseException {
        SimpleDateFormat df = new SimpleDateFormat("dd/MMM/yyyy:HH:mm:ss", Locale.US);
        return df.parse(this.timeLocal);
    }
    
    public String getTimeLocalDateHour() throws ParseException{
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHH");
        return df.format(this.getTimeLocalDate());
    }
    public String getHttpRefererDomain(){
        if(httpReferer.length()<8){ 
            return httpReferer;
        }
        
        String str=this.httpReferer.replace("\"", "").replace("http://", "").replace("https://", "");
        return str.indexOf("/")>0?str.substring(0, str.indexOf("/")):str;
    }
	

	public String getRemoteAddr() {
		return remoteAddr;
	}
	public void setRemoteAddr(String remoteAddr) {
		this.remoteAddr = remoteAddr;
	}
	public String getRemoteUser() {
		return remoteUser;
	}
	public void setRemoteUser(String remoteUser) {
		this.remoteUser = remoteUser;
	}
	public String getTimeLocal() {
		return timeLocal;
	}
	public void setTimeLocal(String timeLocal) {
		this.timeLocal = timeLocal;
	}
	public String getRequest() {
		return request;
	}
	public void setRequest(String request) {
		this.request = request;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getBodyBytesSent() {
		return bodyBytesSent;
	}
	public void setBodyBytesSent(String bodyBytesSent) {
		this.bodyBytesSent = bodyBytesSent;
	}
	public String getHttpReferer() {
		return httpReferer;
	}
	public void setHttpReferer(String httpReferer) {
		this.httpReferer = httpReferer;
	}
	public String getHttpUserAgent() {
		return httpUserAgent;
	}
	public void setHttpUserAgent(String httpUserAgent) {
		this.httpUserAgent = httpUserAgent;
	}
	public boolean isValid() {
		return valid;
	}
	public void setValid(boolean valid) {
		this.valid = valid;
	}
}
