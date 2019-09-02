package net.le.tourism.authority.common.result;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.Data;
import lombok.ToString;

import java.util.List;

/**
 * @author hanle
 * @version v1.0
 * @date 2019-06-14
 * @modify
 *
 * 编程千万条, 规范第一条, 注释不规范, 接盘泪两行!
 */
@Data
@ToString
public class PageResult<T> {

    /**
     * 当前页
     */
    private long currentPage;

    /**
     * 每页条数
     */
    private long pageSize;

    /**
     * 总数
     */
    private long totalSize;

    /**
     * 总页数
     */
    private long totalPage;

    private List<T> list;

    private PageResult() {
    }

    public PageResult(Page<T> page) {
        this.currentPage = page.getCurrent();
        this.pageSize = page.getSize();
        this.totalSize = page.getTotal();
        this.totalPage = page.getPages();
        this.list = page.getRecords();
    }

}
