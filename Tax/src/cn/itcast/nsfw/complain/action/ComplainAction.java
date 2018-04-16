package cn.itcast.nsfw.complain.action;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.struts2.ServletActionContext;

import cn.itcast.core.action.BaseAction;
import cn.itcast.core.util.QueryHelper;
import cn.itcast.nsfw.complain.entity.Complain;
import cn.itcast.nsfw.complain.entity.ComplainReply;
import cn.itcast.nsfw.complain.service.ComplainService;

import com.opensymphony.xwork2.ActionContext;

public class ComplainAction extends BaseAction {
	
	@Resource
	private ComplainService complainService;
	
	private Complain complain;
	private ComplainReply reply;
	private String strTitle;
	private String strState;
	private String startTime;
	private String endTime;
	private Map<String, Object> statisticMap;
	
	//列表
	public String listUI(){
		try {
			//加载投诉状态集合
			ActionContext.getContext().getContextMap().put("complainStateMap", Complain.COMPLAIN_STATE_MAP);
			
			QueryHelper queryHelper = new QueryHelper(Complain.class, "c");
			if(StringUtils.isNotBlank(startTime)){
				startTime = decode(startTime);
				queryHelper.addCondition("c.compTime >= ?", DateUtils.parseDate(startTime + ":00", "yyyy-MM-dd HH:mm:ss"));
			}
			if(StringUtils.isNotBlank(endTime)){
				endTime = decode(endTime);
				queryHelper.addCondition("c.compTime <= ?", DateUtils.parseDate(endTime + ":00", "yyyy-MM-dd HH:mm:ss"));
			}
			if(complain != null){
				if(StringUtils.isNotBlank(complain.getState())){
					queryHelper.addCondition("c.state = ?", complain.getState() );
				}
				if(StringUtils.isNotBlank(complain.getCompTitle())){
					complain.setCompTitle(decode(complain.getCompTitle()));
					queryHelper.addCondition("c.compTitle like ?", "%" + complain.getCompTitle() + "%" );
				}
			}
			
			//根据状态排序
			queryHelper.addOrderByProperty("c.state", QueryHelper.ORDER_BY_ASC);
			//根据投诉时间排序
			queryHelper.addOrderByProperty("c.compTime", QueryHelper.ORDER_BY_ASC);
			
			pageResult = complainService.getPageResult(queryHelper, getPageNo(), getPageSize());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "listUI";
	}
	
	//跳转到受理页面
	public String dealUI(){
		//加载投诉状态集合
		ActionContext.getContext().getContextMap().put("complainStateMap", Complain.COMPLAIN_STATE_MAP);
		if(complain != null){
			strTitle = complain.getCompTitle();
			strState = complain.getState();
			complain = complainService.findObjectById(complain.getCompId());
		}
		return "dealUI";
	}
	
	//保存受理信息
	public String deal(){
		if(complain != null && reply != null){
			//1、更新投诉信息的状态为 已受理
			Complain tem = complainService.findObjectById(complain.getCompId());
			if(Complain.COMPLAIN_STATE_UNDONE.equals(tem.getState())){//说明是待受理，需要更新投诉信息状态为已受理
				tem.setState(Complain.COMPLAIN_STATE_DONE);
			}
			//2、保存受理信息
			reply.setComplain(tem);
			reply.setReplyTime(new Timestamp(new Date().getTime()));
			tem.getComplainReplies().add(reply);
			complainService.update(tem);
		}
		return "list";
	}
	
	//跳转到年度统计图表页面
	public String annualStatisticChartUI(){
		return "annualStatisticChartUI";
	}
	
	//获取年度投诉数
	public String getAnnualStatisticData(){
		int year = 0;
		HttpServletRequest request = ServletActionContext.getRequest();
		//1、获取年度
		if(request.getParameter("year") != null){
			year = Integer.parseInt(request.getParameter("year"));
		} else {
			year = Calendar.getInstance().get(Calendar.YEAR);//默认当前年份
		}
		statisticMap = new HashMap<String, Object>();
		//2、根据年度获取对应12个月份的投诉数并设置到返回结果中
		statisticMap.put("msg", "success");
		statisticMap.put("chartData", complainService.getStatisticDataByYear(year));
		return "statisticData";
	}

	public Complain getComplain() {
		return complain;
	}

	public void setComplain(Complain complain) {
		this.complain = complain;
	}

	public String getStrTitle() {
		return strTitle;
	}

	public void setStrTitle(String strTitle) {
		this.strTitle = strTitle;
	}

	public String getStrState() {
		return strState;
	}

	public void setStrState(String strState) {
		this.strState = strState;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public ComplainReply getReply() {
		return reply;
	}

	public void setReply(ComplainReply reply) {
		this.reply = reply;
	}

	public Map<String, Object> getStatisticMap() {
		return statisticMap;
	}
	
}
