package cn.itcast.core.exception;

public class ActionException extends SysException {

	public ActionException() {
		super("请求操作失败");
	}

	public ActionException(String message) {
		super(message);
	}

}
