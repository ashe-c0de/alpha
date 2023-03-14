package org.ashe.alpha.base.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Setter
@Getter
public class PageDTO {

    /**
     * 当前页
     */
    @Min(value = 1, message = "页码最小为1")
    @NotNull(message = "当前页(要么选择传，要么不传，不能传nul)")
    private Integer current = 1;

    /**
     * 页面大小
     */
    @Min(value = 1, message = "每页数据条数小为1")
    @NotNull(message = "每页数据条数(要么选择传，要么不传，不能传null)")
    private Integer size = 10;
}