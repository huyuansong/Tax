package cn.itcast.nsfw.complain;

import java.text.SimpleDateFormat;
import java.util.Date;

public class QuartzTask {

	
	public void doSimpleTrigger() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		System.out.println("doing simpleTrigger task..." + sdf.format(new Date()));
	}
	
	public void doCronTrigger() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		System.out.println("doing cronTrigger task..." + sdf.format(new Date()));
	}
}
