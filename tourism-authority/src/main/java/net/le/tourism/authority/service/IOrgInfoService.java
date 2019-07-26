package net.le.tourism.authority.service;

import com.baomidou.mybatisplus.extension.service.IService;
import net.le.tourism.authority.pojo.dto.EditOrgInfoDto;
import net.le.tourism.authority.pojo.dto.InsertOrgInfoDto;
import net.le.tourism.authority.pojo.dto.QueryOrgInfoDto;
import net.le.tourism.authority.pojo.entity.OrgInfo;
import net.le.tourism.authority.pojo.vo.QueryOrgInfoVo;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 韩乐
 * @since 2019-05-18
 */
public interface IOrgInfoService extends IService<OrgInfo> {

    /**
     * 查询组织信息
     * @param queryOrgInfoDto
     * @return
     */
    List<QueryOrgInfoVo> queryOrgInfo(QueryOrgInfoDto queryOrgInfoDto);

    /**
     * 添加组织信息
     * @param insertOrgInfoDto
     */
    void insertOrgInfo(InsertOrgInfoDto insertOrgInfoDto);

    /**
     * 修改组织信息
     * @param editOrgInfoDto
     */
    void editOrgInfo(EditOrgInfoDto editOrgInfoDto);

    /**
     * 删除组织
     * @param orgId
     */
    void removeOrgInfo(Integer orgId);
}
