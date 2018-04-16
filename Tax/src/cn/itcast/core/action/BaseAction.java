package cn.itcast.core.action;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import cn.itcast.core.util.PageResult;

import com.opensymphony.xwork2.ActionSupport;

public abstract class BaseAction extends ActionSupport {

	protected String[] selectedRow;
	protected PageResult pageResult;
	private int pageNo;
	private int pageSize;
	public static int DEFAULT_PAGE_SIZE = 3;//默认页大小
	
	//decode 字符串
	public String decode(String str){
		try {
			return URLDecoder.decode(str,"utf-8");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return str;
	}
	public String[] getSelectedRow() {
		return selectedRow;
	}
	public void setSelectedRow(String[] selectedRow) {
		this.selectedRow = selectedRow;
	}
	public PageResult getPageResult() {
		return pageResult;
	}

	public void setPageResult(PageResult pageResult) {
		this.pageResult = pageResult;
	}

	public int getPageNo() {
		return pageNo;
	}

	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}

	public int getPageSize() {
		if(pageSize < 1) pageSize = DEFAULT_PAGE_SIZE;
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
}
