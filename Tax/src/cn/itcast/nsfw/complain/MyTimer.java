package cn.itcast.nsfw.complain;

import java.util.Timer;

public class MyTimer {

	public static void main(String[] args) {
		//延迟1秒执行，每隔2秒执行一次
		new Timer().schedule(new MyTimerTask(), 1000, 2000);
	}

}
