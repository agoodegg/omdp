alter table t_order_info add TRASH_MEMO varchar(200) after AGENT_MOBILE;
alter table t_order_info add TRASH_OPR_NAME varchar(40) after TRASH_MEMO;