package com.daichao.common.mq;

/**
 * 队列配置
 *
 * @author sunda
 * @version 2017-08-14
 */
public enum QueueConfig {

    //征信API接口调用日志
    API_INVOKE_LOG("apiInvokeLogQueue", "API_INVOKE_LOG"),
//    PREPARE_AUTO_APPROVE("prepareAutoApproveQueue", "PREPARE_AUTO_APPROVE"),
//    AUTO_APPROVE("autoApproveQueue", "AUTO_APPROVE"),
    PUSH_USER_BASEINFO("pushUserBaseInfoQuene", "PUSH_USER_BASEINFO"),
    PUSH_USER_ADDITIONALINFO("pushUserAdditionalInfoQuene", "PUSH_USER_ADDITIONALINFO"),

    PUSH_RONG_BASEINFO("pushRongBaseInfoQuene", "PUSH_RONG_BASEINFO"),
    PUSH_RONG_ADDITIONALINFO("pushRongAdditionalInfoQuene", "PUSH_RONG_ADDITIONALINFO"),
	PROCESS_RONGTJ_REPORTDETAIL("processRongTJReportDetailQuene", "PROCESS_RONGTJ_REPORTDETAIL"),
	PULL_RONGTJ_REPORT("pullRongTJReportQuene", "PULL_RONGTJ_REPORT"),

    PUSH_JDQ("pushJDQQuene", "PUSH_JDQ"),

    PUSH_SLL_BASEINFO("pushSLLBaseInfoQuene", "PUSH_SLL_BASEINFO"),
    PUSH_SLL_ADDITIONALINFO("pushSLLAdditionalInfoQuene", "PUSH_SLL_ADDITIONALINFO"),

    PUSH_DWD_BASEINFO("pushDWDBaseInfoQuene", "PUSH_DWD_BASEINFO"),
    PUSH_DWD_ADDITIONALINFO("pushDWDAdditionalInfoQuene", "PUSH_DWD_ADDITIONALINFO"),
    PUSH_DWD_CHARGEINFO("pushDWDChargeInfoQuene", "PUSH_DWD_CHARGEINFO");

    /**
     * 队列名称
     */
    private String name;
    /**
     * 队列类型
     */
    private String type;

    QueueConfig(String name, String type) {
        this.name = name;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

}
