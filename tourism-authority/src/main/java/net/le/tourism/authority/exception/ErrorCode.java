package net.le.tourism.authority.exception;

import lombok.Getter;

/**
 * @author hanle
 * @version v1.0
 * @date 2019-04-04
 * @modify
 *
 * 编程千万条, 规范第一条, 注释不规范, 接盘泪两行!
 */
@Getter
public enum ErrorCode {

    /**
     * 错误代码code 共5为  第一位 系统代码 本系统固定为4 第二三位为模块代码，第四五为模块内的异常代码
     */
    sys_ok(0, "ok"),
    sys_default_error(1, "服务器请求错误"),
    // 参数异常校验
    sys_param_error(40101, "参数校验失败！"),
    sys_sql_syntax_error(40102, "SQL语法错误！"),
    sys_login_name_exists(40103, "当前登录名已被注册！"),
    sys_insert_admin_error(40104, "添加管理员信息失败！"),
    sys_edit_admin_error(40105, "修改管理员信息失败！"),
    sys_admin_id_un_found(40106, "删除管理员失败，管理员id必须大于0！"),
    sys_admin_info_un_found(40107, "删除管理员失败，ID对应的管理员信息不存在0！"),
    sys_remove_admin_error(40108, "管理员账号不能被删除！"),
    sys_file_not_null(40109, "文件不能为空！"),
    sys_file_upload_insert_error(40110, "保存文件上传信息失败！"),
    sys_update_admin_status_error(40111, "管理员账号不能被禁用！"),
    sys_file_un_read(40112, "读取文件出错！"),
    sys_file_un_security(40113, "加密失败！"),
    sys_insert_admin_role_error(40114, "角色不能为空！"),
    sys_sql_error(40115, "执行sql错误！"),
    sys_insert_org_error(40116, "添加组织信息失败，组织信息或上级组织不能为空！"),
    sys_login_info_error(40117, "你当前登录账号信息异常，请检查！"),
    sys_edit_org_error(40118, "修改组织信息失败，组织信息或上级组织不能为空！"),
    sys_upload_file_to_max(40119, "上传文件失败，最大不能超过10M"),
    sys_role_info_is_null(40120, "修改角色信息失败，该角色不存在！"),
    sys_source_remove_error(40121, "当前资源下有子节点，不能删除！"),
    sys_source_remove_error_id_invalid(40122, "资源删除失败，资源Id不能为空！"),
    sys_insert_admin_org_error(40123, "组织不能为空！"),
    sys_remove_org_error(40124, "当前组织下有子组织不能删除！"),
    sys_remove_role_error(40125, "当前角色下已有用户不能删除！"),
    sys_source_remove_error_has_role(40126, "当前资源下已被分配，不能删除！"),

    authority_un_login(40201, "你当前未登录，请登录！"),
    authority_login_user_un_register(40202, "登录失败，登录账号不存在！"),
    authority_login_info_error(40203, "登录失败，登录密码错误！"),
    authority_login_token_Invalid(40204, "登录已过期，请重新登录！"),;

    private Integer code;

    private String msg;

    ErrorCode() {
        this.code = 0;
        this.msg = "ok";
    }

    ErrorCode(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
